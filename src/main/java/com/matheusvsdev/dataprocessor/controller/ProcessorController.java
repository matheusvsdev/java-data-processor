package com.matheusvsdev.dataprocessor.controller;

import com.matheusvsdev.dataprocessor.dto.FileDTO;
import com.matheusvsdev.dataprocessor.dto.UserDTO;
import com.matheusvsdev.dataprocessor.service.ProcessorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/processor")
public class ProcessorController {

    private final ProcessorService processorService;

    public ProcessorController(ProcessorService processorService) {
        this.processorService = processorService;
    }

    @PostMapping
    public ResponseEntity<String> loadData(@RequestBody FileDTO filePath) {
        return ResponseEntity.ok(processorService.loadData(filePath));
    }
}
