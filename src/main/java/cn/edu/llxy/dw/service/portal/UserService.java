package cn.edu.llxy.dw.service.portal;

import cn.edu.llxy.dw.core.bean.User;

public interface UserService {

    /**
     * 用户登录
     * @param username  用户名
     * @param password  密码
     * @return
     */
    public User userLogin(String username, String password);
}
