package com.project.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UserController {
    private final ParentService parentService;
    private final ParentRepository parentRepository;

    public UserController(ParentService parentService, ParentRepository parentRepository) {
        this.parentService = parentService;
        this.parentRepository = parentRepository;
    }

    @GetMapping("/parent")
    public ResponseEntity<List<Parent>> getAllParents() {
        Address address = new Address(
                "11th Street",
                "boston",
                "virginia",
                "1212"
        );
        Parent parent = new Parent("firstName","secondName", address);
        parentRepository.save(parent);
        return new ResponseEntity<>(parentService.findAll(), HttpStatus.OK);
    }
}
