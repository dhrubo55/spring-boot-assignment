package com.project.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class ParentServiceTest {
    @Autowired
    ParentRepository parentRepository;

    Address addressOfParentExpected = new Address(
            "22th Street",
            "Boston",
            "Virginia",
            "1212");
    Parent parentExpected = new Parent("User","Name",addressOfParentExpected);

    @Test
    void getAllParents() {
        parentRepository.save(parentExpected);
        ParentService parentService = new ParentService(parentRepository);

        Parent parentActual = parentService.findAll().get(0);

        assertEquals(parentExpected.firstName, parentActual.firstName);
        assertEquals(parentExpected.lastName,parentActual.lastName);
        assertEquals(parentExpected.address.addressId,parentActual.address.addressId);
    }

    @Test
    void getParentById() throws Exception {
        parentRepository.save(parentExpected);
        ParentService parentService = new ParentService(parentRepository);
        Parent parentActual = parentService.findById(1L);

        assertEquals(parentExpected.firstName, parentActual.firstName);
    }

    @Test
    void saveParent() throws Exception {
        ParentService parentService = new ParentService(parentRepository);
        Parent parentActual = parentService.save(parentExpected);

        assertEquals(parentExpected.firstName,parentActual.firstName);
    }

    @Test
    void updateParent() throws Exception {
        parentRepository.save(parentExpected);
        ParentService parentService = new ParentService(parentRepository);
        Parent parentActual = parentService.update(
                1L,
                new Parent(
                        "firstName",
                        "lastName",
                        addressOfParentExpected
                ));

        assertEquals("firstName",parentActual.firstName);

    }

    @Test
    void deleteParent() throws Exception {
        parentRepository.save(parentExpected);
        ParentService parentService = new ParentService(parentRepository);
        parentService.delete(1L);

        try {
            Parent parentActual = parentService.findById(1L);
            assertEquals(parentExpected.firstName, parentActual.firstName);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Parent with id 1 doesnt exist");
        }

    }
}
