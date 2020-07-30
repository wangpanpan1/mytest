package cn.it.controller.admin;


import cn.it.domain.Blog;
import cn.it.domain.User;
import cn.it.service.BlogService;
import cn.it.service.TagService;
import cn.it.service.TypeService;
import cn.it.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class BlogController {
    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    //查询所有 @GetMapping("/blogs")

   @GetMapping("/blogs")
    public  String findAll(Model model){
       // setTypeAndTag(model);
        model.addAttribute("types",typeService.listType());
        List<Blog> blogs=blogService.findAll();
        model.addAttribute("blogs",blogs);
        return "admin/blogs";
    }

//跳转添加页面/admin/blogs/input
   @GetMapping("/blogs/input")
    public String blog_input( Model model){

       setTypeAndTag(model);
       model.addAttribute("blog", new Blog());
        return "admin/blogs-input";
   }

// 分类和标签的获取
    private void setTypeAndTag(Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());

    }


  //保存/admin/blogs
    @PostMapping("/blogs")
    public String save(Blog blog,HttpSession session,RedirectAttributes redirectAttributes){
    //保存分类
     blog.setType(typeService.findById(blog.getType().getId()));
        //保存标签
        blog.setTags(tagService.listTag(blog.getTagIds()));
   //保存用户
        blog.setUser((User)session.getAttribute("user"));
       Blog b = null;
        if(blog.getId()==null){
            b=blogService.saveBlog(blog);
        }else{
            b=blogService.updateBlog(blog.getId(),blog);
        }
        if(b==null){
            redirectAttributes.addFlashAttribute("message","添加失败");
        }else {
            redirectAttributes.addFlashAttribute("message","添加成功");
        }
        return "redirect:/admin/blogs";
    }


//修改/admin/blogs/{id}/input(id=${blog.id})
   @GetMapping("/blogs/{id}/input")
public String editInput(@PathVariable Long id, Model model) {

    setTypeAndTag(model);//获取当前博客的分类和标签
   Blog blog= blogService.getBlog(id);
    blog.init();
    model.addAttribute("blog",blog);
   return "admin/blogs-input";
}



//admin/blogs/search搜索

    @PostMapping("/blogs/search")
    public String search(BlogQuery blogQuery,Model model){

        List<Blog> blogs=blogService.findBlog(blogQuery);

        System.out.println(blogs);
        model.addAttribute("blogs" ,blogs);
        return "admin/blogs :: blogList";
    }





    //删除@{/admin/blogs/{id}/delete(id=${blog.id})}
    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes) {
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/blogs";
    }















}
