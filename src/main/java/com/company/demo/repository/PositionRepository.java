package com.company.demo.repository;

import com.company.demo.model.Position;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface PositionRepository extends PagingAndSortingRepository<Position, Long>{

    Optional<Position> findFirstByName(String name);

    List<Position> findAll();

}
