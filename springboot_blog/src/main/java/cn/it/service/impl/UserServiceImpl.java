package cn.it.service.impl;

import cn.it.dao.UserDao;

import cn.it.domain.User;
import cn.it.service.UserService;

import cn.it.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User findByUsernameAndPassword(String username, String password) {
      /*  String password01= MD5Utils.code(password);*/
        return  userDao.findByUsernameAndPassword(username,MD5Utils.code(password));
    }
}
