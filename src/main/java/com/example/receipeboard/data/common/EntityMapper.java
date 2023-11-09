package com.example.receipeboard.data.common;

public interface EntityMapper <DTO, Entity>{
    Entity toEntity(DTO dto);
}
