package org.example.jsfprojetbinome.dao;

import org.example.jsfprojetbinome.model.Employee;

import java.util.List;

public interface EmployeeDAO {
    int save(Employee employee);
    boolean delete(Employee employee);
    boolean edit(Employee employee);
    List<Employee> findAll();
    Employee findById(int id);
    Employee findByEmail(String email);
    boolean isEmailUnique(String email);
}
