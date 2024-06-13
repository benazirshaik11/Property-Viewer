package com.skenariolabs.propertyviewer.model.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeoapifyResponse {
    private List<Feature> features;

    // getters and setters

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Feature {
        private Geometry geometry;

        // getters and setters

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Geometry {
            private List<Double> coordinates;

            // getters and setters
            @JsonIgnore
            public Double getLatitude() {
                return coordinates.get(1);
            }

            @JsonIgnore
            public Double getLongitude() {
                return coordinates.get(0);
            }
        }
    }
}
