package pers.star.questionnaire.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import pers.star.questionnaire.mapper.SysUserMapper;
import pers.star.questionnaire.pojo.user.SysUser;
import pers.star.questionnaire.service.SysUserService;

import java.util.List;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Override
    public List<SysUser> findAll() {
        return list();
    }

    @Override
    public SysUser findById(int id) {
        return getById(id);
    }
}
