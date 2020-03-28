package com.wenfan.seckill.service;

import com.wenfan.seckill.entity.PageBean;
import com.wenfan.seckill.entity.Promote;
import com.wenfan.seckill.model.AdminPromotionModel;

/**
 * Created by wenfan on 2020/1/28 15:22
 */
public interface PromoteService {

    AdminPromotionModel getAdminPromotionModelByPromotionId(Integer promotionId);

    Promote getPromoteInfoByItemId(Integer id);

    PageBean<AdminPromotionModel> listPromotionByPage(Integer pageIndex, Integer pageSize);

    int addPromotion(Promote promote,Integer promoteStock);

    int editPromotion(Promote promote,Integer promoteStock);

    int delPromotion(Integer promotionId);

}
