package ru.itis.listener;

import ru.itis.message.Message;
import ru.itis.server.BoardConnection;
import ru.itis.server.BoardServer;
import ru.itis.server.ServerContext;

public abstract class AbstractEventListener implements ServerEventListener {

    protected BoardServer server;

    protected BoardConnection connection;

    protected Message message;

    protected byte type;

    protected ServerContext context;

    public AbstractEventListener(byte type) {
        this.type = type;
    }

    @Override
    public void init(BoardServer server) {
        this.server = server;
        this.context = server.getContext();
    }

    @Override
    public byte getType() {
        return type;
    }
}
