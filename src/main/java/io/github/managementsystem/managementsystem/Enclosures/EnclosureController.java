package io.github.managementsystem.managementsystem.Enclosures;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("/api/v1/enclosures")
public class EnclosureController {

    @Autowired
    private EnclosureService enclosureService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT')")
    public ResponseEntity<?> fetchAllEnclosures() {
        List<Enclosure> enclosures = enclosureService.fetchAllEnclosures();
        return new ResponseEntity<>(enclosures, HttpStatus.OK);
    }

    @GetMapping("/{type}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT')")
    public ResponseEntity<?> fetchEnclosuresByType(@PathVariable("type") String type) {
        List<Enclosure> enclosures = enclosureService.fetchEnclosuresByType(type);
        return new ResponseEntity<>(enclosures, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createNewEnclosure(@RequestBody EnclosureRequest enclosureRequest) {
        Enclosure enclosure = enclosureService.createNewEnclosure(enclosureRequest);
        return new ResponseEntity<>(enclosure, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateEnclosure(
            @PathVariable("id") BigInteger enclosureId,
            @RequestBody EnclosureRequest enclosureRequest) {
        Enclosure enclosure = enclosureService.updateEnclosure(enclosureId, enclosureRequest);
        return new ResponseEntity<>(enclosure, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteEnclosure(
            @PathVariable("id") BigInteger enclosureId) {
        Enclosure enclosure = enclosureService.deleteEnclosure(enclosureId);
        return new ResponseEntity<>(enclosure, HttpStatus.OK);
    }
}
