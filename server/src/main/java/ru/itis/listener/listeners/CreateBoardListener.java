package ru.itis.listener.listeners;

import ru.itis.listener.AbstractEventListener;
import ru.itis.message.Message;
import ru.itis.message.MessageTypes;
import ru.itis.models.Board;
import ru.itis.serializers.BoardSerializer;
import ru.itis.server.BoardConnection;
import ru.itis.services.BoardService;
import ru.itis.services.BoardServiceImpl;

public class CreateBoardListener extends AbstractEventListener {

    private BoardSerializer boardSerializer;

    private BoardService boardService;

    public CreateBoardListener() {
        super(MessageTypes.CREATE_BOARD);
        this.boardSerializer = new BoardSerializer();
    }

    @Override
    public void handle(BoardConnection connection, Message message) {
        this.boardService = context.getBoardService();
        Board board = boardSerializer.deserialize(message.getData());
        boardService.create(board);

        for(BoardConnection boardConnection : server.getConnections()) {
            Message messageSend = Message.builder()
                    .type(MessageTypes.CREATE_BOARD_SUCCESS)
                    .build();
            boardConnection.sendMessage(messageSend);
        }
    }
}
