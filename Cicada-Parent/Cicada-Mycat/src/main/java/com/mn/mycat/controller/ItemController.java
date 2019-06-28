package com.mn.mycat.controller;

import com.mn.mycat.model.Item;
import com.mn.mycat.service.ItemService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RestController
public class ItemController
{

    @Resource
    private ItemService itemService;


    @RequestMapping("/item/add/{id}/{value}")
    public String add(@PathVariable("id") Long id,@PathVariable("value") int value)
    {
        Item i = new Item();
        i.setValue(value).setIndate(new Date());
        itemService.add(i);
        return "添加成功";
    }


    @GetMapping("/item/find")
    public List<Item> findItem()
    {
        return itemService.find();
    }

}
