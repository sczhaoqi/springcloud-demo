package com.zq.springcloud.application.controller;

import com.zq.springcloud.application.service.PcdDataService;
import com.zq.springcloud.application.beans.PcdData;
import com.zq.springcloud.application.beans.ResultBody;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/***
 * @author 01367477
 */
@RestController
public class ServiceInstanceRestController  {

    private static final Logger logger=Logger.getLogger(ServiceInstanceRestController.class);
    @Autowired
    private PcdDataService service;

    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping("/services/{applicationName}")
    public List<ServiceInstance> serviceInstancesByApplicationName(
            @PathVariable String applicationName) {
        return this.discoveryClient.getInstances(applicationName);
    }

    @RequestMapping("/")
    public String sayhello() {
        return "hello";
    }
    @Value("${server.port}")
    String port;
    @RequestMapping("/hi")
    public String home(@RequestParam String name) {
        return "hi "+name+",i am from port:" +port;
    }

    @RequestMapping(value = "/pcddata",method = RequestMethod.GET,produces = { "application/json;charset=UTF-8" })
    public ResultBody psdData(@RequestParam(value="longitude")double longitude,@RequestParam(value="latitude")double latitude) throws IOException {
        PcdData pcdData=new PcdData();
        pcdData.setLongitude(longitude);
        pcdData.setLatitude(latitude);
        logger.info(longitude+";"+latitude);
        service.otainPCDData(pcdData);
        return new ResultBody(200,"Success",pcdData);
    }
}

