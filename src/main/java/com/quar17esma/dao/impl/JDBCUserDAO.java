package com.quar17esma.dao.impl;

import com.quar17esma.dao.ConnectionPool;
import com.quar17esma.dao.UserDAO;
import com.quar17esma.entity.User;
import com.quar17esma.enums.Role;
import com.quar17esma.exceptions.BusyEmailException;
import com.quar17esma.exceptions.NoSuchUserException;
import com.quar17esma.exceptions.WrongPasswordException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCUserDAO implements UserDAO {
    private static final Logger LOGGER = Logger.getLogger(JDBCUserDAO.class);

    private static final String FIND_ALL = "SELECT * FROM user ";
    private static final String FIND_BY_ID = "SELECT * FROM user WHERE id = ?";
    private static final String FIND_BY_EMAIL = "SELECT * FROM user WHERE email = ?";
    private static final String FIND_BY_PHONE = "SELECT * FROM user WHERE phone = ?";
    private static final String UPDATE = "UPDATE user " +
            "SET email = ?, phone = ?, password = ?, role = ?, name = ?, surname = ?, birth_date = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM user WHERE id = ?";
    private static final String INSERT = "INSERT INTO user (email, phone, password, role, name, surname, birth_date) " +
            "VALUES(?, ?, ?, ?, ?, ?, ?) ";

    private ConnectionPool connectionPool;

    private JDBCUserDAO(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    private static class Holder {
        private static JDBCUserDAO INSTANCE = new JDBCUserDAO(ConnectionPool.getInstance());
    }

    public static JDBCUserDAO getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement query = connection.prepareStatement(FIND_ALL)) {
            ResultSet rs = query.executeQuery();
            while (rs.next()) {
                User user = createUser(rs);
                users.add(user);
            }
        } catch (Exception e) {
            LOGGER.error("Fail to find users", e);
        }

        return users;
    }

    @Override
    public Optional<User> findById(long id) {
        Optional<User> result = Optional.empty();

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement query = connection.prepareStatement(FIND_BY_ID)) {
            query.setLong(1, id);
            ResultSet rs = query.executeQuery();
            while (rs.next()) {
                User user = createUser(rs);
                result = Optional.of(user);
            }
        } catch (Exception e) {
            LOGGER.error("Fail to find user with id = " + id, e);
        }

        return result;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Optional<User> result = Optional.empty();

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement query = connection.prepareStatement(FIND_BY_EMAIL)) {
            query.setString(1, email);
            ResultSet rs = query.executeQuery();
            if (rs.next()) {
                User user = createUser(rs);
                result = Optional.of(user);
            }
        } catch (Exception e) {
            LOGGER.error("Fail to find user with email = " + email, e);
        }

        return result;
    }

    @Override
    public Optional<User> findByPhone(String phone) {
        Optional<User> result = Optional.empty();

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement query = connection.prepareStatement(FIND_BY_PHONE)) {
            query.setString(1, phone);
            ResultSet rs = query.executeQuery();
            while (rs.next()) {
                User user = createUser(rs);
                result = Optional.of(user);
            }
        } catch (Exception e) {
            LOGGER.error("Fail to find user with phone = " + phone, e);
        }

        return result;
    }

    private User createUser(ResultSet rs) throws SQLException {
        return new User.Builder()
                .setId(rs.getInt("user.id"))
                .setEmail(rs.getString("user.email"))
                .setPhone(rs.getString("user.phone"))
                .setPassword(rs.getString("user.password"))
                .setName(rs.getString("user.name"))
                .setSurname(rs.getString("user.surname"))
                .setBirthDate(rs.getDate("user.birth_date").toLocalDate())
                .setRole(Role.valueOf(rs.getString("user.role").toUpperCase()))
                .build();
    }

    @Override
    public boolean update(User user) {
        boolean result = false;

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement query = connection.prepareStatement(UPDATE)) {
            query.setString(1, user.getEmail());
            query.setString(2, user.getPhone());
            query.setString(3, user.getPassword());
            query.setString(4, user.getRole().name());
            query.setString(5, user.getName());
            query.setString(6, user.getSurname());
            query.setDate(7, Date.valueOf(user.getBirthDate()));
            query.setLong(8, user.getId());
            query.executeUpdate();
            result = true;
        } catch (Exception e) {
            LOGGER.error("Fail to update user with id = " + user.getId(), e);
        }

        return result;
    }

    @Override
    public boolean delete(long id) {
        boolean result = false;

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement query = connection.prepareStatement(DELETE)) {
            query.setLong(1, id);
            query.executeUpdate();
            result = true;
        } catch (Exception e) {
            LOGGER.error("Fail to delete user with id = " + id, e);
        }

        return result;
    }

    @Override
    public long insert(User user) {
        long result = -1;

        try (Connection connection = connectionPool.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement queryFindByEmail = connection.prepareStatement(FIND_BY_EMAIL);
                 PreparedStatement queryInsert = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {

                queryFindByEmail.setString(1, user.getEmail());
                ResultSet rs = queryFindByEmail.executeQuery();
                if (rs.next()) {
                    throw new BusyEmailException("Fail to register user: " + user + ", email is busy",
                            user.getName(), user.getEmail());
                }

                queryInsert.setString(1, user.getEmail());
                queryInsert.setString(2, user.getPhone());
                queryInsert.setString(3, user.getPassword());
                queryInsert.setString(4, user.getRole().name());
                queryInsert.setString(5, user.getName());
                queryInsert.setString(6, user.getSurname());
                queryInsert.setDate(7, Date.valueOf(user.getBirthDate()));
                queryInsert.executeUpdate();
                ResultSet rsId = queryInsert.getGeneratedKeys();
                if (rsId.next()) {
                    result = rsId.getLong(1);
                    user.setId(result);
                }

                connection.commit();
            } catch (BusyEmailException e) {
                connection.rollback();
                connection.setAutoCommit(true);
                LOGGER.error(e.getMessage(), e);
                throw e;
            } catch (SQLException e) {
                connection.rollback();
                connection.setAutoCommit(true);
                throw e;
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            LOGGER.error("Fail to insert user: " + user, e);
            throw new RuntimeException(e);
        }

        return result;
    }

    public Optional<User> login(String email, String password) {
        Optional<User> userOptional = Optional.empty();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement findByEmailQuery = connection.prepareStatement(FIND_BY_EMAIL)) {
            findByEmailQuery.setString(1, email);
            ResultSet rs = findByEmailQuery.executeQuery();
            if (rs.next()) {
                User user = createUser(rs);
                if (password.equals(user.getPassword())) {
                    userOptional = Optional.ofNullable(user);
                } else {
                    throw new WrongPasswordException("Wrong password for user with email: " + email, email);
                }
            } else {
                throw new NoSuchUserException("Fail to find user with email: " + email, email);
            }
        } catch (NoSuchUserException e) {
            LOGGER.error(e.getMessage(), e);
            throw new NoSuchUserException(e.getMessage(), e.getEmail());
        } catch (WrongPasswordException e) {
            LOGGER.error(e.getMessage(), e);
            throw new WrongPasswordException(e.getMessage(), e.getEmail());
        } catch (Exception e) {
            LOGGER.error("Fail to find user with email = " + email, e);
        }

        return userOptional;
    }
}
