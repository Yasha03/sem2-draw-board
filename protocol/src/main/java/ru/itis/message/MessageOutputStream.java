package ru.itis.message;

import java.io.IOException;
import java.io.OutputStream;

public class MessageOutputStream extends OutputStream {

    private final OutputStream outputStream;

    private final byte FIRST_BYTE;

    private final byte SECOND_BYTE;

    public MessageOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
        this.FIRST_BYTE = 65;
        this.SECOND_BYTE = 66;
    }

    public void writeMessage(Message message) throws IOException {
        outputStream.write(FIRST_BYTE);
        outputStream.write(SECOND_BYTE);

        outputStream.write(message.getType());

        byte[] data = message.getData();
        if(data == null){
            data = new byte[0];
        }
        outputStream.write((byte) (data.length >> 8));
        outputStream.write((byte) (data.length));

        for (int i = 0; i < data.length; i++) {
            outputStream.write(data[i]);
        }

        outputStream.flush();
    }

    @Override
    public void write(byte[] b) throws IOException {
        outputStream.write(b);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        outputStream.write(b, off, len);
    }

    @Override
    public void flush() throws IOException {
        outputStream.flush();
    }

    @Override
    public void close() throws IOException {
        outputStream.close();
    }

    @Override
    public void write(int b) throws IOException {
        outputStream.write(b);
    }
}