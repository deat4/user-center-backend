package com.zkf.usercenter1.model.domain.request;


import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户注册请求体
 *
 * @author zkf
 */

@Data
public class UserRegisterRequest implements Serializable {


    @Serial
    private static final long serialVersionUID = 8425166609489122420L;

    private String userAccount;

    private String userPassword;

    private String checkPassword;

    private String planetCode;
}
