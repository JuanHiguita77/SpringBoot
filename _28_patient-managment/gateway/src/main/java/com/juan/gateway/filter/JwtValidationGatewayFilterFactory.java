package com.juan.gateway.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class JwtValidationGatewayFilterFactory extends AbstractGatewayFilterFactory<Object>
{

    private final WebClient webClient;

    // Constructor to initialize the WebClient with the base URL of the authentication service
    public JwtValidationGatewayFilterFactory(WebClient.Builder webClientBuilder, @Value("${auth.service.url}") String authServiceUrl)
    {
        this.webClient = webClientBuilder.baseUrl(authServiceUrl).build();
    }

    @Override
    public GatewayFilter apply(Object config)
    {
        return (exchange, chain) ->
        {
            // Extract the Authorization header from the incoming request
            String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            // Log the received token (for debugging purposes, avoid logging sensitive data in production)
            System.out.println("Received Authorization header: " + token);

            // Check if the token is missing or does not start with "Bearer "
            if (token == null || !token.startsWith("Bearer "))
            {
                System.out.println("Invalid or missing token. Rejecting request.");
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            // Validate the token by making a GET request to the authentication service
            return webClient.get()
                    .uri("/validate")
                    .header(HttpHeaders.AUTHORIZATION, token)
                    .retrieve()
                    .toBodilessEntity()
                    .doOnSuccess(response -> System.out.println("Token validation successful."))
                    .doOnError(error -> System.out.println("Token validation failed: " + error.getMessage()))
                    .then(chain.filter(exchange));
        };
    }
}
