package com.project.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ParentService {

    private final ParentRepository parentRepository;

    public ParentService(ParentRepository parentRepository) {
        this.parentRepository = parentRepository;
    }

    public List<Parent> findAll() {
        return parentRepository.findAll();
    }

    public Parent findById(Long id) throws Exception {
//        Optional<Parent> parent = parentRepository.findById(id);
//        return parent.get();
        return parentRepository.findById(id).orElseThrow(() -> new Exception("Parent with id "+id+" doesnt exist"));
    }

    public Parent save(Parent parent) {
        return parentRepository.save(parent);
    }

    public Parent update(Long id,Parent parent) {
        Optional<Parent> parentWithId = parentRepository.findById(id);
        if(parentWithId.isPresent()) {
            Parent parentFromId = parentWithId.get();
            parentFromId.setFirstName(parent.getFirstName());
            parentFromId.setLastName(parent.getLastName());
            parentFromId.setAddress(parent.getAddress());
            return parentRepository.save(parentFromId);
        } else {
            return parentRepository.save(parent);
        }
    }

    public void delete(Long id) {
        parentRepository.deleteById(id);
    }
}
