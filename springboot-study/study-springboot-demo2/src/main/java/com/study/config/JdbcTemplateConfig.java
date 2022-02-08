package com.study.config;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.autoconfigure.jdbc.JdbcProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

/**
 * jdbcTemplate自动装配
 */
@Configuration
@ConditionalOnClass({DataSource.class, JdbcTemplate.class})
@ConditionalOnSingleCandidate(DataSource.class)
@AutoConfigureAfter(DataSourceConfig.class)
@EnableConfigurationProperties(JdbcProperties.class)
public class JdbcTemplateConfig {

    @Configuration
    static class JdbcTemplateConfiguration {

        private final DataSource dataSource;
        private final JdbcProperties jdbcProperties;

        JdbcTemplateConfiguration(DataSource dataSource, JdbcProperties jdbcProperties) {
            this.dataSource = dataSource;
            this.jdbcProperties = jdbcProperties;
        }

        /**
         * jdbcTemplate配置
         *
         * @return
         */
        @Bean
        @Primary
        @ConditionalOnMissingBean(JdbcOperations.class)
        public JdbcTemplate jdbcTemplate() {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(this.dataSource);
            JdbcProperties.Template template = this.jdbcProperties.getTemplate();
            jdbcTemplate.setFetchSize(template.getFetchSize());
            jdbcTemplate.setMaxRows(template.getMaxRows());
            if (template.getQueryTimeout() != null) {
                jdbcTemplate.setQueryTimeout((int) template.getQueryTimeout().getSeconds());
            }
            return jdbcTemplate;
        }

    }

    @Configuration
    @Import(JdbcTemplateConfiguration.class)
    static class NamedParameterJdbcTemplateConfiguration {

        @Bean
        @Primary
        @ConditionalOnSingleCandidate(JdbcTemplate.class)
        @ConditionalOnMissingBean(NamedParameterJdbcTemplateConfiguration.class)
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(JdbcTemplate jdbcTemplate) {
            return new NamedParameterJdbcTemplate(jdbcTemplate);
        }

    }

}
