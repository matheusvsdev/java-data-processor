package com.matheusvsdev.dataprocessor.mapper;

import com.matheusvsdev.dataprocessor.dto.UserDTO;
import com.matheusvsdev.dataprocessor.entities.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserEntity toEntity(UserDTO dto) {
        UserEntity entity = new UserEntity();
        entity.setId(dto.id());
        entity.setName(dto.name());
        entity.setAge(dto.age());
        entity.setScore(dto.score());
        entity.setActive(dto.active());
        entity.setCountry(dto.country());
        return entity;
    }
}
