package org.example.jsfprojetbinome.dao.impl;

import org.example.jsfprojetbinome.dao.EmployeeDAO;
import org.example.jsfprojetbinome.model.Departement;
import org.example.jsfprojetbinome.model.Employee;
import org.example.jsfprojetbinome.util.ConnectDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {
    // internationalization + input, edit, delete activate desactivate....

    public boolean delete(Employee employee) {
        ConnectDB db = new ConnectDB();
        Connection connection = new ConnectDB().getConnection();
        String sql = "DELETE FROM employee WHERE id=?";
        boolean result = false;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, employee.getId());

            result = statement.executeUpdate() > 0;

        } catch(SQLException e) {
            e.printStackTrace();
        }
        db.closeConnection();

        return result;
    }


    public int save(Employee employee) {
        ConnectDB db = new ConnectDB();
        Connection connection = db.getConnection();
        String sql = "INSERT INTO employee (firstname,lastname, email,departement) VALUES(?, ?, ?, ?)";

        int result = 0;

        try {

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, employee.getEmail());
            statement.setString(2, employee.getFirstname());
            statement.setString(3, employee.getLastname());
            statement.setString(4, employee.getDepartement().name());

            result = statement.executeUpdate();

        } catch(SQLException e) {
            e.printStackTrace();
        }
        db.closeConnection();

        return result;
    }

//    public int save(Employee employee) {
//        ConnectDB db = new ConnectDB();
//        Connection connection = db.getConnection();
//        String sql = "INSERT INTO employee (firstname,lastname, email,departement,  birthdate) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
//
//        java.util.Date utilDate = employee.getBirthdate();
//        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
//        int result = 0;
//
//        try {
//
//            PreparedStatement statement = connection.prepareStatement(sql);
//
//            statement.setString(1, employee.getEmail());
//            statement.setString(2, employee.getFirstname());
//            statement.setString(3, employee.getLastname());
//            statement.setString(4, employee.getEmail());
//            statement.setString(5, employee.getDepartement().name());
//            statement.setDate(6, sqlDate);
//
//            result = statement.executeUpdate();
//
//        } catch(SQLException e) {
//            e.printStackTrace();
//        }
//        db.closeConnection();
//
//        return result;
//    }

    public Employee findById(int id){
        ConnectDB db = new ConnectDB();
        Connection connection;
        String sql = "SELECT firstname, lastname, email, departement, birthdate FROM employee WHERE id = ?";
        Employee employee = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            connection = db.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            rs = statement.executeQuery();
            if (rs.next()) {
                employee = new Employee(rs.getInt("id"),
                        rs.getString(("firstname")),
                        rs.getString("lastname"),
                        rs.getString("email"),
                        Departement.valueOf(rs.getString("departement")),
                        rs.getDate("birthdate")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        db.closeConnection();

        return employee;
    }

    public Employee findByEmail(String email){
        ConnectDB db = new ConnectDB();
        Connection connection = new ConnectDB().getConnection();
        String sql = "SELECT * FROM employee WHERE email LIKE ?";
        Employee employee = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            connection = db.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            rs = statement.executeQuery();
            if (rs.next()) {
                employee = new Employee(
                        rs.getInt("id"),
                        rs.getString(("firstname")),
                        rs.getString("lastname"),
                        rs.getString("email"),
                        Departement.valueOf(rs.getString("departement")),
                        rs.getDate("birthdate")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        db.closeConnection();

        return employee;
    }

    public boolean edit(Employee employee){
        ConnectDB db = new ConnectDB();
        Connection connection = new ConnectDB().getConnection();
        String sql = "UPDATE employee SET firstname=?, lastname=?, email=?, departement=?, birthdate=? WHERE id=?";
        boolean result = false;

        try {
            java.util.Date utilDate = employee.getBirthdate();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, employee.getFirstname());
            statement.setString(2, employee.getLastname());
            statement.setString(3, employee.getEmail());
            statement.setString(4, employee.getDepartement().toString());
            statement.setDate(5, sqlDate);
            statement.setInt(6, employee.getId());

            result = statement.executeUpdate() > 0;

        } catch(SQLException e) {
            e.printStackTrace();
        }
        db.closeConnection();

        return result;
    }


    @Override
    public List<Employee> findAll() {
        ConnectDB db = new ConnectDB();
        Connection connection = new ConnectDB().getConnection();
        String sql = "SELECT * FROM employee";
        List<Employee> employees = new ArrayList<>();

        int id  = -1;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                employees.add(new Employee(
                        rs.getInt("id"),
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getString("email"),
                        Departement.valueOf(rs.getString("departement")),
                        rs.getDate("birthdate")
                ));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        db.closeConnection();

        return employees;
    }
    @Override
    public boolean isEmailUnique(String email){
        ConnectDB db = new ConnectDB();
        Connection connection;
        String sql = "SELECT * FROM employee WHERE email LIKE ?";
        boolean unique = true;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            connection = db.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            rs = statement.executeQuery();
            if (rs.next()) {
                unique = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return unique;
    }
}