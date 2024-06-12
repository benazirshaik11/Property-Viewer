package com.skenariolabs.propertyviewer.service;

import com.skenariolabs.propertyviewer.model.repo.Building;
import com.skenariolabs.propertyviewer.model.response.GeoapifyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class GeoapifyService {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Value("${geoapify.api.url}")
    private String geoapifyUrl;

    @Value("${geoapify.api.key}")
    private String geoapifyApiKey;


    public Mono<GeoapifyResponse.Feature.Geometry> fetchCoordinates(Building building) {
        String address = String.format("%s %s, %s, %s, %s",
                building.getNumber(),
                building.getStreet(),
                building.getCity(),
                building.getPostCode(),
                building.getCountry());

        String geoapifyUrl = String.format(this.geoapifyUrl + "?text=%s&apiKey=%s", address, geoapifyApiKey);

        return webClientBuilder.build()
                .get()
                .uri(geoapifyUrl)
                .retrieve()
                .bodyToMono(GeoapifyResponse.class)
                .map(response -> response.getFeatures().getFirst().getGeometry());
    }
}