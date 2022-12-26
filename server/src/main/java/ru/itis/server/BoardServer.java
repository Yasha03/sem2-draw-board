package ru.itis.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class BoardServer {
    private int port;

    private ServerSocket serverSocket;

    private List<BoardConnection> connections;

    private ServerContext context;

    public BoardServer(int port) {
        this.port = port;
        this.connections = new ArrayList<>();
        this.context = new ServerContext();
    }

    public void start() {
        try {
            this.serverSocket = new ServerSocket(port);
            while (true) {
                Socket socket = serverSocket.accept();
                connectionHandler(socket);
            }
        } catch (IOException e) {
            e.printStackTrace(); //TODO
        }
    }

    public void connectionHandler(Socket socket) {
        BoardConnection connection = new BoardConnection(this, socket);
        connections.add(connection);
        new Thread(connection).start();
    }

    public ServerContext getContext() {
        return context;
    }

    public List<BoardConnection> getConnections() {
        return connections;
    }
}
