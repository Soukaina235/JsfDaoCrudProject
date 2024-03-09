package org.example.jsfprojetbinome.dao;

import org.example.jsfprojetbinome.model.Employee;

import java.util.List;

public interface EmployeeDAO {
    public int save(Employee employee);
    public boolean delete(Employee employee);
    public boolean edit(Employee employee);
    public List<Employee> findAll();
    public Employee findById(int id);
    public Employee findByEmail(String email);
    public boolean isEmailUnique(String email);
}
