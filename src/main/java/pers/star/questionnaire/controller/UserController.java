package pers.star.questionnaire.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pers.star.questionnaire.advice.exception.children.ServerException;
import pers.star.questionnaire.anno.CurrentUser;
import pers.star.questionnaire.auth.pojo.TokenInfo;
import pers.star.questionnaire.auth.pojo.TokenUserInfo;
import pers.star.questionnaire.auth.service.TokenService;
import pers.star.questionnaire.dto.LoginDto;
import pers.star.questionnaire.pojo.user.SysUser;
import pers.star.questionnaire.service.SysUserService;

import javax.annotation.Resource;
import java.util.Objects;

@RestController
@CrossOrigin
public class UserController {
    @Resource
    private SysUserService sysUserService;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private TokenService tokenService;

    @PostMapping("/api/user/login")
    public TokenInfo login(@RequestBody LoginDto login) {
        String username = login.getUsername();
        String password = login.getPassword();
        SysUser sysUser = sysUserService.lambdaQuery().eq(SysUser::getUsername, username).last("limit 1").one();
        if (Objects.isNull(sysUser)) {
            throw new ServerException("用户不存在");
        }
        String passwordInDb = sysUser.getPassword();
        if (!passwordEncoder.matches(password, passwordInDb)) {
            throw new ServerException("密码不正确");
        }
        String token = tokenService.createToken(TokenUserInfo.builder()
                .id(sysUser.getId())
                .wxOpenid(sysUser.getWxOpenid())
                .username(sysUser.getUsername())
                .build(), 99999999999999L);
        return TokenInfo.builder()
                .token(token)
                .build();
    }

    @PostMapping("/api/user/register")
    public SysUser register(@RequestBody SysUser user) {
        String username = user.getUsername();
        String password = user.getPassword();
        if (StringUtils.isBlank(password)) {
            throw new ServerException("密码不能为空");
        }
        if (sysUserService.lambdaQuery().eq(SysUser::getUsername, username).exists()) {
            throw new ServerException("用户名已存在");
        }
        user.setPassword(passwordEncoder.encode(password));
        user.setId(null);

        sysUserService.save(user);
        return user;
    }

    @GetMapping("/api/user/info")
    public SysUser userInfo(@CurrentUser TokenUserInfo userInfo) {
        return sysUserService.getById(userInfo.getId());
    }

    @PutMapping("/api/user/info")
    public SysUser updateUser(@CurrentUser TokenUserInfo userInfo, @RequestBody SysUser user) {
        sysUserService.updateById(user);
        return user;
    }

}
