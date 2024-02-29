package pers.star.questionnaire.service.impl;

import cn.hutool.core.bean.BeanUtil;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pers.star.questionnaire.domain.SysUserDetails;
import pers.star.questionnaire.pojo.user.SysUser;
import pers.star.questionnaire.service.SysUserService;

import javax.annotation.Resource;

@Service
public class DomainUserDetailsService implements UserDetailsService {
    @Resource
    private SysUserService sysUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserService.lambdaQuery().eq(SysUser::getUsername, username).one();
        return BeanUtil.copyProperties(sysUser, SysUserDetails.class);
    }
}
