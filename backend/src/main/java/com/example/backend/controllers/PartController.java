package com.example.backend.controllers;

import com.example.backend.models.Part;
import com.example.backend.services.PartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/parts")
@CrossOrigin(origins = "http://localhost:3000") 
public class PartController {

    @Autowired
    private PartService partService;


    @GetMapping
    public ResponseEntity<List<Part>> getAllParts() {
        return ResponseEntity.ok(partService.getAllParts());
    }


    @GetMapping("/{id}")
    public ResponseEntity<Part> getPartById(@PathVariable Long id) {
        Optional<Part> part = partService.getPartById(id);
        return part.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PostMapping
    public ResponseEntity<Part> addPart(@RequestBody Part part) {
        return ResponseEntity.ok(partService.addPart(part));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Part> updatePart(@PathVariable Long id, @RequestBody Part partDetails) {
        Part updatedPart = partService.updatePart(id, partDetails);
        if (updatedPart != null) {
            return ResponseEntity.ok(updatedPart);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePart(@PathVariable Long id) {
        if (partService.deletePart(id)) {
            return ResponseEntity.ok("Part deleted successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}