package ku.cs.task_management.controllers;

import ku.cs.task_management.responses.HomeResponse;
import ku.cs.task_management.services.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class HomeController {

    @Autowired
    private HomeService homeService;

    @GetMapping("/")
    public ResponseEntity<HomeResponse> getHomePage(@RequestParam UUID m) {
        return ResponseEntity.ok(homeService.getHomePage(m));
    }

}
