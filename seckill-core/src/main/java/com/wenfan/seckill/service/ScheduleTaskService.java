package com.wenfan.seckill.service;

import com.wenfan.seckill.utils.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author:wenfan
 * @description:
 * @data: 2019/3/10 12:18
 */

//@Service
public class ScheduleTaskService {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private RedisUtils redisUtils;

    //@Autowired
    // private VideoMapper videoMapper;

    /*@Scheduled(cron = "0/5 * * * * ?")
    public void generateGoalSetting() {

        *//*List<Video> videos = videoMapper.selectAll();
        for (Video video: videos){
            *//**//*Integer viewCount = video.getViewCount();
            viewCount += Integer.parseInt(redisUtils.get("security:viewCount:id" + video.getId()));
            video.setViewCount(viewCount);
            videoMapper.insertSelective(video);*//**//*
            log.info(video.getId()+"viewCount更新成功");
        }*//*
        log.info("viewCount更新成功");
    }*/

}
