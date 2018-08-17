package com.jaiswalakshay.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Data
@Entity
@Table(schema = "public", name = "users")
public class User {

    @Id
    String id;
    String username;
    String contactNo;
    String firstname;
    String lastname;

    public User(){
        this.id = UUID.randomUUID().toString();
    }

}
