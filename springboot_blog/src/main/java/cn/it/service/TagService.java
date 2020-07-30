package cn.it.service;

import cn.it.domain.Tag;
import cn.it.domain.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {

    //分页查询
    Page<Tag> findAll(Pageable pageable);


    //保存
    Tag save(Tag tag);


    //name查询
    Tag getTypeByName(String name);

    List<Tag> listTag(String tagIds);

    List<Tag> listTag();


    void deleteTag(Long id);//删除




    List<Tag> listTagTop(Integer size);
}
