package com.example.appuser.remote;

import com.example.appuser.remote.impl.OrderServiceFeignImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "app-order", fallback = OrderServiceFeignImpl.class)
public interface IOrderServiceFeign {

    @GetMapping(value = "/order/{param}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String callOrder(@PathVariable(value = "param") String param);

    @GetMapping(value = "/getOrder/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getOrderFromOrder(@PathVariable(value = "id") Integer id);

    @RequestMapping(value = "/custevaluation/uploadFile", method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String uploadFile(@RequestPart("file") MultipartFile multipartFile,
                             @RequestParam("custId") String custId);

}
