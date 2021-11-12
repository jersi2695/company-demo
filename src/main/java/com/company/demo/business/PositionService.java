package com.company.demo.business;

import com.company.demo.model.Position;
import com.company.demo.repository.PositionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PositionService {

    private final PositionRepository positionRepository;

    public Position findOrCreatePosition(String name){
        return positionRepository.findFirstByName(name).orElseGet(() ->
                positionRepository.save(new Position(name)));
    }

    public List<Position> listPositions(){
        return positionRepository.findAll();
    }

}
