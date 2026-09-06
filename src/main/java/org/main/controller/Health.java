package org.main.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Health {

    @GetMapping("/health")
    public ResponseEntity<Void> get()
    {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<String> get1()
    {
        var user = "good user";
        return ResponseEntity.ok(user);
    }
}
