package org.example.jsfprojetbinome.model;

import java.util.Date;

public class Employee {

    // Declaration of class attributes
    private int id ;
    private String firstname;
    private String lastname;
    private String email;
    private Departement departement;
    private Date birthdate;
    private boolean editable;


    // Contructors
    public Employee(int id, String firstname, String lastname, String email, Departement departement, Date birthdate) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.departement = departement;
        this.birthdate = birthdate;
    }

    public Employee(String firstname, String lastname, String email, Departement departement, Date birthdate) {
        this.id = 0;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.departement = departement;
        this.birthdate = birthdate;
    }

    public Employee() {
        super();
    }


    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Departement getDepartement() {
        return departement;
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    // Method toString
    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", departement=" + departement +
                ", birthdate=" + birthdate +
                ", editable=" + editable +
                '}';
    }
}
