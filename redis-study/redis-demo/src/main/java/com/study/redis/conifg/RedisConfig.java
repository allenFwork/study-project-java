package com.study.redis.conifg;

import io.lettuce.core.ReadFrom;
import org.springframework.boot.autoconfigure.data.redis.LettuceClientConfigurationBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisConfig {

    @Bean // 进行Redis的主从配置实现，主节点写数据，从节点读数据
    public LettuceClientConfigurationBuilderCustomizer clientConfigurationBuilderCustomizer() {
        /**
         * - MASTER：从主节点读取
         * - MASTER_PREFERRED：优先从master节点读取，master不可用才读取replica
         * - REPLICA：从slave（replica）节点读取
         * - REPLICA _PREFERRED：优先从slave（replica）节点读取，所有的slave都不可用才读取master
         */
        return clientConfigurationBuilder -> clientConfigurationBuilder.readFrom(ReadFrom.REPLICA_PREFERRED);
    }

}
