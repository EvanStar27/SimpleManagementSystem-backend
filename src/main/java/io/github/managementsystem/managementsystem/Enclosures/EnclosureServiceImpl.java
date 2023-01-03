package io.github.managementsystem.managementsystem.Enclosures;

import io.github.managementsystem.managementsystem.Exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class EnclosureServiceImpl implements EnclosureService {

    @Autowired
    private EnclosureRepository enclosureRepository;

    @Override
    public List<Enclosure> fetchAllEnclosures() {
        return enclosureRepository.fetchAll();
    }

    @Override
    public Enclosure fetchEnclosureById(BigInteger enclosureId) {
        return enclosureRepository.findById(enclosureId)
                .orElseThrow(() -> new NotFoundException("Enclosure Not Found"));
    }

    @Override
    public Enclosure createNewEnclosure(EnclosureRequest enclosureRequest) {
        return enclosureRepository.save(enclosureRequest);
    }

    @Override
    public Enclosure updateEnclosure(BigInteger enclosureId, EnclosureRequest enclosureRequest) {
        Enclosure enclosure = fetchEnclosureById(enclosureId);
        return enclosureRepository.update(enclosureId, enclosureRequest);
    }

    @Override
    public Enclosure deleteEnclosure(BigInteger enclosureId) {
        Enclosure enclosure = fetchEnclosureById(enclosureId);
        enclosureRepository.delete(enclosureId);
        return enclosure;
    }

    @Override
    public List<Enclosure> fetchEnclosuresByType(String type) {
        return enclosureRepository.fetchByType(type);
    }
}
