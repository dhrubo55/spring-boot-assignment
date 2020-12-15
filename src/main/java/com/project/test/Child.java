package com.project.test;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Child {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column(name = "parent_id", nullable = false)
    public Long parentId;
    @Column(name = "firstName")
    public String firstName;
    @Column(name = "lastName")
    public String lastName;

    public Child(Long parentId, String firstName, String lastName) {
        this.parentId = parentId;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
