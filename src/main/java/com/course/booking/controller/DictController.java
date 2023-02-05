package com.course.booking.controller;


import com.course.booking.common.response.Result;
import com.course.booking.controller.vo.SysDictData;
import com.course.booking.service.DictTypeService;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/system/dict/data")
public class DictController {


    @Resource
    private DictTypeService dictTypeService;

    /**
     * 根据字典类型查询字典数据信息
     */
    @GetMapping(value = "/type/{dictType}")
    public Result<List<SysDictData>> dictType(@PathVariable String dictType) {
        return Result.success(dictTypeService.selectDictDataByType(dictType));
    }
}
