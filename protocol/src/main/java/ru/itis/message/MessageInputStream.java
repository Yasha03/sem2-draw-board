package ru.itis.message;

import ru.itis.exceptions.MessageTypeException;
import ru.itis.exceptions.ProtocolHeaderException;

import java.io.IOException;
import java.io.InputStream;

public class MessageInputStream extends InputStream {

    private InputStream inputStream;

    private final byte FIRST_BYTE;

    private final byte SECOND_BYTE;

    public MessageInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
        this.FIRST_BYTE = 65;
        this.SECOND_BYTE = 66;
    }

    public Message getMessage() throws IOException, ProtocolHeaderException, MessageTypeException {
        byte firstByte = (byte) inputStream.read();
        byte secondByte = (byte) inputStream.read();

        if (firstByte != FIRST_BYTE || secondByte != SECOND_BYTE) {
            throw new ProtocolHeaderException("Invalid protocol header");
        }

        byte type = (byte) inputStream.read();

        if (!MessageTypes.typeIsExist(type)) {
            throw new MessageTypeException("Unknown message type");
        }
        byte[] messageData = new byte[0];
        int length = inputStream.read() << 8 | inputStream.read();

        messageData = new byte[length];

        for (int i = 0; i < length; i++) {
            messageData[i] = (byte) inputStream.read();
        }

        return Message.builder()
                .type(type)
                .data(messageData)
                .build();
    }

    @Override
    public int read(byte[] b) throws IOException {
        return inputStream.read(b);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        return inputStream.read(b, off, len);
    }

    @Override
    public long skip(long n) throws IOException {
        return inputStream.skip(n);
    }

    @Override
    public int available() throws IOException {
        return inputStream.available();
    }

    @Override
    public void close() throws IOException {
        inputStream.close();
    }

    @Override
    public synchronized void mark(int readlimit) {
        inputStream.mark(readlimit);
    }

    @Override
    public synchronized void reset() throws IOException {
        inputStream.reset();
    }

    @Override
    public boolean markSupported() {
        return inputStream.markSupported();
    }

    @Override
    public int read() throws IOException {
        return inputStream.read();
    }
}