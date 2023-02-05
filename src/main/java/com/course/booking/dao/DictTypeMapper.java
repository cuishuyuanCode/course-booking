package com.course.booking.dao;

import com.course.booking.controller.vo.SysDictData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DictTypeMapper {


    @Select("select dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark from sys_dict_data " +
            "where status = '0' and dict_type = #{dictType} order by dict_sort asc")
    List<SysDictData> selectDictDataByType(String dictType);
}
