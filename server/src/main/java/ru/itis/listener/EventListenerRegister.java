package ru.itis.listener;

import ru.itis.listener.listeners.*;
import ru.itis.message.MessageTypes;

public class EventListenerRegister {

    public static ServerEventListener getListener(byte type){
        return switch (type) {
            case MessageTypes.REGISTER_FORM -> new RegisterFormListener();
            case MessageTypes.LOGIN_FORM -> new LoginFormListener();
            case MessageTypes.CREATE_BOARD -> new CreateBoardListener();
            case MessageTypes.GET_ALL_BOARD -> new GetAllBoardsListener();
            case MessageTypes.ADD_ELEMENT -> new AddElementListener();
            case MessageTypes.GET_ALL_ELEMENT_BY_BOARD -> new GetAllElementsByBoardIdListener();
            default -> throw new IllegalArgumentException("Not found listener");
        };
    }
}
