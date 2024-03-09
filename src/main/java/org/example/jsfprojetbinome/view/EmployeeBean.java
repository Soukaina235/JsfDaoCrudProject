package org.example.jsfprojetbinome.view;

import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.SessionScoped;
import jakarta.faces.component.html.HtmlCommandButton;
import org.example.jsfprojetbinome.model.Employee;
import org.example.jsfprojetbinome.service.EmployeeService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ManagedBean(name="employeeBean")
@SessionScoped
public class EmployeeBean {
    private List<Employee> employees ;
    private EmployeeService employeeService;
    private HtmlCommandButton deletebutton;
    private HtmlCommandButton editbutton;
    private HtmlCommandButton addbutton;
    private HtmlCommandButton savechangesbutton;
    private boolean anyEmployeeEditable;
    private Map<Integer, String> emails;


    public EmployeeBean(){
        employeeService = new EmployeeService();
        employees = employeeService.findAllService();
        deletebutton = new HtmlCommandButton();
        addbutton = new HtmlCommandButton();
        editbutton = new HtmlCommandButton();
        savechangesbutton = new HtmlCommandButton();
        savechangesbutton.setDisabled(true);
        anyEmployeeEditable = true;

        emails = new HashMap<>();


        for (Employee employee : employees) {
            employee.setEditable(false);
        }
    }

    public HtmlCommandButton getDeletebutton() {
        return deletebutton;
    }

    public void setDeletebutton(HtmlCommandButton deletebutton) {
        this.deletebutton = deletebutton;
    }

    public HtmlCommandButton getEditbutton() {
        return editbutton;
    }

    public void setEditbutton(HtmlCommandButton editbutton) {
        this.editbutton = editbutton;
    }

    public HtmlCommandButton getAddbutton() {
        return addbutton;
    }

    public void setAddbutton(HtmlCommandButton addbutton) {
        this.addbutton = addbutton;
    }

    public HtmlCommandButton getSavechangesbutton() {
        return savechangesbutton;
    }

    public void setSavechangesbutton(HtmlCommandButton savechangesbutton) {
        this.savechangesbutton = savechangesbutton;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public EmployeeService getEmployeeService() {
        return employeeService;
    }

    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    public boolean isAnyEmployeeEditable() {
        return anyEmployeeEditable;
    }

    public void setAnyEmployeeEditable(boolean anyEmployeeEditable) {
        this.anyEmployeeEditable = anyEmployeeEditable;
    }

    public void delete(Employee employee){
        employeeService.deleteService(employee);
        employees = employeeService.findAllService();
    }
    public String edit(Employee employee){

        deletebutton.setRendered(false);
        editbutton.setRendered(false);
        employee.setEditable(true);
        savechangesbutton.setDisabled(false);

        emails.put(employee.getId(), employee.getEmail());


        System.out.println(employee);

        return null;

    }

    public void saveChanges() {


//        for (EmployeeDTO employeeDTO : employees) {
//            if (employeeDTO.isEditable()) {
//                employeeService.editService(employeeDTO);
//                employeeDTO.setEditable(false);
//
//                System.out.println("save changes: " + employeeDTO);
//
//            }
//        }

        for (Employee employee : employees) {
            if (employee.isEditable()) {
                employeeService.editService(employee, emails.get(employee.getId()));
                employee.setEditable(false);
            }

        }

        emails = new HashMap<>();
        anyEmployeeEditable = false;

        savechangesbutton.setRendered(true);
        savechangesbutton.setDisabled(true);
    }

}
