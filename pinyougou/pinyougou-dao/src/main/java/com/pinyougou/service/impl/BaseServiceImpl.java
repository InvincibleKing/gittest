package com.pinyougou.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougou.service.BaseService;
import com.pinyougou.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;

import java.io.Serializable;
import java.util.List;

public class BaseServiceImpl<T> implements BaseService<T>{

    //spring 4.x 版本之后引入的泛型依赖注入
    @Autowired
    private Mapper<T> mapper;

    /**
     * 根据主键查询
     * @param id
     * @return
     */
    @Override
    public T findOne(Serializable id) {
        return mapper.selectByPrimaryKey(id);
    }

    /**
     * 查询全部
     * @return
     */
    @Override
    public List<T> findAll() {
        return mapper.selectAll();
    }

    /**
     * 根据条件查询
     * @param t
     * @return
     */
    @Override
    public List<T> findByWhere(T t) {
        return mapper.select(t);
    }

    /**
     * 分页查询
     * @param page
     * @param rows
     * @return
     */
    @Override
    public PageResult findPage(Integer page, Integer rows) {

        //创建分页
        PageHelper.startPage(page,rows);

        //查询
        List<T> list = mapper.selectAll();

        //创建分页对象
        PageInfo<T> pageInfo = new PageInfo<>(list);

        return new PageResult(pageInfo.getTotal(),pageInfo.getList());
    }

    /**
     * 根据条件分页查询
     * @param page
     * @param rows
     * @param t
     * @return
     */
    @Override
    public PageResult findPageByT(Integer page, Integer rows, T t) {

        //创建分页
        PageHelper.startPage(page,rows);

        //根据条件查询
        List<T> list = mapper.select(t);

        //创建分页对象
        PageInfo<T> pageInfo = new PageInfo<>(list);

        return new PageResult(pageInfo.getTotal(),pageInfo.getList());
    }

    @Override
    public void add(T t) {
        mapper.insertSelective(t);
    }

    @Override
    public void update(T t) {
        mapper.updateByPrimaryKeySelective(t);
    }

    @Override
    public void deleteByIds(Serializable[] ids) {

        if (ids != null&&ids.length>0){
            for (Serializable id : ids){
                mapper.deleteByPrimaryKey(id);
            }
        }
    }
}
