package com.matheusvsdev.dataprocessor.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

    /*
     * O ObjectMapper é uma classe da biblioteca Jackson, usada para converter objetos Java
     * em JSON e vice-versa. Ele é tipo o “tradutor oficial” entre o mundo Java e o mundo JSON.
     * Serialização: transforma objetos Java em JSON
     * Desserialização: transforma JSON em objetos Java
    */

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();

        // Adiciona suporte para as classes de data e hora do Java 8+,
        // como LocalDate, LocalDateTime, etc.
        mapper.registerModule(new JavaTimeModule());

        // Por padrão, o Jackson serializa datas como timestamps numéricos (ex: 1692451200000)
        // Com essa configuração, ele passa a serializar como strings legíveis, tipo "2025-08-19T15:42:00"
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }
}
