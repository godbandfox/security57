package com.fxy.dhm.security.hadnler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fxy.dhm.entity.vo.R;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author fudonghui
 * @version 1.0
 * @date 2022/6/13 13:59
 */
@Service
public class LogoutSuccessHandlerImpl  implements LogoutSuccessHandler {
    ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 从request中获取登录的用户
        // 如果存在用户
        // 删除token记录
        // 退出如果有日志 记录日志
        // 返回给前台展示退出成功
        try {
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(objectMapper.writeValueAsString(R.success("退出成功")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
