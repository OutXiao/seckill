/*
* SequenceInfoMapper.java
* http://www.wenfan.club
* Copyright © 2020 wenfan All Rights Reserved
* 作者：wenfan
* QQ：571696215
* E-Mail：guwenfan@qq.com
* 2020-01-29 13:53 Created
*/
package com.wenfan.seckill.mapper;

import com.wenfan.seckill.entity.SequenceInfo;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

@Component
@org.apache.ibatis.annotations.Mapper
public interface SequenceInfoMapper extends Mapper<SequenceInfo> {

    SequenceInfo geSequenceInfoByName(String name);

}