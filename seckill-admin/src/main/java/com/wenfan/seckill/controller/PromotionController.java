package com.wenfan.seckill.controller;

import com.wenfan.seckill.entity.Item;
import com.wenfan.seckill.entity.PageBean;
import com.wenfan.seckill.entity.Promote;
import com.wenfan.seckill.exception.SystemException;
import com.wenfan.seckill.model.AdminPromotionModel;
import com.wenfan.seckill.rest.RestMsg;
import com.wenfan.seckill.service.PromoteService;
import com.wenfan.seckill.utils.DateUtils;
import com.wenfan.seckill.vo.EntityVO;
import com.wenfan.seckill.vo.ResponseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * Created by wenfan on 2020/2/16 19:52
 */
@RestController
@RequestMapping("/sys/admin/promotion")
public class PromotionController {

    @Autowired
    private PromoteService promoteService;

    @GetMapping("/allPromotionByPage")
    public EntityVO<Item> getAllActByPage(@RequestParam("pageIndex") Integer pageIndex,@RequestParam("pageSize") Integer pageSize) {
        int recordsTotal,recordsFiltered;
        PageBean<AdminPromotionModel> page = promoteService.listPromotionByPage(pageIndex, pageSize);
        recordsTotal = page.getTotalNum();
        recordsFiltered = recordsTotal;
        return new EntityVO(recordsTotal, recordsFiltered, page.getItems());
    }

    @PostMapping("/add")
    public ResponseInfo addPromotion(
            @RequestParam("itemId") Integer itemId,
            @RequestParam("promoteName") String  promoteName,
            @RequestParam("promoteStock") Integer promoteStock,
            @RequestParam("promotePrice") Double promotePrice,
            @RequestParam("startDate") String  startDate,
            @RequestParam("endDate") String endDate){
        Promote promote = new Promote();
        promote.setItemId(itemId);
        promote.setPromoteItemPrice(promotePrice);
        promote.setStartDate(DateUtils.stringConvertToDate(startDate));
        promote.setEndDate(DateUtils.stringConvertToDate(endDate));
        promote.setPromoteName(promoteName);
        int i = promoteService.addPromotion(promote,promoteStock);
        if (i == 0)
            return ResponseInfo.fail();
        else
            return ResponseInfo.success();
    }

    @PostMapping("/update")
    public ResponseInfo editPromotion(
            @RequestParam("promotionId") Integer promotionId,
            @RequestParam("itemId") Integer itemId,
            @RequestParam("promoteName") String  promoteName,
            @RequestParam("promoteStock") Integer promoteStock,
            @RequestParam("promotePrice") Double promotePrice,
            @RequestParam("startDate") String  startDate,
            @RequestParam("endDate") String endDate){
        Promote promote = new Promote();
        promote.setItemId(itemId);
        promote.setId(promotionId);
        promote.setPromoteItemPrice(promotePrice);
        promote.setStartDate(DateUtils.stringConvertToDate(startDate));
        promote.setEndDate(DateUtils.stringConvertToDate(endDate));
        promote.setPromoteName(promoteName);
        if (promoteStock <= 0)
            throw new SystemException("库存需要大于零");
        int i = promoteService.editPromotion(promote,promoteStock);
        if (i == 0)
            return ResponseInfo.fail();
        else
            return ResponseInfo.success();
    }

    @DeleteMapping("/del/{promotionId}")
    public ResponseInfo delPromotion(@PathVariable("promotionId") String promotionId){
        int i =promoteService.delPromotion(Integer.parseInt(promotionId));
        if (i == 0)
            return ResponseInfo.success(RestMsg.DEL_FAILURE);
        return ResponseInfo.success(RestMsg.DEL_SUCCESS);
    }

    @GetMapping("/getAdminPromitionById/{promotionId}")
    public ResponseInfo getAdminPromitionById(@PathVariable("promotionId") Integer promotionId){
        AdminPromotionModel adminPromotionModel = promoteService.getAdminPromotionModelByPromotionId(promotionId);
        return ResponseInfo.success(adminPromotionModel);
    }


}
