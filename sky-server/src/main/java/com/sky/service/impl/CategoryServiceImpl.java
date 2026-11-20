package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.annotation.AutoFill;
import com.sky.context.BaseContext;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Category;
import com.sky.entity.Employee;
import com.sky.enumeration.OperationType;
import com.sky.mapper.CategoryMapper;
import com.sky.result.PageResult;
import com.sky.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    //新增分类
    @AutoFill(value = OperationType.INSERT)
    @Override
    public void save(CategoryDTO categoryDTO) {
        Category category = Category.builder()
                .type(categoryDTO.getType())
                .name(categoryDTO.getName())
                .sort(categoryDTO.getSort())
                .build();
        category.setStatus(1);
//        category.setCreateTime(LocalDateTime.now());
//        category.setUpdateTime(LocalDateTime.now());
        categoryMapper.insert(category);

        //设置当前记录创建人id和修改人id
//        category.setCreateUser(BaseContext.getCurrentId());//目前写个假数据，后期修改
//        category.setUpdateUser(BaseContext.getCurrentId());
    }

    //分类分页查询
    @Override
    public PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO) {
        // select * from employee limit 0,10
        //开始分页查询
        PageHelper.startPage(categoryPageQueryDTO.getPage(), categoryPageQueryDTO.getPageSize());

        Page<Category> page = categoryMapper.pageQuery(categoryPageQueryDTO);

        long total = page.getTotal();
        List<Category> records = page.getResult();

        return new PageResult(total, records);
    }

    //根据id删除分类
    @Override
    public void deleteById(Long id) {
        categoryMapper.deleteById(id);
    }

    //根据id修改分类信息
    @AutoFill(value = OperationType.UPDATE)
    @Override
    public void update(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);

//        category.setUpdateTime(LocalDateTime.now());
//        category.setUpdateUser(BaseContext.getCurrentId());

        categoryMapper.update(category);

    }

    //根据id修改分类的启用状态或禁用状态
    @Override
    public void startOrStop(Integer status, Long id) {
        Category category = Category.builder()
                .status(status)
                .id(id)
                .build();
        categoryMapper.update(category);
    }

//    //根据类型查询分类
//    @Override
//    public List<Category> list(Integer type) {
//        return List.of();
//    }

}
