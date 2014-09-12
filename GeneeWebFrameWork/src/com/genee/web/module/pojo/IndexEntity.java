/**
 * 
 */
package com.genee.web.module.pojo;

import java.io.Serializable;

/**
 * 指标基本信息表对应的entity类
 * 
 * @author yanan.che 2014年8月13日
 */
public class IndexEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	// 指标ID PK
	private int sId;

	// 指标类型ID FK
	private int tId;

	// 指标名称
	private String sName;

	// 指标代码
	private String sCode;
	
	//位置
	private String sLocation;
	
	//描述
	private String sDescription;
	
	//宽度
	private String sWidth;

	//是否统计
	private String sNotCount;

	// 指标实现类
	private String sClassName;

	// 指标实现方法
	private String sMethodName;

	// 指标状态 设置默认指标列
	private String sFlag;

	// 指标排序顺序
	private int sSort;

	// 指标开关
	private String sOnOff;
	
	public int getsId() {
		return sId;
	}

	public void setsId(int sId) {
		this.sId = sId;
	}

	public int gettId() {
		return tId;
	}

	public void settId(int tId) {
		this.tId = tId;
	}

	public String getsName() {
		return sName;
	}

	public void setsName(String sName) {
		this.sName = sName;
	}

	public String getsCode() {
		return sCode;
	}

	public void setsCode(String sCode) {
		this.sCode = sCode;
	}

	public String getsClassName() {
		return sClassName;
	}

	public void setsClassName(String sClassName) {
		this.sClassName = sClassName;
	}

	public String getsMethodName() {
		return sMethodName;
	}

	public void setsMethodName(String sMethodName) {
		this.sMethodName = sMethodName;
	}

	public String getsFlag() {
		return sFlag;
	}

	public void setsFlag(String sFlag) {
		this.sFlag = sFlag;
	}

	public int getsSort() {
		return sSort;
	}

	public void setsSort(int sSort) {
		this.sSort = sSort;
	}

	public String getsOnOff() {
		return sOnOff;
	}

	public void setsOnOff(String sOnOff) {
		this.sOnOff = sOnOff;
	}

	public String getsLocation() {
		return sLocation;
	}

	public void setsLocation(String sLocation) {
		this.sLocation = sLocation;
	}

	public String getsDescription() {
		return sDescription;
	}

	public void setsDescription(String sDescription) {
		this.sDescription = sDescription;
	}

	public String getsWidth() {
		return sWidth;
	}

	public void setsWidth(String sWidth) {
		this.sWidth = sWidth;
	}

	public String getsNotCount() {
		return sNotCount;
	}

	public void setsNotCount(String sNotCount) {
		this.sNotCount = sNotCount;
	}
}
