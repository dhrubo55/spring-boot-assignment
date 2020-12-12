package com.project.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ParentServiceTest {
    @Autowired
    private ParentRepository parentRepository;

    @Test
    void getAllParents() {
        Address address = new Address(
                "11th Street",
                "boston",
                "virginia",
                "1219"
                );
        Parent parentExpected = new Parent("User","Type 1",address);
        parentRepository.save(parentExpected);
        ParentService parentService = new ParentService(parentRepository);
        Parent parentActual = parentService.findAll().get(0);

        //assertEquals(parents.size(),1);
        assertEquals(parentExpected.firstName,parentActual.firstName);
        assertEquals(parentExpected.lastName,parentActual.lastName);
        assertEquals(parentExpected.address.addressId,parentActual.address.streetName);
    }
}
