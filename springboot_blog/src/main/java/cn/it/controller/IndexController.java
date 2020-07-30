package cn.it.controller;


import cn.it.domain.Blog;
import cn.it.domain.Type;
import cn.it.service.BlogService;
import cn.it.service.TagService;
import cn.it.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private  BlogService  blogService;

    @Autowired
    private  TypeService  typeService;
    @Autowired
    private  TagService  tagService;



    @GetMapping("/")
    public String index(@PageableDefault(size = 2,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,Model model){


        //Page<Blog> blogPage= blogService.findAll(pageable);//获取所有的博客
        //获取分类标签
        //List<Type> types=typeService.listTypeTop(6);
        model.addAttribute("page", blogService.findAll(pageable));//获取所有的博客
        model.addAttribute("types",typeService.listTypeTop(2));//获取分类
        model.addAttribute("tags",tagService.listTagTop(2));//获取标签

        //博客推荐
        model.addAttribute("recommendBlogs",blogService.listRecommendBlogTop(2));


    return "index";
}



//全局搜索
     @PostMapping("/search")
    public  String search ( @PageableDefault(size = 3,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                            @RequestParam String query,Model model){
        /* Page<Blog> blogPage=blogService.findbyQuery("%"+query+"%",pageable);*/
         model.addAttribute("page",blogService.findbyQuery("%"+query+"%",pageable));

         return "search01";//跳转到搜索页面

}



//newblogList

    @GetMapping("/footer/newblog")
    public String newblogs(Model model) {
        model.addAttribute("newblogs", blogService.listRecommendBlogTop(3));
        return "_fragments :: newblogList";
    }


}













    /*//普通查询
    @GetMapping("/")
    public  String  index(Model model){


        List<Blog> blogList=blogService.findAll();
        model.addAttribute("blogList",blogList);

        return  "index01";
    }*/


    //分页
/* @GetMapping("/")
    public String  index( Model model,Integer pageNum){
        //List<Type> types=typeService.findAll();
        if(pageNum==null){
            pageNum=1;
        }
//使用排序
        Sort sort=new Sort(Sort.Direction.DESC,"id");
//properties是实体类的主键，不是数据库表的字段（主键）
        Pageable pageable = new PageRequest(pageNum-1, 3,sort);
        Page<Blog> blogPage= blogService.findAll(pageable);
        model.addAttribute("blogPage",blogPage);
        return "index01";
    }*/












/*
    @GetMapping("/")
    public String index(@PageableDefault(size = 2, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        Model model) {
        model.addAttribute("page",blogService.listBlog(pageable));
        model.addAttribute("types", typeService.listTypeTop(6));
        model.addAttribute("tags", tagService.listTagTop(10));
        model.addAttribute("recommendBlogs", blogService.listRecommendBlogTop(8));
        return "index";
    }
*/




/*

    @GetMapping("/footer/newblog")
    public String newblogs(Model model) {
        model.addAttribute("newblogs", blogService.listRecommendBlogTop(3));
        return "_fragments :: newblogList";
    }

*/



