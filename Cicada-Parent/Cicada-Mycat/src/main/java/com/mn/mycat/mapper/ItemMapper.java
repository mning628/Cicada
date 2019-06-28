package com.mn.mycat.mapper;

import com.mn.mycat.model.Item;

import java.util.List;


public interface ItemMapper
{

    void add(Item i);

    List<Item> find();

}
