package dev.honkanen.input;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GeoJson implements Serializable {

    public String type;
    public String name;
    public Feature[] features;

    public GeoJson() {
    }
}
