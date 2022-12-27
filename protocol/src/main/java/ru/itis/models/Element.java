package ru.itis.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
public class Element {
    private UUID id;

    private UUID creatorId;

    private UUID boardId;

    private Type type;

    private Double size;

    private String value;

    private String color;

    public enum Type {
        BRUSH,
        ERASER,
        IMAGE,
        LINE,
        SQUARE
    }
}
