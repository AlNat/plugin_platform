package dev.alnat.plugin_platform.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Александр Натаров on 15.01.2021
 */
@Configuration
public class OpenAPIConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Plugin platform")
                        .version("1.0.0")
                        .description("Платформа для запуска плагинов"));
    }

}
