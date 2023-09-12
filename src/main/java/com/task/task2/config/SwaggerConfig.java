package com.task.task2.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenApi() {
        Info info = new Info()
                .title("Lv3 주특기 과제")
                .version("v0.0.1")
                .description("Lv3 주특기 과제 API 명세서 입니다.");

        return new OpenAPI()
                .components(new Components())
                .info(info);
    }
}
