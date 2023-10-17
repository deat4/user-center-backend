package com.zkf.usercenter1.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class UserLoginRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 8598891150527509926L;
    private String userAccount;

    private String userPassword;
}
