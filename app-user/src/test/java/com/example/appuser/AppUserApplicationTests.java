package com.example.appuser;

import com.example.appuser.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class AppUserApplicationTests {

    @Autowired
    UserServiceImpl userService;

    @Test
    @Transactional //事务型测试
    @Rollback //测试完成后数据回滚
    void contextLoads() {
        userService.getUserById(1);
    }

}
