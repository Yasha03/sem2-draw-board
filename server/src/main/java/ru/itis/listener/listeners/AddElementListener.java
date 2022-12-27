package ru.itis.listener.listeners;

import ru.itis.exceptions.BoardServerException;
import ru.itis.listener.AbstractEventListener;
import ru.itis.message.Message;
import ru.itis.message.MessageTypes;
import ru.itis.models.Element;
import ru.itis.serializers.ElementSerializer;
import ru.itis.server.BoardConnection;
import ru.itis.services.ElementService;

public class AddElementListener extends AbstractEventListener {

    private ElementSerializer elementSerializer;

    private ElementService elementService;

    public AddElementListener() {
        super(MessageTypes.ADD_ELEMENT);
        this.elementSerializer = new ElementSerializer();
    }

    @Override
    public void handle(BoardConnection connection, Message message) throws BoardServerException {
        this.elementService = context.getElementService();

        Element element = elementSerializer.deserialize(message.getData());
        elementService.save(element);

        for(BoardConnection boardConnection : server.getConnections()){
            Message messageElement = Message.builder()
                    .type(MessageTypes.SEND_ELEMENT_TO_CLIENT)
                    .data(elementSerializer.serialize(element))
                    .build();
            boardConnection.sendMessage(messageElement);
        }
    }
}
