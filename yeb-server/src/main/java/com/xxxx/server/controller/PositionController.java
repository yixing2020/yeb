package com.xxxx.server.controller;

import com.xxxx.server.pojo.Position;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.service.IPositionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 职位管理处理器
 */
@RestController
@RequestMapping("/system/cfg/pos")
public class PositionController {

    @Autowired
    private IPositionService positionService;
    //查询所有的职位
    @ApiOperation(value = "获取所有的职位")
    @GetMapping("/")
    public List<Position> getAllPosition(){
        return positionService.list();
    }
    //添加职位

    @ApiOperation(value = "添加职位")
    @PostMapping("/")
    public RespBean addPostition(@RequestBody Position position){
        position.setCreateDate(new Date());
        if(positionService.save(position)){
            //如果添加成功,返回公共返回对象
            return RespBean.success("添加成功!");
        }
        return RespBean.error("添加失败!");
    }
    //修改职位
    @ApiOperation(value = "修改职位信息")
    @PutMapping("/")
    public RespBean updatePostition(@RequestBody Position position){
        if(positionService.updateById(position)){
            return RespBean.success("修改成功");
        }
        return RespBean.error("修改失败");
    }
    //删除职位
    @ApiOperation(value = "删除职位信息")
    @DeleteMapping("/{id}")
    public RespBean deletePostition(@PathVariable Integer id){
        if(positionService.removeById(id)){
            return  RespBean.success("删除成功!");
        }
        return RespBean.error("删除失败");
    }
    //批量删除职位

    @ApiOperation(value = "批量删除")
    @DeleteMapping("/")
    public RespBean deletePositionById(Integer[] ids){
        if(positionService.removeByIds(Arrays.asList(ids))){
            return RespBean.success("删除成功!");
        }
        return RespBean.error("删除失败");

    }


}
