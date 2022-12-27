package ru.itis.clientfx.connection;

import ru.itis.exceptions.LostConnectionException;
import ru.itis.message.Message;
import ru.itis.message.MessageInputStream;
import ru.itis.message.MessageOutputStream;
import ru.itis.models.Account;
import ru.itis.models.Board;
import ru.itis.models.Element;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SocketConnection {
    private Socket socket;

    private MessageInputStream inputStream;

    private MessageOutputStream outputStream;

    private Account user;

    private List<Board> boards;

    private Board currentBoard;

    private List<Element> elements;

    public SocketConnection(InetAddress inetAddress, int port) throws LostConnectionException {
        try {
            this.socket = new Socket(inetAddress, port);
            this.inputStream = new MessageInputStream(socket.getInputStream());
            this.outputStream = new MessageOutputStream(socket.getOutputStream());
            this.boards = new ArrayList<>();
            this.elements = new ArrayList<>();
        } catch (IOException e) {
            throw new LostConnectionException("The connection to the server was broken");
        }
    }

    public void closeConnection(){
        try {
            socket.close();
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to close connections");
        }
    }

    public void sendMessage(Message message) throws IOException {
        outputStream.writeMessage(message);
    }

    public MessageInputStream getInputStream() {
        return this.inputStream;
    }

    public Account getUser() {
        return user;
    }

    public void setUser(Account user) {
        this.user = user;
    }

    public List<Board> getBoards() {
        return boards;
    }

    public void setBoards(List<Board> boards) {
        this.boards = boards;
    }

    public Board getCurrentBoard() {
        return currentBoard;
    }

    public void setCurrentBoard(Board currentBoard) {
        this.currentBoard = currentBoard;
    }

    public List<Element> getElements() {
        return elements;
    }

    public void setElements(List<Element> elements) {
        this.elements = elements;
    }
}
