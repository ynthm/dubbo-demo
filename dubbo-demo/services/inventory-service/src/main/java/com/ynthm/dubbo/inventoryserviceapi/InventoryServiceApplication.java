package com.ynthm.dubbo.inventoryserviceapi;

import com.alibaba.nacos.api.config.ConfigType;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import com.ynthm.dubbo.product.service.api.DemoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

/** @author ethan */
@Slf4j
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@NacosPropertySource(
    dataId = "inventory-service.yaml",
    type = ConfigType.YAML,
    autoRefreshed = true)
public class InventoryServiceApplication {

  @DubboReference(version = "${product.service.version}")
  private DemoService demoService;

  @NacosValue(value = "${test:127.0.0.1}", autoRefreshed = true)
  private String test;

  public static void main(String[] args) {
    SpringApplication.run(InventoryServiceApplication.class, args);
  }

  @Bean
  public ApplicationRunner runner() {
    return args -> {
      log.info(test);
      log.info(demoService.sayHello("Ethan"));
    };
  }
}
