package com.example.backend.services;

import com.example.backend.models.Part;
import com.example.backend.repositories.PartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PartService {

    @Autowired
    private PartRepository partRepository;

    public List<Part> getAllParts() {
        return partRepository.findAll();
    }

    public Optional<Part> getPartById(Long id) {
        return partRepository.findById(id);
    }

    public Part addPart(Part part) {
        return partRepository.save(part);
    }

    public Part updatePart(Long id, Part partDetails) {
        return partRepository.findById(id).map(part -> {
            part.setName(partDetails.getName());
            part.setDescription(partDetails.getDescription());
            part.setPrice(partDetails.getPrice());
            part.setStock(partDetails.getStock());
            return partRepository.save(part);
        }).orElse(null);
    }

    public boolean deletePart(Long id) {
        if (partRepository.existsById(id)) {
            partRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
