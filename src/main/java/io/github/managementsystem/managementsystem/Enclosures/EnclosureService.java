package io.github.managementsystem.managementsystem.Enclosures;

import org.springframework.http.ResponseEntity;

import java.math.BigInteger;
import java.util.List;

public interface EnclosureService {
    List<Enclosure> fetchAllEnclosures();

    Enclosure createNewEnclosure(EnclosureRequest enclosureRequest);

    Enclosure updateEnclosure(BigInteger enclosureId, EnclosureRequest enclosureRequest);

    Enclosure fetchEnclosureById(BigInteger enclosureId);

    Enclosure deleteEnclosure(BigInteger enclosureId);

    List<Enclosure> fetchEnclosuresByType(String type);
}
