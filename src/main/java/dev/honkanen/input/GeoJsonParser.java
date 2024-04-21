package dev.honkanen.input;

import com.fasterxml.jackson.databind.ObjectMapper;

public class GeoJsonParser {
    ObjectMapper mapper = new ObjectMapper();

    public GeoJson parse(String json) {
        try {
            return mapper.readValue(json, GeoJson.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
