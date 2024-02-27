package pers.star.questionnaire.interceptor;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pers.star.questionnaire.advice.exception.children.UnauthorizedException;
import pers.star.questionnaire.auth.pojo.TokenUserInfo;
import pers.star.questionnaire.auth.service.TokenService;
import pers.star.questionnaire.util.TokenUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@Slf4j
@Component
public class AuthorizationInterceptor extends BaseHandlerInterceptor {

    @Resource
    UserPermissionService userPermissionService;

    @Resource
    UserPermissionMapper permissionMapper;

    @Resource
    RedisUtil redisUtil;

    @Resource
    TokenService tokenService;

    public static final String USER_PERMISSION_CACHE_KEY_PREFIX = "user:permission:";

    @Override
    public boolean preHandle(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws Exception {
        List<UserPermission> pathNeedPermission = userPermissionService.selectListByPath(request.getServletPath(),
                request.getMethod());
        // 放行无需权限接口
        if (pathNeedPermission.isEmpty()) {
            return true;
        }
        // 获取用户tokenUserInfo 检查权限 不捕获异常 正常抛出并处理
        TokenUserInfo userInfo = TokenUtil.getOrSetTokenUserInfo(request, tokenService);
        List<UserPermission> userPermission = getUserPermissionByCache(userInfo.getId());
        if (!isAuthorized(pathNeedPermission, userPermission)) {
            throw new UnauthorizedException(NO_PERMISSION);
        }
        return true;
    }


    /**
     * 检测是否授权
     *
     * @param need            URL所需要的权限
     * @param userPermissions 用户具有的权限
     * @return 是否授权
     */
    public boolean isAuthorized(List<UserPermission> need, List<UserPermission> userPermissions) {
        for (UserPermission np : need) {
            boolean isAuthorized = userPermissions.stream().anyMatch(up -> up.getId().equals(np.getId()));
            if (isAuthorized) return true;
        }
        return false;
    }

    /**
     * 缓存获取用户权限
     * PS:无过期时间
     *
     * @param id 用户id
     * @return 用户权限列表
     */
    public List<UserPermission> getUserPermissionByCache(Integer id) {
        String key = USER_PERMISSION_CACHE_KEY_PREFIX + id;
        Object o = redisUtil.get(key);
        List<UserPermission> userPermissions;
        if (o == null) {
            userPermissions = permissionMapper.selectListByUserId(id);
            redisUtil.set(key, JSONUtil.toJsonStr(userPermissions));
            return userPermissions;
        }
        JSONArray objects = JSONUtil.parseArray(o);
        return objects.toList(UserPermission.class);
    }


    @Override
    public int getOrder() {
        return 0;
    }
}
