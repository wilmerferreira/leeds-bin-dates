package com.wilmerferreira;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class WasteClient extends AbstractClient {
    public WasteClient(String subscriptionKey) {
        super(subscriptionKey);
    }

    public BinDate[] getBinDates(String uprn) throws Exception {
        try (HttpClient client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build()) {
            // TODO: Implement optional arguments &startDate=2026-10-19&endDate=2027-04-05
            String url = "https://api.leeds.gov.uk/public/waste/v1/BinsDays?uprn="
                    + URLEncoder.encode(uprn, StandardCharsets.UTF_8);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Ocp-Apim-Subscription-Key", this.subscriptionKey)
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {// TODO: Refactor this logic and handle other status codes and errors
                ObjectMapper mapper = new ObjectMapper();

                return mapper.readValue(
                        response.body(),
                        new TypeReference<>() {
                        });
            }

            return new BinDate[0];
        }
    }
}
