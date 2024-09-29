package ku.cs.task_management.controllers;

import ku.cs.task_management.entities.Project;
import ku.cs.task_management.repositories.TestP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class Test {
    @Autowired private TestP repository;

    @PutMapping("/")
    public ResponseEntity<?> insertData() {
        Map<String, Project> object = new HashMap<>();
        Project p = new Project();

        object.put("object", p);

        Project saved = repository.save(p);

        return ResponseEntity.ok(saved);
    }
}
