package ru.itis.serializers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import ru.itis.models.Board;
import ru.itis.models.Element;

import java.util.ArrayList;
import java.util.List;

public class ElementSerializer implements Serializer<Element>{
    private Gson gson;

    public ElementSerializer() {
        this.gson = new Gson();
    }

    @Override
    public byte[] serialize(Element obj) {
        return MessageSerializer.serialize(gson.toJson(obj));
    }

    @Override
    public byte[] serialize(List<Element> obj) {
        return MessageSerializer.serialize(gson.toJson(obj));
    }

    @Override
    public Element deserialize(byte[] obj) {
        return gson.fromJson(MessageSerializer.deserialize(obj), Element.class);
    }

    @Override
    public List<Element> deserializeAll(byte[] objs) {
        List<Element> resultList = new ArrayList<>();
        JsonArray array = gson.fromJson(MessageSerializer.deserialize(objs), JsonArray.class);
        for (JsonElement element : array){
            Element element1 = gson.fromJson(element, Element.class);
            resultList.add(element1);
        }
        return resultList;
    }
}
