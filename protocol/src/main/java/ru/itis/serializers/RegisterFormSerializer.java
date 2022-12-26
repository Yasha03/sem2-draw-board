package ru.itis.serializers;

import com.google.gson.Gson;
import ru.itis.models.RegisterForm;

import java.util.List;

public class RegisterFormSerializer implements Serializer<RegisterForm>{

    private Gson gson;

    public RegisterFormSerializer(){
        this.gson = new Gson();
    }

    @Override
    public byte[] serialize(RegisterForm obj) {
        return MessageSerializer.serialize(gson.toJson(obj));
    }

    @Override
    public byte[] serialize(List<RegisterForm> obj) {
        return new byte[0];
    }

    @Override
    public RegisterForm deserialize(byte[] obj) {
        return gson.fromJson(MessageSerializer.deserialize(obj), RegisterForm.class);
    }

    @Override
    public List<RegisterForm> deserializeAll(byte[] objs) {
        return null;
    }
}
