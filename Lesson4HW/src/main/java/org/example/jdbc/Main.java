package org.example.jdbc;

import org.example.jdbc.model.Course;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/";
        String user = "root";
        String password = "secret";

        try(Connection connection = DriverManager.getConnection(url, user, password)){
            createDatabase(connection);
            System.out.println("Database created successfully");

            useDatabase(connection);
            System.out.println("Use database successfully");

            createTable(connection);
            System.out.println("Create table successfully");

            insertData(connection, new Course("Docker", 48 * 60 + 20));
            insertData(connection, new Course("SQL", 15 * 60));
            insertData(connection, new Course("Java Junior", 3 * 60 + 30));
            System.out.println("Insert data successfully");

            List<Course> courses = readData(connection);
            for (var course: courses)
                System.out.println(course);
            System.out.println("Read data successfully");

            int duration = courses.get(0).getDurationMinutes();
            courses.get(0).setDurationMinutes(Math.max((duration - 300), 0));
            updateData(connection, courses.get(0));

            courses.get(1).setTitle("MySQL");
            updateData(connection, courses.get(1));
            System.out.println("Update data successfully");

            courses = readData(connection);
            for (var course: courses)
                System.out.println(course);
            System.out.println("Read data successfully");

            deleteData(connection, 3);
            System.out.println("Delete data successfully");

            courses = readData(connection);
            for (var course: courses)
                System.out.println(course);
            System.out.println("Read data successfully");
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        System.out.println("Finish");

    }

    private static void createDatabase(Connection connection) throws SQLException {
        String createDatabaseSQL =  "CREATE DATABASE IF NOT EXISTS schoolDB;";
        try (PreparedStatement statement = connection.prepareStatement(createDatabaseSQL)) {
            statement.execute();
        }
    }

    private static void useDatabase(Connection connection) throws SQLException {
        String useDatabaseSQL =  "USE schoolDB;";
        try (PreparedStatement statement = connection.prepareStatement(useDatabaseSQL)) {
            statement.execute();
        }
    }

    private static void createTable(Connection connection) throws SQLException {
        String createTableSQL =
                "CREATE TABLE IF NOT EXISTS courses (" +
                "id INT AUTO_INCREMENT NOT NULL PRIMARY KEY, " +
                "title VARCHAR(255) NOT NULL, " +
                "duration INT NOT NULL);";
        try (PreparedStatement statement = connection.prepareStatement(createTableSQL)) {
            statement.execute();
        }
    }

    private static void insertData(Connection connection, Course student) throws SQLException {
        String insertDataSQL = "INSERT INTO courses (title, duration) VALUES (?, ?);";
        try (PreparedStatement statement = connection.prepareStatement(insertDataSQL)) {
            statement.setString(1, student.getTitle());
            statement.setInt(2, student.getDurationMinutes());
            statement.executeUpdate();
        }
    }

    private static List<Course> readData(Connection connection) throws SQLException {
        ArrayList<Course> coursesList = new ArrayList<>();
        String readDataSQL = "SELECT * FROM courses;";
        try (PreparedStatement statement = connection.prepareStatement(readDataSQL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                int duration = resultSet.getInt("duration");
                coursesList.add(new Course(id, title, duration));
            }
            return coursesList;
        }
    }

    private static void updateData(Connection connection, Course course) throws SQLException {
        String updateDataSQL = "UPDATE courses SET title=?, duration=? WHERE id=?;";
        try (PreparedStatement statement = connection.prepareStatement(updateDataSQL)) {
            statement.setString(1, course.getTitle());
            statement.setInt(2, course.getDurationMinutes());
            statement.setInt(3, course.getId());
            statement.executeUpdate();
        }
    }

    private static void deleteData(Connection connection, int id) throws SQLException {
        String deleteDataSQL = "DELETE FROM courses WHERE id=?;";
        try (PreparedStatement statement = connection.prepareStatement(deleteDataSQL)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

}

/*
Database created successfully
Use database successfully
Create table successfully
Insert data successfully
Course{id=1, title='Docker', duration=2900 min}
Course{id=2, title='SQL', duration=900 min}
Course{id=3, title='Java Junior', duration=210 min}
Read data successfully
Update data successfully
Course{id=1, title='Docker', duration=2600 min}
Course{id=2, title='MySQL', duration=900 min}
Course{id=3, title='Java Junior', duration=210 min}
Read data successfully
Delete data successfully
Course{id=1, title='Docker', duration=2600 min}
Course{id=2, title='MySQL', duration=900 min}
Read data successfully
Finish
 */
