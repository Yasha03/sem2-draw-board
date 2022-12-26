package ru.itis.serializers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import ru.itis.models.Account;
import ru.itis.models.Board;

import java.util.ArrayList;
import java.util.List;

public class BoardSerializer implements Serializer<Board>{

    private Gson gson;

    public BoardSerializer() {
        this.gson = new Gson();
    }

    @Override
    public byte[] serialize(Board obj) {
        return MessageSerializer.serialize(gson.toJson(obj));
    }

    @Override
    public byte[] serialize(List<Board> obj) {
        return MessageSerializer.serialize(gson.toJson(obj));
    }

    @Override
    public Board deserialize(byte[] obj) {
        return gson.fromJson(MessageSerializer.deserialize(obj), Board.class);
    }

    @Override
    public List<Board> deserializeAll(byte[] objs) {
        List<Board> resultList = new ArrayList<>();
        JsonArray array = gson.fromJson(MessageSerializer.deserialize(objs), JsonArray.class);
        for (JsonElement element : array){
            Board board = gson.fromJson(element, Board.class);
            resultList.add(board);
        }
        return resultList;
    }
}
