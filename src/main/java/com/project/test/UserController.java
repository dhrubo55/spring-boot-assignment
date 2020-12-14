package com.project.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {

    final ParentService parentService;

    public UserController(ParentService parentService) {
        this.parentService = parentService;
    }

    @GetMapping("/parent")
    public ResponseEntity<List<Parent>> getAllParent() {
        return new ResponseEntity<>(parentService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/parent/{id}")
    public ResponseEntity<Parent> getParentById(@PathVariable("id") Long id) throws Exception {
        return new ResponseEntity<>(parentService.findById(id), HttpStatus.OK);
    }

    @PostMapping("/parent")
    public ResponseEntity<Parent> createParent(@RequestBody Parent parent) {
        return new ResponseEntity<>(parentService.save(parent),HttpStatus.CREATED);
    }

    @PutMapping("/parent/{id}")
    public ResponseEntity<Parent> updateParent(@PathVariable long id, @RequestBody Parent parent) {
        return new ResponseEntity<>(parentService.update(id,parent), HttpStatus.OK);
    }

    @DeleteMapping("/parent/{id}")
    public void deleteParent(@PathVariable long id) {
        parentService.delete(id);
    }
}
