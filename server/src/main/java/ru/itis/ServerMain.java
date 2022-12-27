package ru.itis;

import ru.itis.exceptions.BoardServerException;
import ru.itis.server.BoardServer;

import java.io.IOException;

public class ServerMain {
    public static void main(String[] args) {
        BoardServer server = new BoardServer(2031);
        try {
            server.start();
        }catch (BoardServerException | IOException e){
            throw new IllegalStateException(e);
        }
    }
}
