package com.course.booking.service.impl;

import com.course.booking.common.response.Result;
import com.course.booking.controller.dto.CourseDTO;
import com.course.booking.controller.vo.CourseVO;
import com.course.booking.controller.vo.UserInfoVO;
import com.course.booking.dao.CourseMapper;
import com.course.booking.dao.UserMapper;
import com.course.booking.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class CourseServiceImpl implements CourseService {


    Logger logger = LoggerFactory.getLogger(CourseServiceImpl.class);

    @Resource
    private CourseMapper courseMapper;

    @Resource
    private UserMapper userMapper;


    @Override
    public Result<List<CourseVO>> listCourse() {
        List<CourseVO> courseVOS = courseMapper.listCourse();
        return Result.success(courseVOS, courseVOS.size());
    }

    @Override
    public Result<Boolean> updateCourse(CourseDTO courseDTO) {
        UserInfoVO user = userMapper.getUser(courseDTO.getUserId());
        courseDTO.setUser(user.getUserName());
        courseMapper.updateCourse(courseDTO);
        return Result.success(true);
    }

    @Override
    public Result<Boolean> addCourse(CourseDTO courseDTO) {
        logger.info("addCourse{}", courseDTO);
        try {
            //1.根据userId先查询出user信息
            UserInfoVO user = userMapper.getUser(courseDTO.getUserId());
            courseDTO.setUser(user.getUserName());
            courseMapper.addCourse(courseDTO);
        } catch (Exception e) {
            logger.info("新增课程信息失败:{}", e.getMessage());
            return Result.failure();
        }
        return Result.success(true);
    }

    @Override
    public Result<Boolean> deleteCourse(List<String> courseIds) {
        for (String courseId : courseIds) {
            courseMapper.deleteCourse(courseId);
        }
        return Result.success();
    }
}
