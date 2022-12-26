package ru.itis.server;

import ru.itis.jdbc.HikariSimpleDataSource;
import ru.itis.repositories.*;
import ru.itis.services.*;
import ru.itis.validators.SignUpValidatorRegex;

public class ServerContext {
    private AccountRepository accountRepository;

    private BoardRepository boardRepository;

    private AccountService accountService;

    private BoardService boardService;

    private ElementService elementService;

    public ServerContext() {
        this.accountRepository = new AccountRepositoryImpl(HikariSimpleDataSource.getDataSource());
        this.accountService = new AccountServiceImpl(new SignUpValidatorRegex(), accountRepository);
        this.boardRepository = new BoardRepositoryImpl(HikariSimpleDataSource.getDataSource());
        this.boardService = new BoardServiceImpl(boardRepository);
        this.elementService = new ElementServiceImpl(new ElementRepositoryImpl(HikariSimpleDataSource.getDataSource()));
    }

    public AccountService getAccountService() {
        return accountService;
    }

    public BoardService getBoardService() {
        return boardService;
    }

    public ElementService getElementService() {
        return elementService;
    }
}
