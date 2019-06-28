package com.mn.mycat.service;

import com.mn.mycat.model.Item;

import java.util.List;


public interface ItemService
{

    void add(Item i);

    List<Item> find();

}
