package ru.itis.clientfx.connection;

import javafx.application.Platform;
import ru.itis.clientfx.App;
import ru.itis.clientfx.gui.GuiManager;
import ru.itis.exceptions.MessageTypeException;
import ru.itis.exceptions.ProtocolHeaderException;
import ru.itis.message.Message;
import ru.itis.message.MessageInputStream;
import ru.itis.message.MessageTypes;
import ru.itis.models.Element;
import ru.itis.serializers.AccountSerializer;
import ru.itis.serializers.BoardSerializer;
import ru.itis.serializers.ElementSerializer;
import ru.itis.serializers.MessageSerializer;

import java.io.IOException;
import java.util.List;

public class ConnectionListener implements Runnable{

    private SocketConnection connection;

    private GuiManager guiManager;

    private MessageInputStream inputStream;

    private AccountSerializer accountSerializer;

    private BoardSerializer boardSerializer;

    private ElementSerializer elementSerializer;

    public ConnectionListener(SocketConnection connection, GuiManager guiManager) {
        this.connection = connection;
        this.guiManager = guiManager;
        this.inputStream = connection.getInputStream();
        this.accountSerializer = new AccountSerializer();
        this.boardSerializer = new BoardSerializer();
        this.elementSerializer = new ElementSerializer();
    }

    @Override
    public void run() {
        Message message;
        try {
            while ((message = inputStream.getMessage()) != null) {
                switch (message.getType()) {
                    case MessageTypes.REGISTER_SUCCESS, MessageTypes.LOGIN_SUCCESS -> {
                        connection.setUser(accountSerializer.deserialize(message.getData()));
                        guiManager.showMainPage();
                    }
                    case MessageTypes.REGISTER_ERROR -> guiManager.showErrorRegister(MessageSerializer.deserialize(message.getData()));
                    case MessageTypes.LOGIN_ERROR -> guiManager.showErrorLogin(MessageSerializer.deserialize(message.getData()));
                    case MessageTypes.GET_ALL_BOARD -> {
                        connection.setBoards(boardSerializer.deserializeAll(message.getData()));
                        GuiManager.setUpdateBoards(true);
                    }
                    case MessageTypes.CREATE_BOARD_SUCCESS -> {
                        Message messageSend1 = Message.builder()
                                .type(MessageTypes.GET_ALL_BOARD)
                                .build();
                        connection.sendMessage(messageSend1);
                    }
                    case MessageTypes.SEND_ELEMENT_TO_CLIENT -> {
                        boolean elementIsExist = false;
                        Element element = elementSerializer.deserialize(message.getData());
                        for(Element elementExist : connection.getElements()){
                            if(element.getId().equals(elementExist.getId())){
                               elementIsExist = true;
                            }
                        }
                        if(!elementIsExist){
                            connection.getElements().add(element);
                            GuiManager.setUpdateElements(true);
                        }
                    }
                    case MessageTypes.GET_ALL_ELEMENT_BY_BOARD -> {
                        List<Element> elements = elementSerializer.deserializeAll(message.getData());
                        for(Element element : elements){
                            boolean elementIsExist = false;
                            for(Element elementExist : connection.getElements()){
                                if(element.getId().equals(elementExist.getId())){
                                    elementIsExist = true;
                                }
                            }
                            if(!elementIsExist){
                                connection.getElements().add(element);
                            }
                        }
                        GuiManager.setUpdateElements(true);
                    }

                }
            }
        } catch (MessageTypeException | IOException | ProtocolHeaderException e) {
            e.printStackTrace(); // TODO
        }
    }
}
