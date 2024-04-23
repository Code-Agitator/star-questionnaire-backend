package pers.star.questionnaire.controller;

import org.springframework.web.bind.annotation.*;
import pers.star.questionnaire.pojo.user.SysUser;
import pers.star.questionnaire.service.SysUserService;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/admin/user")
public class AdminUserController {
    @Resource
    private SysUserService sysUserService;

    @GetMapping("")
    public List<SysUser> getUsers() {
        return sysUserService.findAll();
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Integer id) {
        sysUserService.removeById(id);
    }

}
