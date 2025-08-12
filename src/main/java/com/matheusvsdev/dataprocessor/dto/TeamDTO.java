package com.matheusvsdev.dataprocessor.dto;

import java.util.List;

public record TeamDTO(
        String name,
        Boolean leader,
        List<ProjectDTO> projects
) {
}
