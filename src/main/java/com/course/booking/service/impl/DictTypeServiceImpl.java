package com.course.booking.service.impl;

import com.course.booking.controller.vo.SysDictData;
import com.course.booking.dao.DictTypeMapper;
import com.course.booking.service.DictTypeService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DictTypeServiceImpl implements DictTypeService {


    @Resource
    private DictTypeMapper dictDataMapper;

    @Override
    public List<SysDictData> selectDictDataByType(String dictType) {
        List<SysDictData> dictDatas = dictDataMapper.selectDictDataByType(dictType);
        if (!CollectionUtils.isEmpty(dictDatas)) {
            return dictDatas;
        }
        return null;
    }
}
