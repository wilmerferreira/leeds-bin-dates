package com.wilmerferreira;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class AddressClient extends AbstractClient {

    public AddressClient(String subscriptionKey) {
        super(subscriptionKey);
    }

    public Address[] getAddresses(String postcode) throws Exception {
        try (HttpClient client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build()) {
            String url = "https://api.leeds.gov.uk/public/addresses/v1/addresses?query="
                    + URLEncoder.encode(postcode, StandardCharsets.UTF_8);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Ocp-Apim-Subscription-Key", this.subscriptionKey)
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) { // TODO: Refactor this logic and handle other status codes and errors
                ObjectMapper mapper = new ObjectMapper();

                return mapper.readValue(
                        response.body(),
                        new TypeReference<>() {
                        });
            }

            return new Address[0];
        }
    }
}
