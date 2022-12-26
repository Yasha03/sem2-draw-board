package ru.itis;

import ru.itis.server.BoardServer;

public class ServerMain {
    public static void main(String[] args) {
        BoardServer server = new BoardServer(2031);
        server.start();
    }
}
