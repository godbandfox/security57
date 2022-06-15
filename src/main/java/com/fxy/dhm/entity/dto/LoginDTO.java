package com.fxy.dhm.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author fudonghui
 * @version 1.0
 * @date 2022/6/13 17:44
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {
    String username;
    String password;
    String code;
    String uuid;
}
