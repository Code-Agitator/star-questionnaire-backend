package pers.star.questionnaire.auth.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pers.star.questionnaire.auth.properties.TokenProperties;
import pers.star.questionnaire.auth.service.TokenService;
import pers.star.questionnaire.auth.service.impl.TokenServiceImpl;

/**
 * TokenService 自动配置
 */
@EnableConfigurationProperties(TokenProperties.class)
@Configuration
public class TokenConfig {

    @Bean
    public TokenService tokenService(TokenProperties tokenProperties) {
        return new TokenServiceImpl(tokenProperties);
    }
}
