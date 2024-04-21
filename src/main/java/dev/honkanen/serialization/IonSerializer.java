package dev.honkanen.serialization;

import com.fasterxml.jackson.dataformat.ion.IonObjectMapper;

/**
 * A serializer that uses the Ion format (Amazon's binary JSON format).
 * <p>
 * TODO: use the official Ion library instead of Jackson.
 * </p>
 */
public class IonSerializer implements SerializationStrategy {

    IonObjectMapper mapper = new IonObjectMapper();

    public IonSerializer() {
        // configure the mapper to use the JsonArrayFormat
        // this reduces the size of the serialized data as it omits field names
        // mapper.setAnnotationIntrospector(new JsonArrayFormat());
    }

    @Override
    public byte[] serialize(Object object) {
        try {
            return mapper.writeValueAsBytes(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) {
        try {
            return mapper.readValue(data, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
