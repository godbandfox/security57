package com.fxy.dhm.security;

import com.fxy.dhm.security.filter.JwtAuthenticationTokenFilter;
import com.fxy.dhm.security.hadnler.AuthenticationEntryPointImpl;
import com.fxy.dhm.security.hadnler.LogoutSuccessHandlerImpl;
import com.fxy.dhm.security.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * @author fudon
 * @version 1.0
 * @date 2022/6/7 11:17
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)

public class DhmSecurityConfig {

    /**
     * clas定义用户认证逻辑
     */
    @Resource(type = UserDetailsServiceImpl.class)
    UserDetailsService userDetailsService;
    @Resource(type = JwtAuthenticationTokenFilter.class)
    JwtAuthenticationTokenFilter authenticationTokenFilter;
    /**
     * 认证失败处理类
     */
    @Resource
    private AuthenticationEntryPointImpl unauthorizedHandler;
    @Resource
    private LogoutSuccessHandlerImpl logoutSuccessHandler;

    /**
     * 新的配置方法无需继承WebSecurityConfigurerAdapter
     * 只需要声明配置类,并且提供SecurityFilterChainBean的方法
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        // 使用token不开启csrf,自定义请求头
        httpSecurity
                .cors().and()
                .csrf().disable()
                // 认证失败处理类
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                // 不使用session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests().antMatchers("/captchaImage", "/auth/login").anonymous()
                .antMatchers(HttpMethod.GET,
                        "/*.html",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js").permitAll()
                .antMatchers("/profile/**").anonymous()
                .antMatchers("/common/download**").anonymous()
                .antMatchers("/common/download/resource**").anonymous()
                .antMatchers("/swagger-ui.html").anonymous()
                .antMatchers("/swagger-resources/**").anonymous()
                .antMatchers("/webjars/**").anonymous()
                .antMatchers("/*/api-docs").anonymous()
                .antMatchers("/druid/**").anonymous()
                .antMatchers("/ws/**").permitAll()
                .antMatchers("/doc.html/**").permitAll()
                .antMatchers("/wss/**").permitAll()
                .antMatchers("/mini/**").permitAll()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated()
                .and()
                // h2控制台,因为有内嵌iframe,所以被security禁止了,手动关闭
                .headers().frameOptions().disable();
        // 退出登录处理器
        httpSecurity.logout().logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler);
        // 添加JWT filter
        httpSecurity.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 获取AuthenticationManager（认证管理器），登录时认证使用
     *
     * @param authBuilder
     * @return
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


}

