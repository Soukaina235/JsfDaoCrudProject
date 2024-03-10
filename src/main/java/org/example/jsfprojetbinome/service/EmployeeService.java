package org.example.jsfprojetbinome.service;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import org.example.jsfprojetbinome.dao.EmployeeDAO;
import org.example.jsfprojetbinome.dao.impl.EmployeeDAOImpl;
import org.example.jsfprojetbinome.model.Employee;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;


/**
 * Service class for managing employee-related operations.
 */
public class EmployeeService {
    EmployeeDAO employeeDAO;


    /**
     * Constructor to initialize the EmployeeService with a default EmployeeDAO implementation.
     */
    public EmployeeService(){
        this.employeeDAO = new EmployeeDAOImpl();
    }



    /**
     * Adds an error message to the FacesContext.
     * @param message The error message to be added.
     */
    private void addErrorMessage(String message) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
    }

    /**
     * Adds a warning message to the FacesContext.
     * @param message The warning message to be added.
     */
    private void addWarningMessage(String message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, message, null));
    }

    /**
     * Adds a success message to the FacesContext.
     * @param message The success message to be added.
     */
    private void addSuccessMessage(String message) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, message, null));
    }

    /**
     * Saves an employee.
     * @param employee The employee to be saved.
     */
    public void saveService(Employee employee) {
        boolean isUnique = employeeDAO.isEmailUnique(employee.getEmail());
        ResourceBundle bundle = ResourceBundle.getBundle("i18n.labels");

        if (isUnique) {
            int result = employeeDAO.save(employee);
            if (result > 0) {
                addSuccessMessage(bundle.getString("employee.save"));
            } else {
                addErrorMessage(bundle.getString("employee.SaveFailed"));
            }
        } else {
            addWarningMessage(bundle.getString("employee.Emailexist"));
        }
    }

    /**
     * Edits an employee.
     * @param employee The employee to be edited.
     * @param email The email of the employee before editing.
     */
    public void editService(Employee employee, String email) {
        boolean isunique = employeeDAO.isEmailUnique(employee.getEmail()) || Objects.equals(employee.getEmail(), email);

        ResourceBundle bundle = ResourceBundle.getBundle("i18n.labels");

        if(isunique) {
            boolean result = employeeDAO.edit(employee);
            if (result) {
                addSuccessMessage(bundle.getString("employee.edit"));
            } else {
                addErrorMessage(bundle.getString("employee.edit.failed"));
            }
        }else {
            addWarningMessage(bundle.getString("employee.Emailexist"));
        }
    }

    /**
     * Retrieves all employees.
     * @return A list of all employees.
     */
    public List<Employee> findAllService(){
        List<Employee> employees = employeeDAO.findAll();
        return employees;
    }

    /**
     * Deletes an employee.
     * @param employee The employee to be deleted.
     */
    public void deleteService(Employee employee) {
        boolean result = employeeDAO.delete(employee);
        ResourceBundle bundle = ResourceBundle.getBundle("i18n.labels");

        if (result) {
            addSuccessMessage(bundle.getString("employee.delete"));
        } else {
            addErrorMessage(bundle.getString("employee.delete.failed"));
        }
    }

}
