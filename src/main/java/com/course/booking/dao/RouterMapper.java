package com.course.booking.dao;

import com.course.booking.controller.dto.GetUserInfoDTO;
import com.course.booking.controller.vo.RouterVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface RouterMapper {


    @Select("select * from")
    RouterVO getRouters(GetUserInfoDTO getUserInfoDTO);
}
