package com.matheusvsdev.dataprocessor.dto;

import java.util.List;

public record UserDTO(
        String id,
        String name,
        Integer age,
        Integer score,
        Boolean active,
        String country,
        TeamDTO team,
        List<LogDTO> logs
) {
}
