package com.project.test;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "address_id")
    public Long addressId;
    @Column(name = "street_name")
    public String streetName;
    @Column(name = "city")
    public String city;
    @Column(name = "state")
    public String state;
    @Column(name = "zip_code")
    public String zipCode;

    public Address(String streetName, String city, String state, String zipCode) {
        this.streetName = streetName;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }

    public Address() {

    }
}
