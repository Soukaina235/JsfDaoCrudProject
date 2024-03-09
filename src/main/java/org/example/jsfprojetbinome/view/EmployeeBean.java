package org.example.jsfprojetbinome.view;

import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.SessionScoped;
import jakarta.faces.component.html.HtmlCommandButton;
import org.example.jsfprojetbinome.model.Departement;
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
    private Employee employee;
    private boolean showNewEmployeeRow;
    private List<Employee> displayedEmployees;
    private int currentPage = 1;
    private int pageSize = 6;

    public EmployeeBean(){
        employeeService = new EmployeeService();
        employee = new Employee();
        employees = employeeService.findAllService();
        deletebutton = new HtmlCommandButton();
        addbutton = new HtmlCommandButton();
        editbutton = new HtmlCommandButton();
        savechangesbutton = new HtmlCommandButton();
        savechangesbutton.setDisabled(true);
        anyEmployeeEditable = true;
        showNewEmployeeRow = false;

        emails = new HashMap<>();


        for (Employee employee : employees) {
            employee.setEditable(false);
        }
    }
    
    public List<Employee> getDisplayedEmployees() {
        return displayedEmployees;
    }

    public void setDisplayedEmployees(List<Employee> displayedEmployees) {
        this.displayedEmployees = displayedEmployees;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public boolean isShowNewEmployeeRow() {
        return showNewEmployeeRow;
    }

    public void setShowNewEmployeeRow(boolean showNewEmployeeRow) {
        this.showNewEmployeeRow = showNewEmployeeRow;
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

    public Map<Integer, String> getEmails() {
        return emails;
    }

    public void setEmails(Map<Integer, String> emails) {
        this.emails = emails;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
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

    public void toggleNewEmployeeRow() {
        showNewEmployeeRow = !showNewEmployeeRow;
        savechangesbutton.setDisabled(false);
        addbutton.setDisabled(true);
    }

    public void saveChanges() {
        for (Employee employee : employees) {
            if (employee.isEditable()) {
                employeeService.editService(employee, emails.get(employee.getId()));
                employee.setEditable(false);
            }
        }

        if (showNewEmployeeRow) {
            employeeService.saveService(employee);
            employees.add(employee);
            employee = new Employee();
            showNewEmployeeRow = false;
        }

        emails = new HashMap<>();
        anyEmployeeEditable = false;

        savechangesbutton.setRendered(true);
        savechangesbutton.setDisabled(true);
    }

    //PAGINATION
    public void loadEmployees() {
        int startIndex = (currentPage - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, employees.size());
        displayedEmployees = employees.subList(startIndex, endIndex);
    }

    public void nextPage() {
        if (hasNextPage()) {
            currentPage++;
            loadEmployees();
        }
    }

    public void previousPage() {
        if (hasPreviousPage()) {
            currentPage--;
            loadEmployees();
        }
    }

    public boolean hasNextPage() {
        return currentPage < getTotalPages();
    }

    public boolean hasPreviousPage() {
        return currentPage > 1;
    }

    public int getTotalPages() {
        return (int) Math.ceil((double) employees.size() / pageSize);
    }

//    public void saveChanges() {
//
//
////        for (EmployeeDTO employeeDTO : employees) {
////            if (employeeDTO.isEditable()) {
////                employeeService.editService(employeeDTO);
////                employeeDTO.setEditable(false);
////
////                System.out.println("save changes: " + employeeDTO);
////
////            }
////        }
//
//        for (Employee employee : employees) {
//            if (employee.isEditable()) {
//                employeeService.editService(employee, emails.get(employee.getId()));
//                employee.setEditable(false);
//            }
//
//        }
//
//        emails = new HashMap<>();
//        anyEmployeeEditable = false;
//
//        savechangesbutton.setRendered(true);
//        savechangesbutton.setDisabled(true);
//    }
//
//
//
//    public void saveChanges() {
//        employeeService.saveService(employee);
//        employees.add(employee);
//        employee = new Employee();
//        showNewEmployeeRow = false;
//    }

}
