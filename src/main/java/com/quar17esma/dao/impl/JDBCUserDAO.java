package com.quar17esma.dao.impl;

import com.quar17esma.dao.UserDAO;
import com.quar17esma.entity.User;
import com.quar17esma.enums.Role;
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
            "VALUES(?, ?, ?, ?, ?, ?, ?)";

    private Connection connection;

    public JDBCUserDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();

        try (PreparedStatement query = connection.prepareStatement(FIND_ALL)) {
            ResultSet rs = query.executeQuery();
            while (rs.next()) {
                User user = createUser(rs);
                users.add(user);
            }
        } catch (Exception ex) {
            LOGGER.error("Fail to find users", ex);
            throw new RuntimeException(ex);
        }

        return users;
    }

    @Override
    public Optional<User> findById(long id) {
        Optional<User> result = Optional.empty();

        try (PreparedStatement query = connection.prepareStatement(FIND_BY_ID)) {
            query.setLong(1, id);
            ResultSet rs = query.executeQuery();
            while (rs.next()) {
                User user = createUser(rs);
                result = Optional.of(user);
            }
        } catch (Exception ex) {
            LOGGER.error("Fail to find user with id = " + id, ex);
            throw new RuntimeException(ex);
        }

        return result;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Optional<User> result = Optional.empty();

        try (PreparedStatement query = connection.prepareStatement(FIND_BY_EMAIL)) {
            query.setString(1, email);
            ResultSet rs = query.executeQuery();
            while (rs.next()) {
                User user = createUser(rs);
                result = Optional.of(user);
            }
        } catch (Exception ex) {
            LOGGER.error("Fail to find user with email = " + email, ex);
            throw new RuntimeException(ex);
        }

        return result;
    }

    @Override
    public Optional<User> findByPhone(String phone) {
        Optional<User> result = Optional.empty();

        try (PreparedStatement query = connection.prepareStatement(FIND_BY_PHONE)) {
            query.setString(1, phone);
            ResultSet rs = query.executeQuery();
            while (rs.next()) {
                User user = createUser(rs);
                result = Optional.of(user);
            }
        } catch (Exception ex) {
            LOGGER.error("Fail to find user with phone = " + phone, ex);
            throw new RuntimeException(ex);
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

        try (PreparedStatement query = connection.prepareStatement(UPDATE)) {
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
        } catch (Exception ex) {
            LOGGER.error("Fail to update user with id = " + user.getId(), ex);
            throw new RuntimeException(ex);
        }

        return result;
    }

    @Override
    public boolean delete(long id) {
        boolean result = false;

        try (PreparedStatement query = connection.prepareStatement(DELETE)) {
            query.setLong(1, id);
            query.executeUpdate();
            result = true;
        } catch (Exception ex) {
            LOGGER.error("Fail to delete user with id = " + id, ex);
            throw new RuntimeException(ex);
        }

        return result;
    }

    @Override
    public long insert(User user) {
        long result = -1;

        try (PreparedStatement query = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            query.setString(1, user.getEmail());
            query.setString(2, user.getPhone());
            query.setString(3, user.getPassword());
            query.setString(4, user.getRole().name());
            query.setString(5, user.getName());
            query.setString(6, user.getSurname());
            query.setDate(7, Date.valueOf(user.getBirthDate()));
            query.executeUpdate();
            ResultSet rsId = query.getGeneratedKeys();
            if (rsId.next()) {
                result = rsId.getLong(1);
                user.setId(result);
            }
        } catch (Exception ex) {
            LOGGER.error("Fail to insert user: " + user.toString(), ex);
            throw new RuntimeException(ex);
        }

        return result;
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }
}
