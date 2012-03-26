package com.greenpineyu.fel.context;

/**
 * 变量
 * @author yuqingsong
 * 
 * @param <T> 变量的类型
 */
public class Var {
	
	public Var(String name,Object value,Class<?> type){
		this.name = name;
		this.value = value;
		this.type = type;
		if(type == null){
			// 如果没有指定type,将type设置成value.getClass。
			this.type = value!=null?value.getClass():FelContext.NULL.getClass();
		}
	}
	public Var(String name,Object value){
		this(name,value,null);
	}

	/**
	 * 变量名称
	 */
	private String name;

	/**
	 * 变量值
	 */
	private Object value;

	/**
	 * 变量类型
	 */
	private Class<?> type;
	
	
	public Class<?> getType() {
		return type;
	}

	public void setType(Class<?> type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
	
	public static void main(String[] args) {
	}
	

}