package com.project.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ChildServiceTest {
    @Autowired
    private ChildRepository childRepository;

    Child childExpected = new Child(1L,"User","Name");

    @Test
    void getAllChild() {
        childRepository.save(childExpected);
        ChildService childService = new ChildService(childRepository);
        Child childActual = childService.findAll().get(0);

        assertEquals(childExpected.parentId,childActual.parentId);
        assertEquals(childExpected.firstName, childActual.firstName);
        assertEquals(childExpected.lastName, childActual.lastName);
    }

    @Test
    void getChildById() throws Exception {
        childRepository.save(childExpected);
        ChildService childService = new ChildService(childRepository);
        Child childActual = childService.getById(1L);

        assertEquals(childExpected.parentId, childActual.parentId);
    }

    @Test
    void saveChild() {
        ChildService childService = new ChildService(childRepository);
        Child childActual = childService.save(childExpected);

        assertEquals(childExpected.firstName,childActual.firstName);
    }

    @Test
    void updateChild() {
        childRepository.save(childExpected);
        Child child = new Child(1L, "FirstName", "LastName");
        ChildService childService = new ChildService(childRepository);
        Child childActual = childService.update(1L,child);

        assertEquals(child.firstName, childActual.firstName);
    }

    @Test
    void deleteChild() {
        childRepository.save(childExpected);
        ChildService childService = new ChildService(childRepository);
        childService.delete(1L);

        try {
            Child childActual = childService.getById(1L);
            assertEquals(childExpected.firstName, childActual.firstName);
        } catch (Exception e) {
            assertEquals(e.getMessage(),"Child with id 1 not found");
        }
    }
}
