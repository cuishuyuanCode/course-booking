package com.course.booking.service.impl;

import com.course.booking.controller.dto.GetUserInfoDTO;
import com.course.booking.controller.vo.TableDataInfo;
import com.course.booking.controller.vo.UserInfoVO;
import com.course.booking.dao.UserMapper;
import com.course.booking.service.UserService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Resource
    private UserMapper userMapper;

    @Override
    public UserInfoVO getUserInfo() {
        log.info("开始查询用户信息");
        return null;
//        return userMapper.getUserInfo();
    }


    @Override
    public TableDataInfo listUser() {
        List<UserInfoVO> userInfoVOS = userMapper.listUser();
        return getDataTable(userInfoVOS);
    }

    /**
     * 响应请求分页数据
     */
    protected TableDataInfo getDataTable(List<?> list)
    {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(200);
        rspData.setMsg("查询成功");
        rspData.setRows(list);
        rspData.setTotal(new PageInfo(list).getTotal());
        return rspData;
    }
}
