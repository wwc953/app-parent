import com.example.apputils.AppUtilsApplication;
import com.example.apputils.redis.api.IRedisService;
import com.example.apputils.redis.lock.api.IDistributeLockService;
import com.example.apputils.redis.spring.impl.SpringRedisServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest(classes = AppUtilsApplication.class)
class AppRedisClusterApplicationTests {

    @Autowired
    IRedisService redisService;

    @Test
    void contextLoads() {
        String id = redisService.getID();
        System.out.println(id);
    }

    /**
     * redisson 分布式锁使用案例
     */
    @Autowired
    IDistributeLockService lockService;
    int total = 10;
    @Test
    void lockTest() {
        String lockKey = "redisLockKey";
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 100; i++) {
            executorService.execute(() -> {
                lockService.lock(lockKey, 60);
                if (total > 0) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    total--;
                }
                System.out.println("剩余: " + total);
                boolean heldByCurrentThread = lockService.isHeldByCurrentThread(lockKey);
                if (heldByCurrentThread) {
                    lockService.unLock(lockKey);
                }
            });
        }

    }
}
