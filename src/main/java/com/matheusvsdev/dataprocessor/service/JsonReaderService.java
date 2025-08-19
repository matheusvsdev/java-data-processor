package com.matheusvsdev.dataprocessor.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matheusvsdev.dataprocessor.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class JsonReaderService {
    private static final Logger logger = LoggerFactory.getLogger(JsonReaderService.class);
    private static final List<UserDTO> EMPTY_LIST = List.of();

    private final ObjectMapper mapper;

    public JsonReaderService(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public List<UserDTO> parseUsersFromJsonFile(Path path) {
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            return mapper.readValue(reader, mapper.getTypeFactory()
                    .constructCollectionType(List.class, UserDTO.class));
        } catch (IOException e) {
            logger.error("Erro ao ler arquivo JSON: {}", path, e);
            return EMPTY_LIST;
        }
    }
}
