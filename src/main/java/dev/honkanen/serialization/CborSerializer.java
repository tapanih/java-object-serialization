package dev.honkanen.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.cbor.databind.CBORMapper;

/**
 * A serializer that uses the CBOR format.
 * <p>
 * CBOR is a binary format that is more compact than JSON
 * and is designed to be more efficient to parse.
 * </p>
 */
public class CborSerializer implements SerializationStrategy {
    ObjectMapper mapper = new CBORMapper();

    public CborSerializer() {
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
