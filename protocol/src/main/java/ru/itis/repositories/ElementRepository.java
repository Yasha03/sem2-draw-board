package ru.itis.repositories;

import ru.itis.models.Board;
import ru.itis.models.Element;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ElementRepository {
    public void save(Element element);

    public Optional<Element> findById(UUID id);

    List<Element> loadAllByBoardId(UUID id);


}
