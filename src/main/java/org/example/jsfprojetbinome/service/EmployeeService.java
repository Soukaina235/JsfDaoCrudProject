package org.example.jsfprojetbinome.service;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import org.example.jsfprojetbinome.dao.EmployeeDAO;
import org.example.jsfprojetbinome.dao.impl.EmployeeDAOImpl;
import org.example.jsfprojetbinome.model.Employee;

import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class EmployeeService {
    EmployeeDAO employeeDAO;

    public EmployeeService(){
        this.employeeDAO = new EmployeeDAOImpl();
    }

    public void saveService(Employee employee){
        boolean isunique = employeeDAO.isEmailUnique(employee.getEmail());
        FacesMessage msg;

        ResourceBundle bundle = ResourceBundle.getBundle("i18n.labels");

        if(isunique) {
            int result = employeeDAO.save(employee);
            if(result > 0){
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("employee.save"),null);
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("employee.SaveFailed"),null);
            }
        }else {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("employee.Emailexist"),null);
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void editService(Employee employee, String email) {
        boolean isunique = employeeDAO.isEmailUnique(employee.getEmail()) || Objects.equals(employee.getEmail(), email);
        //boolean isunique = true;
        //boolean isunique = employeeDAO.isEmailUnique(employee.getEmail());

        ResourceBundle bundle = ResourceBundle.getBundle("i18n.labels");
        FacesMessage msg;

        if(isunique) {
            boolean result = employeeDAO.edit(employee);
            if (result) {
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("employee.edit"), null);

            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("employee.edit.failed"), null);
            }
        }else {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("employee.Emailexist"),null);
        }

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public List<Employee> findAllService(){
        List<Employee> employees = employeeDAO.findAll();
        return employees;
    }

    public void deleteService(Employee employee) {
        boolean result = employeeDAO.delete(employee);
        ResourceBundle bundle = ResourceBundle.getBundle("i18n.labels");
        FacesMessage msg;

        if (result) {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("employee.delete"), null);

        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("employee.delete.failed"), null);
        }

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
