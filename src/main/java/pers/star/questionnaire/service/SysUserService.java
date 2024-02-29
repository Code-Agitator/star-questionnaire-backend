package pers.star.questionnaire.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.access.prepost.PostAuthorize;
import pers.star.questionnaire.pojo.user.SysUser;

import java.util.List;

public interface SysUserService extends IService<SysUser> {
    List<SysUser> findAll();

    @PostAuthorize("hasPermission(returnObject,'READ')")
    SysUser findById(int id);
}

