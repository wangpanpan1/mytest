package cn.it.service.impl;

import cn.it.dao.TagDao;
import cn.it.domain.Tag;
import cn.it.domain.Type;
import cn.it.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TagServiceImpl implements TagService{

    @Autowired
    private TagDao tagDao;

//分页
    @Override
    public Page<Tag> findAll(Pageable pageable) {
        return tagDao.findAll(pageable);
    }


//保存
    @Override
    public Tag save(Tag tag ) {
        return tagDao.save(tag);
    }



    //name查询
    @Override
    public Tag getTypeByName(String name) {
        return tagDao.findByName(name);
    }



  @Override
    public List<Tag> listTag(String tagIds) {
        return tagDao.findAll(convertToList(tagIds));

    }

    private List<Long> convertToList(String ids) {
        List<Long> list = new ArrayList<>();
        if (!"".equals(ids) && ids != null) {
            String[] idarray = ids.split(",");
            for (int i=0; i < idarray.length;i++) {
                list.add(new Long(idarray[i]));
            }
        }
        return list;
    }







    @Override
    public List<Tag> listTag() {
        return tagDao.findAll();
    }


    //删除
    @Override
    public void deleteTag(Long id) {
        tagDao.delete(id);
    }




    @Override
    public List<Tag> listTagTop(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC, "blogs.size");
        Pageable pageable = new PageRequest(0, size, sort);
        return tagDao.findTop(pageable);
    }


}
