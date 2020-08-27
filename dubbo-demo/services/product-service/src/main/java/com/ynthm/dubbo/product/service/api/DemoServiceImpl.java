package com.ynthm.dubbo.product.service.api;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Value;

/** @author ethan */
@DubboService(version = "${product.service.version}")
public class DemoServiceImpl implements DemoService {
  /** The default value of ${dubbo.application.name} is ${spring.application.name} */
  @Value("${dubbo.application.name}")
  private String serviceName;

  @Override
  public String sayHello(String name) {
    return String.format("[%s] : Hello, %s", serviceName, name);
  }
}
