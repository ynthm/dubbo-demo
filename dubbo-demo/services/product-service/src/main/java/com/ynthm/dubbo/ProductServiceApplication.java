package com.ynthm.dubbo;

import com.alibaba.nacos.api.config.ConfigType;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

/** @author ethan */
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@NacosPropertySource(dataId = "product-service.yaml", type = ConfigType.YAML, autoRefreshed = true)
public class ProductServiceApplication {

  @NacosValue(value = "${test:127.0.0.1}", autoRefreshed = true)
  private String test;

  public static void main(String[] args) {
    ConfigurableApplicationContext ac =
        SpringApplication.run(ProductServiceApplication.class, args);

    System.out.println(ac.getEnvironment().getProperty("test"));
  }
}
