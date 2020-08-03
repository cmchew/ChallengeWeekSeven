package com.example.demo;


import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;

//    @NotNull
//    @Size (min=3)
//    private String hr;
//
//    @NotNull
//    @Size (min=3)
//    private String it;
//
//    @NotNull
//    @Size (min=3)
//    private String sales;
//
//    @NotNull
//    @Size (min=3)
//    private String art;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Employee> employees;

    public Department() {
    }

//    public Department(@NotNull @Size(min = 3) String hr, @NotNull @Size(min = 3) String it, @NotNull @Size(min = 3) String sales, @NotNull @Min(3) String art) {
//        this.hr = hr;
//        this.it = it;
//        this.sales = sales;
//        this.art = art;
//    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



//    public String getHr() {
//        return hr;
//    }
//
//    public void setHr(String hr) {
//        this.hr = hr;
//    }
//
//    public String getIt() {
//        return it;
//    }
//
//    public void setIt(String it) {
//        this.it = it;
//    }
//
//    public String getSales() {
//        return sales;
//    }
//
//    public void setSales(String sales) {
//        this.sales = sales;
//    }
//
//    public String getArt() {
//        return art;
//    }
//
//    public void setArt(String art) {
//        this.art = art;
//    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    public void addempl(Employee empl) {
        if (this.employees == null) {
            this.employees = new HashSet<Employee>();
        }
        this.employees.add(empl);
    }
}


