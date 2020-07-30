package cn.it.service;

import cn.it.domain.Blog;
import cn.it.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BlogService {

    List<Blog> findAll();//查询所有

    Page<Blog> findAll( Pageable pageable);//分页


    Blog saveBlog(Blog blog);//保存


    Blog getBlog(Long id);//ID查询

    Blog updateBlog(Long id, Blog blog);//修改

    List<Blog> findBlog(BlogQuery blogQuery);//条件查询



    void deleteBlog(Long id);//删除


    //分页查询
    Page<Blog> listBlog(Pageable pageable);



    Page<Blog> listBlog(Pageable pageable,BlogQuery blogQuery);

    //带关键字分页查询
   Page<Blog> findbyQuery(String s, Pageable pageable);


    List<Blog> listRecommendBlogTop(Integer size);


}
