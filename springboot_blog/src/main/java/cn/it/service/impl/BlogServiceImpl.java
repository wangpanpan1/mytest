package cn.it.service.impl;

import cn.it.NotFoundException;
import cn.it.dao.BlogDao;
import cn.it.domain.Blog;
import cn.it.domain.Type;
import cn.it.service.BlogService;
import cn.it.utils.MyBeanUtils;
import cn.it.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogDao blogDao;

    //查询
  @Override
    public List<Blog> findAll() {
        return blogDao.findAll();
    }

//分页
    @Override
    public Page<Blog> findAll(Pageable pageable) {
        return blogDao.findAll(pageable);
    }

    //保存
    public Blog saveBlog(Blog blog) {

   return blogDao.save(blog);
    }


//id查询
    @Override
    public Blog getBlog(Long id) {
        return blogDao.findOne(id);
    }



    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog b = blogDao.findOne(id);
        if (b == null) {
            throw new NotFoundException("该博客不存在");
        }

        //b中的存在的属性，a中一定要有，但是a中可以有多余的属性；
        BeanUtils.copyProperties(blog,b, MyBeanUtils.getNullPropertyNames(blog));
        b.setUpdateTime(new Date());
        return blogDao.save(b);
    }


    //条件查询
    @Override
    public List<Blog> findBlog(BlogQuery blog) {
        return blogDao.findAll(new Specification<Blog>(){
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                //cb:构建查询，添加查询方式   like：模糊匹配
                //root：从实体blog对象中按照title属性进行查询
                List<Predicate> predicates=new ArrayList<Predicate>();
                //模糊条件查询
                if(!"".equals(blog.getTitle()) && blog.getTitle()!=null){
                    predicates.add(cb.like(root.<String>get("title"), "%"+blog.getTitle()+"%"));
                }
                //分类查询
                if(blog.getTypeId()!=null){
                    predicates.add(cb.equal(root.<String>get("type").get("id"),blog.getTypeId()));
                }
             //推荐查询
               if(blog.isRecommend()){
                   predicates.add(cb.equal(root.<Boolean>get("recommend"), blog.isRecommend()));
               }
                cq.where(predicates.toArray( new Predicate[predicates.size()]));
                return null;
            }
        });
    }



    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        return blogDao.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (!"".equals(blog.getTitle()) && blog.getTitle() != null) {
                    predicates.add(cb.like(root.<String>get("title"), "%"+blog.getTitle()+"%"));
                }
                if (blog.getTypeId() != null) {
                    predicates.add(cb.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
                }
                if (blog.isRecommend()) {
                    predicates.add(cb.equal(root.<Boolean>get("recommend"), blog.isRecommend()));
                }
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },pageable);

    }












    //删除

    @Override
    public void deleteBlog(Long id) {
        blogDao.delete(id);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogDao.findAll(pageable);
    }






    //前端搜索

    @Override
    public Page<Blog> findbyQuery(String query, Pageable pageable) {
        return blogDao.findbyQuery(query,pageable);
    }






    @Override
    public List<Blog> listRecommendBlogTop(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC,"updateTime");
        Pageable pageable = new PageRequest(0, size, sort);
        return blogDao.findTop(pageable);
    }




}
