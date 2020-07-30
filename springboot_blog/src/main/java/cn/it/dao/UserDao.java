package cn.it.dao;

import cn.it.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface UserDao extends JpaRepository<User,Long> {
 public User  findByUsernameAndPassword(String username,String password);

/*  public User  findByUsernam(String username,String password);*/
}
