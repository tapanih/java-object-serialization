package dev.honkanen.serialization;

public interface SerializationStrategy {

    byte[] serialize(Object object);

    <T> T deserialize(byte[] data, Class<T> clazz);
}
