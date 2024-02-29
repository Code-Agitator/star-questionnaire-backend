package pers.star.questionnaire;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import pers.star.questionnaire.service.SysUserService;

import javax.annotation.Resource;

@SpringBootTest
public class AclTest {
    @Resource
    private SysUserService sysUserService;
    @Test
    @WithMockUser("root")
    public void test(){
        System.out.println(sysUserService.findAll());
    }
}
