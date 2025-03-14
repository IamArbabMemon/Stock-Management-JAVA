//package com.example.Stock_Management.users;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.Table;
//
//@Entity
//@Table(name="users")
//public class Users {
//
//
//
//}
package com.example.Stock_Management.users;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name="users")
@AllArgsConstructor
@Data
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String email;
    private String name,password,role;
    private Date dob;

    public Users(){}
}
