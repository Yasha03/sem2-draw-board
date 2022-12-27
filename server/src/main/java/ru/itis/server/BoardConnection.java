package ru.itis.server;

import ru.itis.exceptions.BoardServerException;
import ru.itis.exceptions.MessageTypeException;
import ru.itis.exceptions.ProtocolHeaderException;
import ru.itis.listener.EventListenerRegister;
import ru.itis.listener.ServerEventListener;
import ru.itis.message.Message;
import ru.itis.message.MessageInputStream;
import ru.itis.message.MessageOutputStream;

import java.io.IOException;
import java.net.Socket;
import java.rmi.server.ServerCloneException;

public class BoardConnection implements Runnable{

    private BoardServer server;

    private Socket socket;

    private MessageInputStream inputStream;

    private MessageOutputStream outputStream;

    public BoardConnection(BoardServer server, Socket socket) throws BoardServerException {
        this.server = server;
        this.socket = socket;
        try {
            this.inputStream = new MessageInputStream(socket.getInputStream());
            this.outputStream = new MessageOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new BoardServerException("Can't get IO streams");
        }
    }

    @Override
    public void run() {
        Message message;
        try {
            while ((message = inputStream.getMessage()) != null) {
                ServerEventListener listener = EventListenerRegister.getListener(message.getType());
                listener.init(server);
                listener.handle(this, message);
            }
        } catch (MessageTypeException e) {
            throw new IllegalArgumentException("Invalid type message");
        } catch (IOException e) {
            throw new BoardServerException("IO server exception. " + e);
        } catch (ProtocolHeaderException e) {
            throw new IllegalArgumentException("Invalid protocol header");
        }
    }

    public void sendMessage(Message message) throws BoardServerException {
        try {
            outputStream.writeMessage(message);
        } catch (IOException e) {
//            throw new BoardServerException("Failed to send message");
        }
    }
}
