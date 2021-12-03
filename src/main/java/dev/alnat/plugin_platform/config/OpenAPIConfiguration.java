package dev.alnat.plugin_platform.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Александр Натаров on 23.07.2021
 */
@Configuration
public class OpenAPIConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("UIN searcher")
                        .version("1.0.0")
                        .description("REST API для запуска репоисков по УИНам в ГИС ГМП"));
    }

}
