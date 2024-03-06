package dataAccess;

import dataAccess.DAOInterfaces.AuthDAO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class MySQLAuthDAO implements AuthDAO {

    public MySQLAuthDAO() throws DataAccessException {
        configureDatabase();
    }

    @Override
    public String createAuth(String username) throws DataAccessException {
        String newAuth = UUID.randomUUID().toString();
        String statement = "INSERT INTO auth (auth_token, username) VALUES (?, ?)";
        try (var conn = DatabaseManager.getConnection()){
            PreparedStatement preparedStatement = conn.prepareStatement(statement);
            preparedStatement.setString(1, newAuth);
            preparedStatement.setString(2, username);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return newAuth;
    }

    @Override
    public void deleteAuth(String auth) {
        String statement = "DELETE FROM auth where auth_token = ?";
        try (var conn = DatabaseManager.getConnection()){
            PreparedStatement preparedStatement = conn.prepareStatement(statement);
            preparedStatement.setString(1, auth);
            preparedStatement.executeUpdate();
        } catch (SQLException | DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getUser(String authToken) throws DataAccessException {
        String statement = "SELECT * FROM auth where auth_token = ?";
        try (var conn = DatabaseManager.getConnection()){
            PreparedStatement preparedStatement = conn.prepareStatement(statement);
            preparedStatement.setString(1, authToken);
            var info = preparedStatement.executeQuery();
            if (info.next()){
                return info.getString("username");
            }
            else {
                return null;
            }
        } catch (SQLException | DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public int size() throws DataAccessException {
        String statement = "Select * from auth";
        int count = 0;
        try (var conn = DatabaseManager.getConnection()){
            PreparedStatement preparedStatement = conn.prepareStatement(statement);
            var list = preparedStatement.executeQuery();
            while (list.next()){
                count += 1;
            }
            return count;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clear() {
        String statement = "TRUNCATE TABLE auth";
        try (var conn = DatabaseManager.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(statement);
            preparedStatement.executeUpdate();
        } catch (SQLException | DataAccessException e) {
            throw new RuntimeException(e);
        }
    }
        String[] sqlString = {"""
            CREATE TABLE IF NOT EXISTS  auth (
              auth_token varchar(256) NOT NULL,
              username varchar(256) NOT NULL,
              PRIMARY KEY (auth_token)
            );
            """};

    private void configureDatabase() throws DataAccessException {
        DatabaseManager.createDatabase();
        try (var conn = DatabaseManager.getConnection()) {
            for (var statement : sqlString) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException | DataAccessException e) {
            throw new RuntimeException(e);
        }
    }
}