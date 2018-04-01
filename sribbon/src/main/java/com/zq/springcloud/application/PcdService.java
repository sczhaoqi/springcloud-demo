package com.zq.springcloud.application;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/***
 * @author 01367477
 */
@Service
public class PcdService {
    @Autowired
    RestTemplate restTemplate;
    //对该方法创建了熔断器的功能，并指定了fallbackMethod熔断方法，熔断方法直接返回了�?个字符串，字符串为�?�hi,�?+name+�?,sorry,error!�?
    @HystrixCommand(fallbackMethod = "pcdError")
    public ResultBody pcdService(String lntlat) {
        String[] lntlats=lntlat.split(",");
        return restTemplate.getForObject("http://HELLOSERVICE/pcddata?longitude="+lntlats[0]+"&latitude="+lntlats[1],ResultBody.class);
    }

    public ResultBody pcdError(String lntlat) {
        ResultBody rsb=new ResultBody();
        rsb.setMessage("查询,"+lntlat+"失败!");
        rsb.setCode(404);
        return rsb;
    }

}

