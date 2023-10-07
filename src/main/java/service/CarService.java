package service;

import entity.Car;
import exception.CarNotFoundException;
import exception.CredentialsInvalidException;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static connection.DBConnection.closeConnection;
import static connection.DBConnection.getConnection;
import static enums.Queries.*;

public class CarService {
    private PreparedStatement preparedStatement;

    public void createCarTable() {
        var connection = getConnection();
        String query = CREATE_CAR_TABLE.getQuery();

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
            System.out.println("cars table created successfully !");
        } catch (SQLException e) {
            throw new CredentialsInvalidException(e.getMessage());
        } finally {
            closePreparedStatement(preparedStatement);
            closeConnection(connection);
        }
    }

    public void createCar(Car car) {
        var connection = getConnection();
        String query = INSERT_CAR.getQuery();

        try {
            preparedStatement = connection.prepareStatement(query);
            Long id = car.getId();
            String name = car.getName();
            Integer speed = car.getSpeed();
            LocalDate release_date = car.getRelease_date();
            String engine = car.getEngine();
            String color = car.getColor();

            preparedStatement.setLong(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setInt(3, speed);
            preparedStatement.setDate(4, Date.valueOf(release_date));
            preparedStatement.setString(5, engine);
            preparedStatement.setString(6, color);
            preparedStatement.execute();
            System.out.println("Car created successfully: " + car);
        } catch (SQLException e) {
            throw new CredentialsInvalidException(e.getMessage());
        } finally {
            closePreparedStatement(preparedStatement);
            closeConnection(connection);
        }
    }

    public List<Car> getAllCars() {
        List<Car> studentsList = new ArrayList<>();
        var connection = getConnection();
        String query = GET_ALL_CARS.getQuery();
        try {
            preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                var car = fillCar(resultSet);
                studentsList.add(car);
            }
            return studentsList;
        } catch (SQLException e) {
            throw new CredentialsInvalidException(e.getMessage());
        } finally {
            closePreparedStatement(preparedStatement);
            closeConnection(connection);
        }
    }

    public void getCarById(Long id) {
        var connection = getConnection();
        String query = FIND_BY_ID.getQuery();
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Car car = fillCar(resultSet);
                System.out.println(car);
            } else {
                try {
                    throw new CarNotFoundException("Car not found");
                } catch (RuntimeException r) {
                    System.out.println("Car not found with id: " + id);
                }
            }
        } catch (SQLException e) {
            throw new CredentialsInvalidException(e.getMessage());
        } finally {
            closePreparedStatement(preparedStatement);
            closeConnection(connection);
        }

    }

    public void updateCarName(Long id, String name) {
        var connection = getConnection();
        String query = UPDATE_CAR_NAME.getQuery();
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setLong(2, id);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Car updated successfully !");
            } else {
                System.out.println("Car not found with id: " + id);
            }
        } catch (SQLException e) {
            throw new CredentialsInvalidException(e.getMessage());
        } finally {
            closePreparedStatement(preparedStatement);
            closeConnection(connection);
        }
    }

    public void deleteCar(Long id) {
        var connection = getConnection();
        String query = DELETE_CAR.getQuery();
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Car deleted successfully !");
            } else {
                System.out.println("Car not found with id: " + id);
            }

        } catch (SQLException e) {
            throw new CredentialsInvalidException(e.getMessage());
        } finally {
            closePreparedStatement(preparedStatement);
            closeConnection(connection);
        }
    }

    public void closePreparedStatement(PreparedStatement preparedStatement) {
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                throw new CredentialsInvalidException(e.getMessage());
            }
        }
    }

    public Car fillCar(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong(1);
        String name = resultSet.getString(2);
        Integer speed = resultSet.getInt(3);
        LocalDate release_date = Date.valueOf(String.valueOf(resultSet.getDate(4))).toLocalDate();
        String engine = resultSet.getString(5);
        String color = resultSet.getString(6);
        return new Car(id, name, speed, release_date, engine, color);
    }
}
