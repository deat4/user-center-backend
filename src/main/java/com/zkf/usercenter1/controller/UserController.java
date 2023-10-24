package com.zkf.usercenter1.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zkf.usercenter1.common.BaseResponse;
import com.zkf.usercenter1.common.ErrorCode;
import com.zkf.usercenter1.common.ResultUtils;
import com.zkf.usercenter1.exception.BusinessException;
import com.zkf.usercenter1.model.domain.User;
import com.zkf.usercenter1.model.domain.request.UserLoginRequest;
import com.zkf.usercenter1.model.domain.request.UserRegisterRequest;
import com.zkf.usercenter1.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.stream.Collectors;

import static com.zkf.usercenter1.contant.UserConstant.ADMIN_ROLE;
import static com.zkf.usercenter1.contant.UserConstant.USER_LOGIN_STATE;

/**
 * 用户接口
 *
 * @author zkf
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;


    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String planetCode = userRegisterRequest.getPlanetCode();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }

        long result = userService.UserRegister(userAccount, userPassword, checkPassword, planetCode);
        return ResultUtils.success(result);
    }

    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();

        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAM_ERROR);

        }

        User user = userService.userLogin(userAccount, userPassword, request);
        return ResultUtils.success(user);
    }

    @PostMapping("/logout")
    public BaseResponse<Integer> userLogout(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }

        int userLogout = userService.userLogout(request);

        return ResultUtils.success(userLogout);
    }

    @GetMapping("/current")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request) {
        Object userObject = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObject;
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.NO_LOGIN);
        }

        Long userid = currentUser.getId();
        //todo 校验用户是否合法
        User user = userService.getById(userid);
        User safetyUser = userService.getSafetyUser(user);
        return ResultUtils.success(safetyUser);

    }


    @GetMapping("/search")
    public BaseResponse<List<User>> searchUser(String username, HttpServletRequest request) {
        if (isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(username)) {
            queryWrapper.like("username", username);
        }
        List<User> userList = userService.list(queryWrapper);

        List<User> collect = userList.stream().map(user -> userService.getSafetyUser(user)).collect(Collectors.toList());

        return ResultUtils.success(collect);

    }

    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUser(@RequestBody long id, HttpServletRequest request) {

        //鉴权，仅管理员可以查询
        if (isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }

        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_NULL_ERROR);
        }

        boolean b = userService.removeById(id);

        return ResultUtils.success(b);

    }


    /**
     * 是否为管理员
     *
     * @param request
     * @return
     */
    private boolean isAdmin(HttpServletRequest request) {
        //鉴权，仅管理员可以查询
        Object userObject = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObject;
        return user == null || !user.getUserRole().equals(ADMIN_ROLE);

    }

}
