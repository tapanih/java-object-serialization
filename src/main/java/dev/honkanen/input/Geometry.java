package dev.honkanen.input;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.Serializable;


@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = LineString.class),
        @JsonSubTypes.Type(value = MultiLineString.class)
})
public sealed interface Geometry extends Serializable permits LineString, MultiLineString {
}

