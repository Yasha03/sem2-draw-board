package ru.itis.listener.listeners;

import ru.itis.exceptions.BoardServerException;
import ru.itis.listener.AbstractEventListener;
import ru.itis.message.Message;
import ru.itis.message.MessageTypes;
import ru.itis.models.Board;
import ru.itis.models.Element;
import ru.itis.serializers.BoardSerializer;
import ru.itis.serializers.ElementSerializer;
import ru.itis.server.BoardConnection;
import ru.itis.services.ElementService;

import java.util.List;

public class GetAllElementsByBoardIdListener extends AbstractEventListener {

    private ElementSerializer elementSerializer;

    private ElementService elementService;

    private BoardSerializer boardSerializer;

    public GetAllElementsByBoardIdListener() {
        super(MessageTypes.GET_ALL_ELEMENT_BY_BOARD);
        this.elementSerializer = new ElementSerializer();
        this.boardSerializer = new BoardSerializer();
    }

    @Override
    public void handle(BoardConnection connection, Message message) throws BoardServerException {
        this.elementService = context.getElementService();

        Board board = boardSerializer.deserialize(message.getData());

        List<Element> allElements = elementService.loadAllByBoardId(board.getId());
//        for(Element element : allElements){
            Message messageSend = Message.builder()
                    .type(MessageTypes.GET_ALL_ELEMENT_BY_BOARD)
                    .data(elementSerializer.serialize(allElements))
                    .build();
            connection.sendMessage(messageSend);
//        }
    }
}
