package com.course.booking.dao;

import com.course.booking.controller.dto.GetUserInfoDTO;
import com.course.booking.controller.vo.RouterVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RouterMapper {



    @Select("select distinct m.menu_id, m.parent_id, m.menu_name, m.path, m.component, m.`query`, m.visible, m.status, ifnull(m.perms,'') as perms, m.is_frame, m.is_cache, m.menu_type, m.icon, m.order_num, m.create_time " +
            "from sys_menu m where m.menu_type in ('M', 'C') and m.status = 0 order by m.parent_id, m.order_num")
    List<RouterVO> getAllRouters();
}
