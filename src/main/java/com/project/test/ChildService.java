package com.project.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class ChildService {

    private final ChildRepository childRepository;
    public final Logger logger = LoggerFactory.getLogger(ChildService.class);

    public ChildService(ChildRepository childRepository) {
        this.childRepository = childRepository;
    }

    public List<Child> findAll() {
        return childRepository.findAll();
    }

    public Child getById(Long id) throws Exception {
        return childRepository.findById(id)
                .orElseThrow(() -> new Exception("Child with id "+ id + " not found"));
    }

    public Child save(Child child) {
        return childRepository.save(child);
    }

    public Child update(Long id, Child updatedChild) {
        Optional<Child> childWithId = childRepository.findById(id);
        if (childWithId.isPresent()) {
            Child child = childWithId.get();
            child.setParentId(updatedChild.getParentId());
            child.setFirstName(updatedChild.getFirstName());
            child.setLastName(updatedChild.getLastName());
            return childRepository.save(child);
        } else {
            return childRepository.save(updatedChild);
        }
    }

    public void delete(Long id) {
        childRepository.deleteById(id);
    }

    public boolean isParentIdExist(Long parentId, ParentService parentService) {
        boolean isParentExist = false;
        try {
            Parent parent = parentService.findById(parentId);

            isParentExist = true;
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        return isParentExist;
    }
}



