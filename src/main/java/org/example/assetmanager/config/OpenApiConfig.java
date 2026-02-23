package org.example.assetmanager.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI assetManagerOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Asset Manager API")
                        .description("This is the Asset Manager API")
                        .version("1.0")
                );
    }


}
