import com.example.apputils.AppUtilsApplication;
import com.example.apputils.redis.api.IRedisService;
import com.example.apputils.redis.spring.impl.SpringRedisServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = AppUtilsApplication.class)
class AppRedisClusterApplicationTests {

    @Autowired
    IRedisService redisService;

    @Test
    void contextLoads() {
        String id = redisService.getID();
        System.out.println(id);
    }


}
