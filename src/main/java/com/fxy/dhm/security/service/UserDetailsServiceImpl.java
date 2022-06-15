package com.fxy.dhm.security.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fxy.dhm.dao.domain.SeUser;
import com.fxy.dhm.entity.pojo.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;

/**
 * @author fudonghui
 * @version 1.0
 * @date 2022/6/13 15:10
 */
@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private UserPermissionService userPermissionService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        // 有唯一索引
        SeUser seUser = new SeUser().selectOne(new LambdaQueryWrapper<SeUser>().eq(SeUser::getUsername, username).last("limit 1"));
        if (ObjectUtils.isEmpty(seUser)) {
            log.info("登录用户: {} 不存在", username);
            throw new UsernameNotFoundException("登录用户：" + username + " 不存在");
        } else if (seUser.getStatus().equals(1)) {
            log.info("登录用户：{} 已被停用.", username);
            throw new RuntimeException("对不起，您的账号：" + username + " 已停用");
        }
        LoginUser loginUser = new LoginUser();
        BeanUtils.copyProperties(seUser,loginUser);
        // 获得用户权限
        return createLoginUser(loginUser);
    }

    public UserDetails createLoginUser(LoginUser loginUser) {
        // 赋值角色权限
        loginUser.setPermissions(userPermissionService.getMenuPermission(loginUser));
        return loginUser ;
    }
}
