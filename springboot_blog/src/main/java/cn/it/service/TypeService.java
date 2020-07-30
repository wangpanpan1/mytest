package cn.it.service;

import cn.it.domain.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TypeService {

    //保存
Type save(Type type);



//分页查询
   Page<Type> findAll(Pageable pageable);


    //id 删除
   void deleteType(Long id);

    //name查询
    Type getTypeByName(String name);

    //id 查询
    Type findById( Long id);




    List<Type> listType();




    List<Type> listTypeTop(Integer size);//取出前6个







}
