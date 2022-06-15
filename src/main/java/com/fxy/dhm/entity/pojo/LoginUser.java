package com.fxy.dhm.entity.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fxy.dhm.dao.domain.SeUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

/**
 * @author fudonghui
 * @version 1.0
 * @date 2022/6/10 9:59
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginUser extends SeUser  implements Serializable, UserDetails {
    private static final long serialVersionUID = 1L;

    /**
     * 用户唯一标识
     */
    private String uuid;

    /**
     * 过期时间
     */
    private Long expireTime;

    /**
     * 权限列表
     */
    private Set<String> permissions;


    /**
     * 1号用户是管理员,没毛病吧
     * @param userId
     * @return
     */
    public static boolean isAdmin(Long userId) {
        return userId != null && 1L == userId;
    }
    @JsonIgnore
    public boolean isAdmin() {
        return isAdmin(this.getId());
    }
    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return false;
    }
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }
    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return false;
    }
}
