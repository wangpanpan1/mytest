package cn.it.controller.admin;

import cn.it.domain.Type;
import cn.it.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    //查询所有
    @GetMapping("/types")
    public String type( Model model,Integer pageNum){
        //List<Type> types=typeService.findAll();
        if(pageNum==null){
            pageNum=1;
        }
//使用排序
        Sort sort=new Sort(Sort.Direction.DESC,"id");
//properties是实体类的主键，不是数据库表的字段（主键）

        Pageable pageable = new PageRequest(pageNum-1, 3,sort);
        Page<Type> typePage=typeService.findAll(pageable);

/*        //获取总页数
        int getTotalPages();
        //获取总记录数
        long getTotalElements();
//获取列表数据
        List<T> getContent();*/



        model.addAttribute("pageInfo",typePage);

        return "admin/types";

    }












   // @{/admin/types/input}
    @GetMapping("/types/input")
    public  String  input(){

        return "admin/types-input";
    }


    //添加功能@{/admin/types/save}
@PostMapping("/types/save")
public  String  save(Type type,Model model,RedirectAttributes attributes){

        //根据保存type对象的name进行查询( type.getName();)

   Type types=typeService.getTypeByName(type.getName());

   if(types!=null){
//有重复的添加
       model.addAttribute("message","重复的添加");
    return "admin/types-input";
   }
    typeService.save(type);
    attributes.addFlashAttribute("message","添加成功");
   return "redirect:/admin/types";
        //typeService.save(type);

}










    //添加功能
  /*  @PostMapping("/types/save")
    public  String save(@Valid Type type, BindingResult result, RedirectAttributes attributes){

        Type type1 = typeService.getTypeByName(type.getName());
        if (type1 != null) {
            attributes.addFlashAttribute("message", "新增失败");
            return "redirect:/admin/types/input";
        }
       *//* if (result.hasErrors()) {
            attributes.addFlashAttribute("message", "新增失败");
            return "admin/types-input";
        }*//*
        Type t = typeService.save(type);
        if (t == null ) {
            attributes.addFlashAttribute("message", "新增失败");
        } else {
            attributes.addFlashAttribute("message", "新增成功");
        }




        return   "redirect:/admin/types";
    }*/

    //id删除
    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {

        typeService.deleteType(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/types";
    }

//修改
    @GetMapping("/types/{id}/input")
    public String  edit(@PathVariable Long id,Model model){
        Type type=typeService.findById(id);

        model.addAttribute("type",type);
        return "admin/types-edit";
    }



}
