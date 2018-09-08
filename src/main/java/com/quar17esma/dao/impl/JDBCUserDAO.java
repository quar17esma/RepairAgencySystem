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
import java.util.Optional;

public class JDBCUserDAO extends JDBCGenericDAO<User> implements UserDAO {
    private static final String FIND_BY_EMAIL = "SELECT * FROM user WHERE email = ?";
    private static final String FIND_BY_PHONE = "SELECT * FROM user WHERE phone = ?";

    private JDBCUserDAO(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
        logger = Logger.getLogger(JDBCUserDAO.class);
        findAll = "SELECT * FROM user ";
        findById = "SELECT * FROM user WHERE id = ?";
        update = "UPDATE user " +
                "SET email = ?, phone = ?, password = ?, role = ?, name = ?, surname = ?, birth_date = ? WHERE id = ?";
        delete = "DELETE FROM user WHERE id = ?";
        insert = "INSERT INTO user (email, phone, password, role, name, surname, birth_date) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?) ";
    }

    private static class Holder {
        private static JDBCUserDAO INSTANCE = new JDBCUserDAO(ConnectionPool.getInstance());
    }

    public static JDBCUserDAO getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    protected User createItem(ResultSet rs) throws SQLException {
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
    protected void setUpdateQueryParams(PreparedStatement query, User item) throws SQLException {
        query.setString(1, item.getEmail());
        query.setString(2, item.getPhone());
        query.setString(3, item.getPassword());
        query.setString(4, item.getRole().name());
        query.setString(5, item.getName());
        query.setString(6, item.getSurname());
        query.setDate(7, Date.valueOf(item.getBirthDate()));
        query.setLong(8, item.getId());
    }

    @Override
    public long insert(User user) {
        long result = -1;

        try (Connection connection = connectionPool.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement queryFindByEmail = connection.prepareStatement(FIND_BY_EMAIL);
                 PreparedStatement queryInsert = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {

                queryFindByEmail.setString(1, user.getEmail());
                ResultSet rs = queryFindByEmail.executeQuery();
                if (rs.next()) {
                    throw new BusyEmailException("Fail to register user: " + user + ", email is busy",
                            user.getName(), user.getEmail());
                }

                setInsertQueryParams(queryInsert, user);
                queryInsert.executeUpdate();
                ResultSet rsId = queryInsert.getGeneratedKeys();
                if (rsId.next()) {
                    result = rsId.getLong(1);
                    setId(user, result);
                }

                connection.commit();
            } catch (BusyEmailException e) {
                connection.rollback();
                connection.setAutoCommit(true);
                logger.error(e.getMessage(), e);
                throw e;
            } catch (SQLException e) {
                connection.rollback();
                connection.setAutoCommit(true);
                throw e;
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            logger.error("Fail to insert user: " + user, e);
            throw new RuntimeException(e);
        }

        logger.info("User inserted to DB, user: " + user);
        return result;
    }

    @Override
    protected void setInsertQueryParams(PreparedStatement query, User item) throws SQLException {
        query.setString(1, item.getEmail());
        query.setString(2, item.getPhone());
        query.setString(3, item.getPassword());
        query.setString(4, item.getRole().name());
        query.setString(5, item.getName());
        query.setString(6, item.getSurname());
        query.setDate(7, Date.valueOf(item.getBirthDate()));
    }

    @Override
    protected void setId(User item, long id) {
        item.setId(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Optional<User> result = Optional.empty();

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement query = connection.prepareStatement(FIND_BY_EMAIL)) {
            query.setString(1, email);
            ResultSet rs = query.executeQuery();
            if (rs.next()) {
                User user = createItem(rs);
                result = Optional.of(user);
            }
        } catch (SQLException e) {
            logger.error("Fail to find user with email = " + email, e);
        }

        logger.info("Found user by email, user: " + result + ", email: " + email);
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
                User user = createItem(rs);
                result = Optional.of(user);
            }
        } catch (SQLException e) {
            logger.error("Fail to find user with phone = " + phone, e);
        }

        logger.info("Found user by phone, user: " + result + ", phone: " + phone);
        return result;
    }

    public User login(String email, String password) {
        User user = null;

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement findByEmailQuery = connection.prepareStatement(FIND_BY_EMAIL)) {
            findByEmailQuery.setString(1, email);
            ResultSet rs = findByEmailQuery.executeQuery();
            if (rs.next()) {
                User userByEmail = createItem(rs);
                if (password.equals(userByEmail.getPassword())) {
                    user = userByEmail;
                } else {
                    throw new WrongPasswordException("Wrong password for user with email: " + email, email);
                }
            } else {
                throw new NoSuchUserException("Fail to find user with email: " + email, email);
            }
        } catch (NoSuchUserException e) {
            logger.error(e.getMessage(), e);
            throw new NoSuchUserException(e.getMessage(), e.getEmail());
        } catch (WrongPasswordException e) {
            logger.error(e.getMessage(), e);
            throw new WrongPasswordException(e.getMessage(), e.getEmail());
        } catch (SQLException e) {
            logger.error("Fail to find user with email = " + email, e);
        }

        logger.info("Logged in user by email, email: " + email);
        return user;
    }
}
