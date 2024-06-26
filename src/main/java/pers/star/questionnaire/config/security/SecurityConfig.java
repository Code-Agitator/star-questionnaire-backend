package pers.star.questionnaire.config.security;

import cn.hutool.core.util.IdUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.acls.AclPermissionEvaluator;
import org.springframework.security.acls.domain.*;
import org.springframework.security.acls.jdbc.BasicLookupStrategy;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.acls.model.PermissionGrantingStrategy;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import pers.star.questionnaire.auth.service.TokenService;
import pers.star.questionnaire.common.ErrorMessage;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableCaching
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Slf4j
public class SecurityConfig extends GlobalMethodSecurityConfiguration {
    @Resource
    private ObjectMapper objectMapper;

    @Resource
    private DataSource dataSource;

    @Resource
    private CacheManager cacheManager;

    @Resource
    private Environment environment;

    @Bean
    public SecurityFilterChain security(HttpSecurity http, UserDetailsService userDetailsService, TokenService tokenService) throws Exception {

        if (isDev()) {
            http
                    .authorizeRequests()
                    .antMatchers("/api/**/**").permitAll()
                    .antMatchers(swaggerUrlPermitAll()).permitAll()
                    .anyRequest().authenticated();
        } else {
            http
                    .authorizeRequests()
                    .antMatchers("/api/user/**").permitAll()
                    .antMatchers(swaggerUrlPermitAll()).permitAll()
                    .anyRequest().authenticated();
        }
        http.csrf().disable()
                .cors().configurationSource(corsConfigSource())
                .and()
                .formLogin().disable()
                .userDetailsService(userDetailsService)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .accessDeniedHandler(handleException());
        http.addFilterBefore(new JwtAuthenticationTokenFilter(tokenService), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    private boolean isDev() {
        return Arrays.asList(environment.getActiveProfiles()).contains("dev");
    }

    private AccessDeniedHandler handleException() {
        return (request, response, accessDeniedException) -> {
            logException(accessDeniedException, request);
            response.getWriter().println(objectMapper.writeValueAsString(new ErrorMessage(accessDeniedException.getMessage())));
        };
    }

    private void logException(Exception ex, HttpServletRequest request) {
        String traceId = IdUtil.fastSimpleUUID();
        String message = getExceptionMessage(ex);
        String throwAt = getThrowAt(ex);
        log.error("[{}] {} => at:[{}] track:[{}] {}", request.getMethod(), request.getRequestURI(), throwAt, traceId, message);
    }

    private String getThrowAt(Exception ex) {
        StackTraceElement[] stackTrace = ex.getStackTrace();
        if (ObjectUtils.isEmpty(stackTrace)) {
            return "";
        }
        StackTraceElement stackTraceElement = stackTrace[0];
        return stackTraceElement.getClassName() + ":" + stackTraceElement.getLineNumber();
    }

    private String getExceptionMessage(Exception exception) {
        StringBuilder message = new StringBuilder();
        Throwable ex = ExceptionUtils.getRootCause(exception);
        message.append(ex.getClass().getSimpleName()).append(":").append(ex.getMessage());
        return message.toString();
    }

    private String[] swaggerUrlPermitAll() {
        return new String[]{
                "/swagger-ui.html",
                "/swagger-resources/**",
                "/v2/api-docs",
                "/v3/api-docs",
                "/v3/api-docs/**/**",
                "/swagger-ui/*"
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
        AclPermissionEvaluator permissionEvaluator = new AclPermissionEvaluator(this.aclService());
        expressionHandler.setPermissionEvaluator(permissionEvaluator);
        return expressionHandler;
    }

    private LookupStrategy lookupStrategy() {
        return new BasicLookupStrategy(
                dataSource,
                this.aclCache(),
                this.aclAuthorizationStrategy(),
                new ConsoleAuditLogger()
        );
    }

    /**
     * Acl会被频繁访问，所以设置缓存相当有必要
     */
    private SpringCacheBasedAclCache aclCache() {
        return new SpringCacheBasedAclCache(
                this.cacheManager.getCache("acl"),
                this.permissionGrantingStrategy(),
                this.aclAuthorizationStrategy()
        );
    }

    private PermissionGrantingStrategy permissionGrantingStrategy() {
        return new DefaultPermissionGrantingStrategy(new ConsoleAuditLogger());
    }

    private AclAuthorizationStrategy aclAuthorizationStrategy() {
        return new AclAuthorizationStrategyImpl(
                new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    private JdbcMutableAclService aclService() {
        return new JdbcMutableAclService(dataSource, this.lookupStrategy(), this.aclCache());
    }


    private CorsConfigurationSource corsConfigSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.setAllowedMethods(Collections.singletonList("*"));
        configuration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


}
