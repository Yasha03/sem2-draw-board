package ru.itis.services;

import ru.itis.models.Board;

import java.util.List;
import java.util.UUID;

public interface BoardService {
    public void create(Board board);

    public Board loadIfExist(UUID id);

    public List<Board> loadAll();
}
