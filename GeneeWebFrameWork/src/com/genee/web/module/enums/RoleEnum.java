package com.genee.web.module.enums;

public enum RoleEnum {
	
	SADMIN("1", "校级管理员"), LAB("2", "课题组PI"), INCHARGE("3", "仪器负责人"), LAB_INCHARGE("2,3", "课题组PI，仪器负责人");  
    // 成员变量  
    private String name;  
    private String id;  
    // 构造方法  
    private RoleEnum(String id, String name ) {  
        this.name = name;  
        this.id = id;  
    }  
    // 普通方法  
    public static String getName(String id) {  
        for (RoleEnum c : RoleEnum.values()) {  
            if (c.getId().equals(id)) {  
                return c.name;  
            }  
        }  
        return null;  
    }  
    // get set 方法  
    public String getName() {  
        return name;  
    }  
    public void setName(String name) {  
        this.name = name;  
    }
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}  

}
