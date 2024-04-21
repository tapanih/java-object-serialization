package dev.honkanen.input;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Feature implements Serializable {
    public String type;
    public Geometry geometry;
}

