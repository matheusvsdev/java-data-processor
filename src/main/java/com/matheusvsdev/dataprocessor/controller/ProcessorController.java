package com.matheusvsdev.dataprocessor.controller;

import com.matheusvsdev.dataprocessor.dto.FileDTO;
import com.matheusvsdev.dataprocessor.service.ProcessorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/processor")
public class ProcessorController {

    private final ProcessorService service;

    public ProcessorController(ProcessorService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<String> loadData(@RequestBody FileDTO filePath) {
        return ResponseEntity.ok(service.process(filePath));
    }
}
