package ru.itis.serializers;

import java.nio.charset.StandardCharsets;

public class MessageSerializer {

    public static byte[] serialize(String text){
        return text.getBytes(StandardCharsets.UTF_8);
    }

    public static String deserialize(byte[] message){
        return new String(message, StandardCharsets.UTF_8);
    }
}
