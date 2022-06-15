package com.fxy.dhm.auth.service;

import com.fxy.dhm.entity.vo.R;

/**
 * @author fudonghui
 * @version 1.0
 * @date 2022/6/10 14:47
 */
public interface UserService {

    /**
     * 登录
     * @param data
     * @param request
     * @return
     */
    R login(String username,String password);
}
