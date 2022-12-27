package ru.itis.listener;

import ru.itis.exceptions.BoardServerException;
import ru.itis.message.Message;
import ru.itis.server.BoardConnection;
import ru.itis.server.BoardServer;

public interface ServerEventListener {
    public void init(BoardServer server);

    public void handle(BoardConnection connection, Message message) throws BoardServerException;

    public byte getType();
}
