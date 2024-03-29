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

    // Declaration of class attributes
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
    private int currentPage;
    private int pageSize;

    private String searchField;


    // Constructor of the class
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
        addbutton.setDisabled(false);

        emails = new HashMap<>();


        for (Employee employee : employees) {
            employee.setEditable(false);
        }

        currentPage = 1;
        pageSize = 6;
        loadEmployees();
    }

    //Getters and Setters of class

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


    // Method to perform the deletion of an employee

    public void delete(Employee employee){
        employeeService.deleteService(employee);
        search();
        loadEmployees();
    }

    // Method to initiate the editing of an employee
    public String edit(Employee employee){
        deletebutton.setRendered(false);
        editbutton.setRendered(false);
        employee.setEditable(true);
        addbutton.setDisabled(true);
        savechangesbutton.setDisabled(false);

        emails.put(employee.getId(), employee.getEmail());

        return null;
    }

    // Method to toggle the display of the new employee addition row
    public void toggleNewEmployeeRow() {
        showNewEmployeeRow = !showNewEmployeeRow;
        savechangesbutton.setDisabled(false);
        addbutton.setDisabled(true);
    }

    // Method to save changes made to employees
    public void saveChanges() {
        for (Employee employee : employees) {
            if (employee.isEditable()) {
                employeeService.editService(employee, emails.get(employee.getId()));
                employee.setEditable(false);
                addbutton.setDisabled(false);
            }
        }

        if (showNewEmployeeRow) {
            employeeService.saveService(employee);
            employees.add(employee);
            employee = new Employee();
            showNewEmployeeRow = false;
            addbutton.setDisabled(false);
        }

        loadEmployees();

        emails = new HashMap<>();
        anyEmployeeEditable = false;

        savechangesbutton.setRendered(true);
        savechangesbutton.setDisabled(true);
    }




    //PAGINATION

    // Method to load employees based on pagination
    public void loadEmployees() {
        List<Employee> copiedEmployees = new ArrayList<>(employees);
        int startIndex = (currentPage - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, copiedEmployees.size());
        displayedEmployees = copiedEmployees.subList(startIndex, endIndex);
    }



    // Method to move to the next page in pagination
    public void nextPage() {
        if (hasNextPage()) {
            currentPage++;
            loadEmployees();
        }
    }

    // Method to move to the previous page in pagination
    public void previousPage() {
        if (hasPreviousPage()) {
            currentPage--;
            loadEmployees();
        }
    }

    // Method to check if there is a next page in pagination
    public boolean hasNextPage() {
        return currentPage < getTotalPages();
    }

    // Method to check if there is a previous page in pagination
    public boolean hasPreviousPage() {
        return currentPage > 1;
    }

    // Method to calculate the total number of pages for pagination
    public int getTotalPages() {
        return (int) Math.ceil((double) employees.size() / pageSize);
    }

    // Method to get the search field value
    public String getSearchField() {
        return searchField;
    }

    // Method to set the search field value
    public void setSearchField(String searchField) {
        this.searchField = searchField;
    }

    // Method to search for employees based on the search field value
    public void search() {
        List<Employee> searchedEmployees = new ArrayList<>();

        employees = employeeService.findAllService();

        System.out.println("Search field : " + searchField);

        if (searchField != null && !searchField.isEmpty()) {
            for (Employee employee : employees) {
                boolean matchFirstName = employee.getFirstname().toLowerCase().contains(searchField.toLowerCase());
                boolean matchLastName = employee.getLastname().toLowerCase().contains(searchField.toLowerCase());
                boolean matchEmail = employee.getEmail().toLowerCase().contains(searchField.toLowerCase());
                boolean matchDepartement = employee.getDepartement().toString().toLowerCase().contains(searchField.toLowerCase());
                boolean matchBirthdate = employee.getBirthdate().toString().contains(searchField.toLowerCase());

                System.out.println(employee.getBirthdate().toString());

                if ( matchFirstName ||
                        matchLastName ||
                        matchEmail ||
                        matchDepartement ||
                        matchBirthdate
                ) {
                    searchedEmployees.add(employee);

                    System.out.println(employee);
                }

                employees = new ArrayList<>(searchedEmployees);
            }

        }

        loadEmployees();
    }

}
