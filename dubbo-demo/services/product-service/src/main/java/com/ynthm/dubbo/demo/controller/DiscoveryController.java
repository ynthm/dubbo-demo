// package com.ynthm.dubbo.demo.controller;
//
// import com.alibaba.nacos.api.annotation.NacosInjected;
// import com.alibaba.nacos.api.exception.NacosException;
// import com.alibaba.nacos.api.naming.NamingService;
// import com.alibaba.nacos.api.naming.pojo.Instance;
// import org.springframework.web.bind.annotation.*;
//
// import java.util.List;
//
/// **
// * @author ethan
// */
// @RestController
// @RequestMapping("/discovery")
// public class DiscoveryController {
//    @NacosInjected
//    private NamingService namingService;
//
//    @GetMapping(value = "/get")
//    @ResponseBody
//    public List<Instance> get(@RequestParam String serviceName) throws NacosException {
//        return namingService.getAllInstances(serviceName);
//    }
// }
