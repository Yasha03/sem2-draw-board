package ru.itis.repositories;

import ru.itis.models.Board;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BoardRepository {
    public void save(Board board);

    public Optional<Board> findById(UUID id);

    List<Board> loadAll();
}
