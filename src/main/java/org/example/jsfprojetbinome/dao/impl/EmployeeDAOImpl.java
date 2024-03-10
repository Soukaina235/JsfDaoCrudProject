package org.example.jsfprojetbinome.dao.impl;

import org.example.jsfprojetbinome.dao.EmployeeDAO;
import org.example.jsfprojetbinome.model.Departement;
import org.example.jsfprojetbinome.model.Employee;
import org.example.jsfprojetbinome.util.ConnectDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Implementation of the EmployeeDAO interface for database operations related to employees.
 */
public class EmployeeDAOImpl implements EmployeeDAO {

    /**
     * Saves an employee to the database.
     *
     * @param employee The employee to be saved.
     * @return The number of rows affected by the insert operation.
     */
    public int save(Employee employee) {
        ConnectDB db = new ConnectDB();
        Connection connection = db.getConnection();
        String sql = "INSERT INTO employee (firstname,lastname, email,departement,  birthdate) VALUES(?, ?, ?, ?, ?)";

        java.util.Date utilDate = employee.getBirthdate();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        int result = 0;

        try {

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, employee.getFirstname());
            statement.setString(2, employee.getLastname());
            statement.setString(3, employee.getEmail());
            statement.setString(4, employee.getDepartement().name());
            statement.setDate(5, sqlDate);

            result = statement.executeUpdate();

        } catch(SQLException e) {
            e.printStackTrace();
        }
        db.closeConnection();

        return result;
    }


    /**
     * Deletes an employee from the database.
     *
     * @param employee The employee to be deleted.
     * @return True if the deletion is successful, otherwise false.
     */
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


    /**
     * Finds an employee by their unique identifier.
     *
     * @param id The unique identifier of the employee.
     * @return The employee object if found, otherwise null.
     */
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

    /**
     * Finds an employee by their email address.
     *
     * @param email The email address of the employee.
     * @return The employee object if found, otherwise null.
     */
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

    /**
     * Updates an existing employee record in the database.
     *
     * @param employee The employee object containing updated information.
     * @return True if the update operation is successful, otherwise false.
     */
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


    /**
     * Retrieves all employees from the database.
     *
     * @return A list of all employees in the database.
     */
    @Override
    public List<Employee> findAll() {
        ConnectDB db = new ConnectDB();
        Connection connection = new ConnectDB().getConnection();
        String sql = "SELECT * FROM employee";
        List<Employee> employees = new ArrayList<>();

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

    /**
     * Checks if an email address is unique in the database.
     *
     * @param email The email address to be checked.
     * @return True if the email address is unique, otherwise false.
     */
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