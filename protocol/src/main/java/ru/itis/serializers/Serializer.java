package ru.itis.serializers;

import java.util.List;

public interface Serializer<T> {
    byte[] serialize(T obj);

    byte[] serialize(List<T> obj);

    T deserialize(byte[] obj);

    List<T> deserializeAll(byte[] objs);
}
