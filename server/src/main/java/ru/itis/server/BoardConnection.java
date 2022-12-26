package ru.itis.server;

import ru.itis.exceptions.MessageTypeException;
import ru.itis.exceptions.ProtocolHeaderException;
import ru.itis.listener.EventListenerRegister;
import ru.itis.listener.ServerEventListener;
import ru.itis.message.Message;
import ru.itis.message.MessageInputStream;
import ru.itis.message.MessageOutputStream;

import java.io.IOException;
import java.net.Socket;

public class BoardConnection implements Runnable{

    private BoardServer server;

    private Socket socket;

    private MessageInputStream inputStream;

    private MessageOutputStream outputStream;

    public BoardConnection(BoardServer server, Socket socket) {
        this.server = server;
        this.socket = socket;
        try {
            this.inputStream = new MessageInputStream(socket.getInputStream());
            this.outputStream = new MessageOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace(); // TODO
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
            e.printStackTrace(); // TODO
        } catch (IOException e) {
            e.printStackTrace(); // TODO
        } catch (ProtocolHeaderException e) {
            e.printStackTrace(); // TODO
        }
    }

    public void sendMessage(Message message){
        try {
            outputStream.writeMessage(message);
        } catch (IOException e) {
            e.printStackTrace(); // TODO: close connection
        }
    }
}
