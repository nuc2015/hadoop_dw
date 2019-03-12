package cn.edu.llxy.dw.service.portal.impl;

import cn.edu.llxy.dw.core.bean.User;
import cn.edu.llxy.dw.dao.user.UserDao;
import cn.edu.llxy.dw.service.portal.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(value = "userService")
@Transactional(readOnly = false)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;


    @Override
    public User userLogin(String username, String password) {
        List<User> userList = userDao.queryByHql("from User u where u.username = ? and u.password = ?", username, password);
        User user = null;
        if(userList!=null && userList.size() > 0){
            user = userList.get(0);
        }
        return user;
    }
}
