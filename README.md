# app-parent
### 项目版本：

组件|版本|说明
:---:|:---:|:---:
Java|1.8|√
Maven|3.3.9|√
Spring Boot|2.2.8.RELEASE|√
Spring Cloud|Hoxton.RELEASE|√
Spring Cloud Alibaba|2.2.0.RELEASE|×
mybatis-spring-boot|2.1.3|√
mybatis-generator-maven-plugin|1.3.2|√
lombok|1.16.18|√
Zuul|1.3.1|√
Swagger2|2.9.2|√
Nacos|1.1.4|√
Spring Cloud Gateway|2.2.0|√
Redis|2.x.x|限流
Zipkin|xxx|链路追踪

### 模块说明：

模块|说明|
:---:|:---:
app-eureka-server|Eureka注册中心
app-zuul|网关微服务 + 自定义Filter + Redis滑动窗口限流
app-user|用户微服务
app-order|订单微服务
app-gateway|gateway网关
zipkin-server-2.21.5-exec.jar|zipkin服务端,单独启动


### swagger2 url：
http://${ip}:{port}/swagger-ui.html

### zipkin url
http://${ip}:9411


