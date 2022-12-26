package ru.itis.services;

import ru.itis.models.Account;
import ru.itis.models.Board;
import ru.itis.repositories.BoardRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class BoardServiceImpl implements BoardService{

    private BoardRepository boardRepository;

    public BoardServiceImpl(BoardRepository boardRepository){
        this.boardRepository = boardRepository;
    }


    @Override
    public void create(Board board) {
        boardRepository.save(board);
    }

    @Override
    public Board loadIfExist(UUID id) {
        Optional<Board> board = boardRepository.findById(id);
        return board.orElse(null);
    }

    @Override
    public List<Board> loadAll(){
        return boardRepository.loadAll();
    }
}
