package orgsarava338.sadhanakaala.infra.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({ "local", "dev" })
public class SwaggerConfig {

    @Bean
    OpenAPI sadhanaKaalaOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("SadhanaKaala API")
                        .version("v1")
                        .description("Timer, Sessions, Streaks, OAuth APIs"));
    }
}
