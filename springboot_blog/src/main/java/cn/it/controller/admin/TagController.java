package cn.it.controller.admin;

import cn.it.dao.TagDao;
import cn.it.domain.Tag;
import cn.it.domain.Type;
import cn.it.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    public  String  tag(Model model,Integer pageNum){
        if(pageNum==null){
            pageNum=1;
        }
//使用排序
        Sort sort=new Sort(Sort.Direction.DESC,"id");
//properties是实体类的主键，不是数据库表的字段（主键）
        Pageable pageable = new PageRequest(pageNum-1, 3,sort);
        Page<Tag> tagPage=tagService.findAll(pageable);
        /*List<Tag> tags=tagService.findAll();*/
        model.addAttribute("tagPage",tagPage);
        return "admin/tags";
    }


    //跳转到添加页面
    @GetMapping("/tags/input")
    public  String  input()  {      return "admin/tags-input";
    }



//添加保存
    @PostMapping("/tags")
    public  String  input(Tag tag,Model model,RedirectAttributes attributes){
        Tag tag1=tagService.getTypeByName(tag.getName());
         if(tag1!=null){
             model.addAttribute("message","标签重复了");
             return "admin/tags-input";
         }
        tagService.save(tag);
        attributes.addFlashAttribute("message","添加成功");

        return  "redirect:/admin/tags";

    }



//删除
@GetMapping("/tags/{id}/delete")
public String delete(@PathVariable Long id, RedirectAttributes attributes) {
    tagService.deleteTag(id);
    attributes.addFlashAttribute("message", "删除成功");
    return "redirect:/admin/tags";
}






}
