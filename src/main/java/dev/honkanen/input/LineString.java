package dev.honkanen.input;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class LineString implements Serializable, Geometry {
    public String type;
    public double[][] coordinates;
}
