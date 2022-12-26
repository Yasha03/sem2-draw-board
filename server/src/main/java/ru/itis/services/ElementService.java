package ru.itis.services;

import ru.itis.models.Element;

import java.util.List;
import java.util.UUID;

public interface ElementService {
    public void save(Element element);

    public Element findById(UUID id);

    public List<Element> loadAllByBoardId(UUID id);
}
