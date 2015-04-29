package com.decade.framework.async;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 抽象模型集
 * @author: Decade
 * @date: 2013-9-16
 * 
 */
public abstract class DZResponseList<T> extends DZResponse{
	private List<T> _list = new ArrayList<T>();
	
	public void add(T object){
		_list.add(object);
	}
	
	public List<T> getList(){
		return _list;
	}
	
	public void addAll(List<T> list){
		_list.addAll(list);
	}
}
