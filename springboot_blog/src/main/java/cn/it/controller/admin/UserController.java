package cn.it.controller.admin;


import cn.it.domain.User;

import cn.it.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class UserController {


     @Autowired
    private UserService userService;

     @GetMapping
     public  String  admin(){

         return "admin/login";
     }

   @GetMapping("/index")
    public  String  index(){
        return "admin/index";
    }


//登录验证
     @PostMapping("/login")
     public  String login(@RequestParam String username, @RequestParam String password, HttpSession session,RedirectAttributes attributes){
         User user=userService.findByUsernameAndPassword( username,password );
         //RedirectAttributes attributes
       if(user!=null){


           //登录成功
           session.setAttribute("user",user);

           return "admin/index";


       }else {
           //登录失败
      attributes.addFlashAttribute("message", "用户名和密码错误");
           //attributes.addAttribute("message", "用户名和密码错误");
           return "redirect:/admin";


       }


     }



     // 登录退出@{/admin/logout}
    @GetMapping("/logout")
    public  String  logout( HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin";
    }


}
