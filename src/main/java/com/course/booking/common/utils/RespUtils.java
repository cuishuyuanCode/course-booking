package com.course.booking.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RespUtils {


    private static final Logger logger = LoggerFactory.getLogger(RespUtils.class);

    public static void resp(HttpServletResponse response, String dataStr){
        try
        {
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(dataStr);
        }
        catch (IOException e)
        {
            logger.error("处理返回web端数据失败{}",e.getMessage());
        }
    }
}
