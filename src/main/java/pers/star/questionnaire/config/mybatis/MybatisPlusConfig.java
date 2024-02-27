package pers.star.questionnaire.config.mybatis;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * Mybatis plus 配置
 *
 * @author Agitator
 */
@Configuration
@MapperScan(basePackages = "pers.star.questionnaire.mapper")
@EnableTransactionManagement
public class MybatisPlusConfig {

    @Resource
    ObjectMapper objectMapper;

    /**
     * 分页插件
     *
     * @return interceptor
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    @PostConstruct
    public void postConstruct() {
        JacksonTypeHandler.setObjectMapper(objectMapper);
    }


}
