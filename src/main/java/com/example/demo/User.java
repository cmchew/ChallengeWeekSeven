package com.example.demo;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name="user_table")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column (name = "username")
   // @Size (min=2)
    private String username;

    @Column (name = "email")
//
   // @NotNull
    private String email;

    @Column (name = "password")
    private String password;

    @Column (name = "first_name")
   // @NotEmpty
   // @NotNull
    private String firstName;

    @Column (name = "last_name")
   // @NotEmpty
    //@NotNull
    private String lastName;

    @Column (name = "enabled")
    private boolean enabled;

//    @Column (name = "job_title")
//    private String jobTitle;

    private String borrow;
    private String headshot;

    @ManyToOne
    private Libro libro;

    public User(){

    }
    public User(@Size(min=2) String username,
                     String email,
                     String password,
                    String firstName,
                    String lastName, boolean enabled) {
        this.username = username;
        this.email = email;
        this.setPassword(password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.enabled = enabled;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void clearPasswords(){
        this.password = "";
    }

    public String getBorrow() {
        return borrow;
    }

    public String getHeadshot() {
        return headshot;
    }

    public void setHeadshot(String headshot) {
        this.headshot = headshot;
    }

    public void setBorrow(String borrow) {
        this.borrow = borrow;
    }

    public Libro getLibro(){ return libro;}

    public void setLibro(Libro libro){
        this.libro = libro;
    }



}
