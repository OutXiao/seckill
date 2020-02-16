package com.wenfan.seckill.service.impl;

import com.wenfan.seckill.entity.Promote;
import com.wenfan.seckill.mapper.PromoteMapper;
import com.wenfan.seckill.service.PromoteService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wenfan on 2020/1/28 15:24
 */
@Service
public class PromoteServiceImpl implements PromoteService {

    @Autowired
    private PromoteMapper promoteMapper;

    @Override
    public Promote getPromoteInfoByItemId(Integer id) {

        Promote promote = promoteMapper.getPromoteByItemId(id);
        if (promote == null){
            return null;
        }

        //判断当前时间是否秒杀活动即将开始或正在进行
        //  isAfterNow -- Now在（）的后面
        if (new DateTime(promote.getStartDate()).isAfterNow()){
            promote.setStatus(1);
        }else if(new DateTime(promote.getEndDate()).isBeforeNow()){
            promote.setStatus(3);
        }else promote.setStatus(2);

        return promote;
    }
}
