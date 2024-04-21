package dev.honkanen.serialization;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import dev.honkanen.input.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;


/**
 * A serializer that uses the Kryo serialization library.
 * <p>
 * Kryo is a fast and efficient serialization library that is optimized
 * for speed and low size. The format is not guaranteed to be stable
 * across versions of the library or JVM, so it is recommended to use it
 * only for short-term storage or communication.
 * </p>
 */
public class KryoSerializer implements SerializationStrategy {

    private final Kryo kryo;

    public KryoSerializer() {
        kryo = new Kryo();
        kryo.register(GeoJson.class);
        kryo.register(Geometry.class);
        kryo.register(double[].class);
        kryo.register(double[][].class);
        kryo.register(double[][][].class);
        kryo.register(Feature[].class);
        kryo.register(Feature.class);
        kryo.register(LineString.class);
        kryo.register(MultiLineString.class);
    }

    @Override
    public byte[] serialize(Object object) {
        var out = new ByteArrayOutputStream();
        var output = new Output(out);
        kryo.writeObject(output, object);
        output.close();
        return out.toByteArray();
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) {
        var in = new ByteArrayInputStream(data);
        var input = new Input(in);
        var obj = kryo.readObject(input, clazz);
        input.close();
        return obj;
    }
}
