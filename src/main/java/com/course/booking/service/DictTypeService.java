package com.course.booking.service;

import com.course.booking.controller.vo.SysDictData;

import java.util.List;

public interface DictTypeService {


    List<SysDictData> selectDictDataByType(String dictType);
}
