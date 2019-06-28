package com.mn.mycat.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@Accessors(chain = true)
public class Item implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;
	
	private int value;
	
	private Date indate;
	
}
