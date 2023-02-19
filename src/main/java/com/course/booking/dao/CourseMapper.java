package com.course.booking.dao;

import com.course.booking.controller.dto.CourseDTO;
import com.course.booking.controller.vo.CourseVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CourseMapper {


    @Select("select * from course")
    List<CourseVO> listCourse();


    @Update("update course set course_name = #{courseDTO.courseName}, status = #{courseDTO.status}, user = #{courseDTO.user}, user_id = #{courseDTO.userId} where course_id = #{courseDTO.courseId}")
    void updateCourse(@Param("courseDTO") CourseDTO courseDTO);

    @Insert("insert into course (course_name,status,user,user_id) values (#{courseDTO.courseName},#{courseDTO.status},#{courseDTO.user},#{courseDTO.userId})")
    void addCourse(@Param("courseDTO") CourseDTO courseDTO);

    @Delete("delete from course where course_id = #{courseId}")
    void deleteCourse(String courseId);
}
