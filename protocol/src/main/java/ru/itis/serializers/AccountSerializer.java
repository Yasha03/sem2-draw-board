package ru.itis.serializers;

import com.google.gson.Gson;
import ru.itis.models.Account;
import ru.itis.models.RegisterForm;

import java.util.List;

public class AccountSerializer implements Serializer<Account> {

    private Gson gson;

    public AccountSerializer() {
        this.gson = new Gson();
    }

    @Override
    public byte[] serialize(Account obj) {
        return MessageSerializer.serialize(gson.toJson(obj));
    }

    @Override
    public byte[] serialize(List<Account> obj) {
        return new byte[0];
    }

    @Override
    public Account deserialize(byte[] obj) {
        return gson.fromJson(MessageSerializer.deserialize(obj), Account.class);
    }

    @Override
    public List<Account> deserializeAll(byte[] objs) {
        return null;
    }
}
