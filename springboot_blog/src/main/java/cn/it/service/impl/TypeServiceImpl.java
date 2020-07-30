package cn.it.service.impl;

import cn.it.dao.TypeDao;
import cn.it.domain.Type;
import cn.it.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeDao typeDao;

    //保存
    @Override
    public Type save(Type type) {


     return    typeDao.save(type);
    }



    //查询所有
  @Override
    public Page<Type> findAll(Pageable pageable) {
        return typeDao.findAll(pageable);
    }


    //ID删除
    @Override
    public void deleteType(Long id) {


       typeDao.delete(id);
    }


    //name查询
    @Override
    public Type getTypeByName(String name) {
        return typeDao.findByName(name);
    }


    //id查询
    @Override
    public Type findById(Long id) {
        return typeDao.findOne(id);
    }

    @Override
    public List<Type> listType() {
        return typeDao.findAll();
    }



    @Override
    public List<Type> listTypeTop(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC,"blogs.size");
        //blogs.size 关联博客的数量

        Pageable pageable = new PageRequest(0,size,sort);
        return typeDao.findTop(pageable);
    }
}
