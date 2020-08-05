package com.super_horizon.lemmein.models;

import java.util.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.*;
// import org.springframework.data.mongodb.core.mapping.DBRef;


@Document(collection = "users")
public class User {
    
    @Id
    private String id;

    private String username;

    private String password;

    @NotNull
    private Discount discount;

    private List<String> customersRef = new ArrayList<String>();

    // @DBRef
    // private Set<Role> roles = new HashSet<>();


    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.discount = new Discount(EDiscountBy.valueOf(0).get().toString(),0,0,0);
    }

    public void setId (String id) {
        this.id = id;
    }

    public String getId () {
        return this.id;
    }

    public void setUsername (String username) {
        this.username = username;
    }

    public String getUsername () {
        return this.username;
    }

    public void setPassword (String password) {
        this.password = password;
    }

    public String getPassword () {
        return this.password;
    }

    public void setDiscount (Discount discount) {
        this.discount = discount;
    }

    public Discount getDiscount () {
        return this.discount;
    }

    public void setCustomersRef (String customerRef) {
        // if (this.customersRef == null) {
        //     this.customersRef = new ArrayList<String>();
        // }
        this.customersRef.add(customerRef);
    }

    public List<String> getCustomersRef () {
        return this.customersRef;
    }
    
    // public Set<Role> getRoles() {
    //     return roles;
    // }
    
    // public void setRoles(Set<Role> roles) {
    //     this.roles = roles;
    // }

}