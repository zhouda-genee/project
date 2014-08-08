package com.genee.web.framework.core.base;

public class JdbcTemplateParam {

	private String sql;

	private Object[] args;

	private int[] argType;
	
	public JdbcTemplateParam(String sql){
		this.sql = sql;
	}
	
	public JdbcTemplateParam(String sql, Object[] args, int[] argTypes){
		this.sql = sql;
		this.args = args;
		this.argType = argTypes;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public Object[] getArgs() {
		return args;
	}

	public void setArgs(Object[] args) {
		this.args = args;
	}

	public int[] getArgType() {
		return argType;
	}

	public void setArgType(int[] argType) {
		this.argType = argType;
	}

}
