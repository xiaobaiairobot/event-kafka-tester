package com.yunli.bigdata.eventbus.example.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.yunli.bigdata.dsep.service.common.ConfigurationLoader;
import com.yunli.bigdata.dsep.service.common.YamlConfigurationLoader;

/**
 * @author david
 * @date 2020/7/28 7:44 下午
 */
@Configuration
public class KafkaTemplateBeanFactory {

  @Bean
  public ConfigurationLoader configurationLoader(ApplicationContext context) {
    return new YamlConfigurationLoader(context);
  }
}
