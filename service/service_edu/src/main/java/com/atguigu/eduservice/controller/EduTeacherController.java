package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.service.EduTeacherService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.list;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author test.java
 * @since 2020-09-03
 */
@Api(description = "讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {

    //访问地址： http://localhost:8001/eduservice/teacher/findAll
    // 把service注入
    @Autowired
    private EduTeacherService teacherService;

    //1 查询讲师表所有数据
    //rest风格
    @ApiOperation(value="所有讲师列表")
    @GetMapping("findAll")
    public R findAllTeacher(){
        //调用service的方法实现查询所有操作
        List<EduTeacher> list = teacherService.list(null);
        return R.ok().data("items",list);
    }

    //2 逻辑删除讲师的方法
    @DeleteMapping("{id}")
    public R removeTeacher(@ApiParam(name = "id", value="讲师ID", required=true) @PathVariable String id){
        boolean flag = teacherService.removeById(id);
        if(flag){
            return R.ok();
        } else {
            return R.error();
        }
    }

    //3 分页查询讲师的方法
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable long current,
                             @PathVariable long limit){

        // 创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);
        // 调用方法实现分页
        // 调用方法时候，底层封装，把分页所有数据封装到pageTeacher对象里面
        teacherService.page(pageTeacher, null);

        long total = pageTeacher.getTotal(); //总记录数
        List<EduTeacher> records = pageTeacher.getRecords(); //数据list集合

//        Map map = new HashMap<>();
//        map.put("total",total);
//        map.put("rows", records);
//        return R.ok().data(map);

        return R.ok().data("total",total).data("rows", records);
    }

    //4 条件查询带分页的方法
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current, @PathVariable long limit,
                                  @RequestBody(required = false) TeacherQuery teacherQuery){
        //创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);

        //构造条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        // 多条件组合查询
        // mybatis 学过动态sql

        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        // 判断条件值是否为空，如果不为空拼接条件
        if (!StringUtils.isEmpty(name)) {
            //构造条件
            wrapper.like("name", name); //模糊查询
        }
        if(!StringUtils.isEmpty(level)){
            wrapper.eq("level",level);  //等于
        }
        if(!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create", begin); // ge 大于等于
        }
        if(!StringUtils.isEmpty(end)){
            wrapper.le("gem_end", end); //le 小于等于
        }


        //调用方法实现条件查询分页
        teacherService.page(pageTeacher,wrapper);
        long total = pageTeacher.getTotal(); //总记录数
        List<EduTeacher> records = pageTeacher.getRecords(); //数据list集合
        return R.ok().data("total",total).data("rows", records);
    }
}

