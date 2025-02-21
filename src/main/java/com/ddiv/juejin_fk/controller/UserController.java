package com.ddiv.juejin_fk.controller;

import com.ddiv.juejin_fk.pojo.ApiResult;
import com.ddiv.juejin_fk.pojo.JuejinUser;
import com.ddiv.juejin_fk.service.UserService;
import com.ddiv.juejin_fk.utils.TokenUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static com.ddiv.juejin_fk.controller.ArticleController.getID;

@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private HttpServletResponse httpServletResponse;

    @PostMapping("/api/register")
    public ApiResult<JuejinUser> register(@RequestBody JuejinUser request) {
        if (userService.findByUserName(request.getUserName()) != null) {
            httpServletResponse.setStatus(409);
            return ApiResult.error(409, "用户名已被占用");
        }
        userService.register(request);
        return ApiResult.success(201, "注册成功", userService.findByUserName(request.getUserName()));
    }

    @PostMapping("/api/login")
    public ApiResult<Object> login(@RequestBody JuejinUser request) {
        String userName = request.getUserName();
        String userPassword = request.getUserPassword();
        JuejinUser user = userService.findByUserName(userName);
        if (user == null || !userPassword.equals(user.getUserPassword())) {
            httpServletResponse.setStatus(401);
            return ApiResult.error(401, "用户名或密码错误");
        }
        Map<Object, Object> map = new HashMap<>();
        map.put("user_name", userName);
        map.put("user_id", user.getUserId());
        map.put("token", TokenUtils.generateToken(user, 0));
        return ApiResult.success(200, "登录成功", map);
    }

    @PutMapping(value = "/api/users/{user_id}/image/upload")
    public ApiResult<Object> imageUpload(@PathVariable(name = "user_id") Integer userId, @RequestHeader String token, @RequestBody Map<String, String> image) throws Exception {
        Jws<Claims> claims = TokenUtils.parseToken(token);
        if (!claims.getPayload().get("user_id").equals(userId)) {
            httpServletResponse.setStatus(401);
            return ApiResult.error(401, "非法请求，禁止修改他人数据");
        }
        userService.updateImage(userId, image.get("avater_base64"));
        return ApiResult.success(200, "修改头像成功");
    }

    @PutMapping(value = "/api/users/{user_id}/password")
    public ApiResult<Object> passwordUpdate(@PathVariable(name = "user_id") Integer userId, @RequestHeader String token, @RequestBody Map<String, String> passwords) {
        Jws<Claims> claims = TokenUtils.parseToken(token);
        if (!claims.getPayload().get("user_id").equals(userId)) {
            httpServletResponse.setStatus(401);
            return ApiResult.error(401, "非法请求，禁止修改他人数据");
        }
        JuejinUser user = userService.findByUserId(userId);
        if (!user.getUserPassword().equals(passwords.get("pre_password"))) {
            return ApiResult.error(401, "原密码错误");
        }
        userService.updatePassword(userId, passwords.get("neo_password"));
        return ApiResult.success(200, "密码修改成功");
    }

    @GetMapping("/api/users/{userId}/info")
    public ApiResult<Map<String, Object>> getUserInfo(@PathVariable Integer userId, @RequestHeader String token) {
        Integer realUser = getID(token);
        Map<String, Object> result;
        if (realUser.equals(userId)) {
            result = userService.getSelfInfo(userId);
        } else {
            result = userService.getUserInfo(userId);
        }
        return ApiResult.success(200, "获取成功", result);
    }

    @GetMapping(value = "/api/{user_id}")
    public ApiResult<Map> getSimpleUser(@PathVariable(name = "user_id") Integer userId) {
        JuejinUser user = userService.getSimpleUser(userId);
        if (user == null) {
            httpServletResponse.setStatus(401);
            return ApiResult.error(401, "用户不存在");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", user.getUserId());
        map.put("user_name", user.getUserName());
        map.put("user_image", user.getUserImage());
        map.put("user_resume", user.getUserResume());
        return ApiResult.success(200, "查询成功", map);
    }

    @GetMapping(value = "/api/users/{user_id}/image")
    public ApiResult<Object> getUserImage(@PathVariable(name = "user_id") Integer userId) {
        Map<String, Object> map = new HashMap<>();
        map.put("user_image", userService.getSimpleUser(userId).getUserImage());
        return ApiResult.success(200, "查询成功", map);
    }

    @PutMapping(value = "/api/users/{user_id}/username")
    public ApiResult<Object> updateUserName(@PathVariable(name = "user_id") Integer userId, @RequestHeader String token, @RequestBody Map<String, String> username) {
        Integer realUser = getID(token);
        if (!realUser.equals(userId)) {
            httpServletResponse.setStatus(401);
            return ApiResult.error(401, "不能修改他人用户名");
        }
        String userName = username.get("user_name");
        JuejinUser user = userService.findByUserName(userName);
        if (user != null) {
            httpServletResponse.setStatus(409);
            return ApiResult.error(409, "用户名重复");
        }
        user = new JuejinUser();
        user.setUserName(userName);
        user.setUserId(userId);
        userService.updateUserName(user);
        return ApiResult.success(200, "成功");
    }

    @DeleteMapping(value = "/api/user/{user_id}/delete")
    public ApiResult<Object> deleteUser(@PathVariable(name = "user_id") Integer userId, @RequestBody JuejinUser password) {
        JuejinUser user = userService.findByUserId(userId);
        if (user == null) {
            httpServletResponse.setStatus(401);
            return ApiResult.error(401, "用户不存在");
        }
        if (!user.getUserPassword().equals(password.getUserPassword())) {
            httpServletResponse.setStatus(402);
            return ApiResult.error(402, "密码错误");
        }
        userService.deleteUser(userId);
        return ApiResult.success(200, "成功注销");
    }

}
