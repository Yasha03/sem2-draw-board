package ru.itis.listener.listeners;

import ru.itis.exceptions.BoardServerException;
import ru.itis.listener.AbstractEventListener;
import ru.itis.message.Message;
import ru.itis.message.MessageTypes;
import ru.itis.models.Board;
import ru.itis.serializers.BoardSerializer;
import ru.itis.server.BoardConnection;
import ru.itis.services.BoardService;

import java.util.List;

public class GetAllBoardsListener extends AbstractEventListener {

    private BoardSerializer boardSerializer;

    private BoardService boardService;

    public GetAllBoardsListener() {
        super(MessageTypes.GET_ALL_BOARD);
        this.boardSerializer = new BoardSerializer();
    }

    @Override
    public void handle(BoardConnection connection, Message message) throws BoardServerException {
        this.boardService = context.getBoardService();
        List<Board> boardList = boardService.loadAll();
        Message messageSend = Message.builder()
                .type(MessageTypes.GET_ALL_BOARD)
                .data(boardSerializer.serialize(boardList))
                .build();
        connection.sendMessage(messageSend);
    }
}
