package com.company.demo.controller;

import com.company.demo.business.PositionService;
import com.company.demo.dto.ResponsePositionDTO;
import com.company.demo.mapper.UtilMapper;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/positions")
@AllArgsConstructor
public class PositionController {

    private final PositionService positionService;
    private final UtilMapper utilMapper;


    @GetMapping
    public List<ResponsePositionDTO> listPositions(){
        var positions = positionService.listPositions();
        return positions.stream().map(utilMapper::map).collect(Collectors.toList());
    }

}
