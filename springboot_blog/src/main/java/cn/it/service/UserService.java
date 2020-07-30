package cn.it.service;


import cn.it.domain.User;

public interface UserService {
 public User  findByUsernameAndPassword(String username,String password);

}
