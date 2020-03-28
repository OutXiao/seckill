package com.wenfan.seckill.controller;

import com.wenfan.seckill.dto.SysUserDto;
import com.wenfan.seckill.entity.Item;
import com.wenfan.seckill.entity.ItemStock;
import com.wenfan.seckill.entity.PageBean;
import com.wenfan.seckill.entity.SysRole;
import com.wenfan.seckill.exception.SystemException;
import com.wenfan.seckill.mapper.ItemMapper;
import com.wenfan.seckill.mapper.ItemStockMapper;
import com.wenfan.seckill.model.AdminItemModel;
import com.wenfan.seckill.rest.RestMsg;
import com.wenfan.seckill.service.ItemService;
import com.wenfan.seckill.utils.DateUtils;
import com.wenfan.seckill.utils.StringUtils;
import com.wenfan.seckill.vo.EntityVO;
import com.wenfan.seckill.vo.ItemVO;
import com.wenfan.seckill.vo.ResponseInfo;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wenfan on 2020/1/24 15:04
 */
@RestController
@RequestMapping("/sys/admin/item")
public class AdminItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemStockMapper itemStockMapper;

    @Autowired
    private ItemMapper itemMapper;

    @GetMapping("/allItemByPage")
    public EntityVO<Item> getAllItemsByPage(HttpServletRequest request) {
        String strPageIndex = request.getParameter("pageIndex");
        String strPageSize = request.getParameter("pageSize");
        int recordsTotal,recordsFiltered;
        int pageIndex = Integer.parseInt(strPageIndex);
        int pageSize = Integer.parseInt(strPageSize);
        PageBean<Item> page = itemService.listItemByPage(pageIndex, pageSize);
        recordsTotal = page.getTotalNum();
        recordsFiltered = recordsTotal;
        return new EntityVO(recordsTotal, recordsFiltered, page.getItems());
    }


    @GetMapping("/getSingleItem/{id}")
    public ResponseInfo getItemById(@PathVariable("id") String id) {
        if (StringUtils.isAnEmpty(id))
            throw new IllegalArgumentException();
        ItemVO rs = itemService.getItemById(Integer.parseInt(id));
        return ResponseInfo.success(rs);
    }

    @GetMapping("/getAdminItemById/{id}")
    public ResponseInfo getAdminItemById(@PathVariable("id") String id) {
        if (StringUtils.isAnEmpty(id))
            throw new IllegalArgumentException();
        Integer itemId = Integer.parseInt(id);
        Item rs = itemService.getAdminItemById(itemId);
        ItemStock stock = itemStockMapper.getItemStockByItemId(itemId);
        AdminItemModel adminItemModel = new AdminItemModel();
        BeanUtils.copyProperties(rs,adminItemModel);
        adminItemModel.setStock(stock.getStock());
        return ResponseInfo.success(adminItemModel);
    }


    @DeleteMapping("deleteItem/{id}")
    public ResponseInfo deleteItem(@PathVariable("id") String id) {
        itemService.deleteItem(Integer.parseInt(id));
        return new ResponseInfo("200", "删除成功");
    }


    @PostMapping("/updateItem/{id}")
    public ResponseInfo updateItem(
            @PathVariable("id") String id,
            @PathParam("title") String title,
            @PathParam("price") Double price,
            @PathParam("desc") String desc,
            @PathParam("imgUrl") String imgUrl,
            @PathParam("stock") Integer stock,
            @PathParam("status") String status) {
        AdminItemModel adminItemModel = new AdminItemModel();
        adminItemModel.setStock(stock);
        adminItemModel.setId(Integer.parseInt(id));
        adminItemModel.setTitle(title);
        adminItemModel.setPrice(price);
        adminItemModel.setDescription(desc);
        adminItemModel.setImgUrl(imgUrl);
        adminItemModel.setStatus(status);
        int i = itemService.editItem(adminItemModel);
        if (i == 0)
            return new ResponseInfo(RestMsg.EDIT_FAILURE);
        return new ResponseInfo(RestMsg.EDIT_SUCCESS);
    }

    @PostMapping("/addItem")
    public ResponseInfo addItem(HttpServletRequest request) {
        int result = 0;
        String title = request.getParameter("title");
        String price = request.getParameter("price");
        String description = request.getParameter("description");
        String imgUrl = request.getParameter("imgUrl");
        String status = request.getParameter("status");
        String stock = request.getParameter("stock");
        if(!StringUtils.isAllEmptys(title,price,description,imgUrl,status,stock)){
            Item item = new Item();
            item.setTitle(title);
            item.setPrice(Double.parseDouble(price));
            item.setDescription(description);
            item.setImgUrl(imgUrl);
            item.setStatus(status);
            result = itemService.createItem(item,Integer.parseInt(stock));
        }
        if (result == 0)
            return ResponseInfo.fail();
        return ResponseInfo.success();
    }

    @GetMapping("/allIdAndName")
    public ResponseInfo getAllIdAndNamOfItem(){
        List<Item> items = itemMapper.selectAll();
        return ResponseInfo.success(items);
    }


}
