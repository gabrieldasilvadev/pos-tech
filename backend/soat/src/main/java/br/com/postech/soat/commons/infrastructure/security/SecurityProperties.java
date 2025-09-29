package br.com.postech.soat.commons.infrastructure.security;

import java.time.Duration;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "security")
public class SecurityProperties {

    private final Jwt jwt = new Jwt();

    @Getter
    @Setter
    public static class Jwt {
        /**
         * Secret key used to sign and validate JWT tokens. It must have at least 32 characters
         * to be compatible with the HS256 algorithm requirements.
         */
        private String secret = "change-me-change-me-change-me-change-me";

        /**
         * Token expiration window. The default value is two hours.
         */
        private Duration expiration = Duration.ofHours(2);
    }
}
