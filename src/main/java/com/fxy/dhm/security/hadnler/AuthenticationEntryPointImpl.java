package com.fxy.dhm.security.hadnler;


import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author fudonghui
 * @version 1.0
 * @date 2022/6/13 13:51
 */
@Component
public class AuthenticationEntryPointImpl  implements AuthenticationEntryPoint {

    /**
     * 当认证失败时调用
     * @param request
     * @param response
     * @param authException
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        int code = HttpStatus.UNAUTHORIZED.value();
        String msg = String.format("请求访问：%s，认证失败，无法访问系统资源", request.getRequestURI());
        try {
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

