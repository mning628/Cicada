package com.mn.mycat.service.impl;

import com.mn.mycat.mapper.ItemMapper;
import com.mn.mycat.model.Item;
import com.mn.mycat.service.ItemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service("itemService")
public class ItemServiceImpl implements ItemService
{

    @Resource
    private ItemMapper itemMapper;

    @Override
    public void add(Item i)
    {
        itemMapper.add(i);
    }

    @Override
    public List<Item> find()
    {
        return itemMapper.find();
    }


}
