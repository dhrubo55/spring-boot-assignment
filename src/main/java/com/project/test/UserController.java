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
    final ChildService childService;
    public UserController(ParentService parentService, ChildService childService) {
        this.parentService = parentService;
        this.childService = childService;
    }

    @GetMapping("/parent")
    public ResponseEntity<List<Parent>> getAllParent() {
        return new ResponseEntity<>(parentService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/parent/{id}")
    public ResponseEntity<Parent> getParentById(@PathVariable("id") Long id) throws Exception {
        return new ResponseEntity<>(parentService.findById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/parent", consumes = "application/json")
    public ResponseEntity<Parent> createParent(@RequestBody Parent parent) {
        return new ResponseEntity<>(parentService.save(parent),HttpStatus.CREATED);
    }

    @PutMapping("/parent/{id}")
    public ResponseEntity<Parent> updateParent(@PathVariable long id, @RequestBody Parent parent) {
        return new ResponseEntity<>(parentService.update(id,parent), HttpStatus.OK);
    }

    @DeleteMapping("/parent/{id}")
    public ResponseEntity<Object> deleteParent(@PathVariable Long id) {
        parentService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/child")
    public ResponseEntity<List<Child>> getAllChild() {
        return new ResponseEntity<>(childService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/child/{id}")
    public ResponseEntity<Object> getChildById(@PathVariable("id") Long id) throws Exception {
        return new ResponseEntity<>(childService.getById(id),HttpStatus.OK);
    }

    @PostMapping("/child")
    public ResponseEntity<Object> createChild(@RequestBody Child child) {
        if (childService.isParentIdExist(child.getParentId(),parentService))
            return new ResponseEntity<>(childService.save(child), HttpStatus.CREATED);
        else
            return new ResponseEntity<>(
                    String.format(UserConstants.PARENT_NOT_FOUND,child.getParentId()),
                    HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/child/{id}")
    public ResponseEntity<Object> updateChild(@PathVariable("id") Long id, @RequestBody Child child) {
        if (childService.isParentIdExist(child.getParentId(), parentService))
            return new ResponseEntity<>(childService.update(id,child), HttpStatus.OK);
        else
            return new ResponseEntity<>(
                    String.format(UserConstants.PARENT_NOT_FOUND,child.getParentId()),
                    HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/child/{id}")
    public ResponseEntity<Object> deleteChildById(@PathVariable("id") Long id) {
        childService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
