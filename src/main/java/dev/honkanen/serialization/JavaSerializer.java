package dev.honkanen.serialization;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Native Java serialization strategy.
 * <p>
 * This strategy uses the built-in Java serialization mechanism to
 * serialize and deserialize objects. It is simple to use and requires
 * no additional dependencies.
 * </p>
 */
public class JavaSerializer implements SerializationStrategy {

    public JavaSerializer() {
    }

    public byte[] serialize(Object object) {
        try {
            var output = new ByteArrayOutputStream();
            var objectOutputStream = new ObjectOutputStream(output);
            objectOutputStream.writeObject(object);
            var dst = output.toByteArray();
            objectOutputStream.close();
            output.close();
            return dst;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T deserialize(byte[] data, Class<T> clazz) {
        try {
            var input = new ByteArrayInputStream(data);
            var objectInputStream = new ObjectInputStream(input);
            var dst = clazz.cast(objectInputStream.readObject());
            objectInputStream.close();
            input.close();
            return dst;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
