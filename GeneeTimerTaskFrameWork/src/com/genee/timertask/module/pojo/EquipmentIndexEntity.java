package com.genee.timertask.module.pojo;

/**
 * 
 * @ClassName: EquipmentIndexEntity
 * @Description: 单台仪器一天的各项指标数据
 * @author da.zhou@geneegroup.com
 * @date 2014年8月18日 下午1:32:57
 *
 */
public class EquipmentIndexEntity {
	
	// 仪器ID 用UUID标识
	private String id;
	
	// 仪器ID
	private long equipmentId;
	
	// 使用者ID
	private String userId;
	
	// 统计日期
	private long time;
	
	// 操作时间
	private int optdate;
	
	// 故障时长
	private long faultDur;
	
	// 预约机时
	private long appointmentDur;
	
	// 使用机时
	private long usedDur;
	
	// 机主使用机时
	private long ownerUsedDur;
	
	// 开放机时
	private long openDur;
	
	// 有效机时
	private long validDur;
	
	// 委托测试机时
	private long testDur;
	
	// 科研机时
	private long scientificDur;
	
	// 教学机时
	private long teachDur;
	
	// 社会项目机时
	private long societyDur;
	
	// 使用次数
	private int usedTimes;
	
	// 测样数
	private int testSamCnt;
	
	// 使用测样数
	private int usedSamCnt;
	
	// 送样测样数
	private int giveSamCnt;
	
	// 机主测样数
	private int ownerSamCnt;
	
	// 学生测样数
	private int stuSamCnt;
	
	// 使用收费
	private double usedCharge;
	
	// 校内收费
	private double onCamCharge;
	
	// 校外收费
	private double offCamCharge;
	
	// 委托测试收费
	private double delegationCharge;
	
	// 创收金额
	private double earningsCharge;
	
	// 维修费
	private double repairCost;
	
	// 培训费
	private double trainCost;
	
	// 培训人数
	private int trainCnt;
	
	// 培训学生
	private int trainStu;
	
	// 培训教师
	private int trainTea;
	
	// 培训其他人
	private int trainOth;
	
	// 服务科研项目数
	private int servScientificCnt;
	
	// 服务教学项目数
	private int servTeachCnt;
	
	// 服务社会项目数
	private int servSocietyCnt;
	
	// 论文数
	private int essayCnt;
	
	// 三大检索
	private int threeSearch;
	
	// 核心刊物
	private int corePublication;
	
	// 获奖数
	private int awardsCnt;
	
	// 国家级获奖数
	private int nationalAwardsCnt;
	
	// 省部级获奖数
	private int provincialAwardsCnt;
	
	// 专利数
	private int patentCnt;
	
	// 教师专利数
	private int teaPatentCnt;
	
	// 学生专利数
	private int stuPatentCnt;
	
	/**
	 * 
	 * <p>Title: </p>
	 * <p>Description: </p>
	 * @param id	UUID主键
	 * @param equipmentId	仪器ID
	 * @param userId	使用者ID
	 * @param time	统计日期
	 */
	public EquipmentIndexEntity(String id, long equipmentId, String userId, long time) {
		this.id = id;
		this.equipmentId = equipmentId;
		this.userId = userId;
		this.time = time;
	}
	
	/**
	 * 
	 * <p>Title: </p>
	 * <p>Description: </p>
	 * @param id	UUID主键
	 * @param equipmentId	仪器ID
	 * @param time	统计日期
	 */
	public EquipmentIndexEntity(String id, long equipmentId, long time) {
		this.id = id;
		this.equipmentId = equipmentId;
		this.time = time;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(long equipmentId) {
		this.equipmentId = equipmentId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public int getOptdate() {
		return optdate;
	}

	public void setOptdate(int optdate) {
		this.optdate = optdate;
	}

	public long getFaultDur() {
		return faultDur;
	}

	public void setFaultDur(long faultDur) {
		this.faultDur = faultDur;
	}

	public long getAppointmentDur() {
		return appointmentDur;
	}

	public void setAppointmentDur(long appointmentDur) {
		this.appointmentDur = appointmentDur;
	}

	public long getUsedDur() {
		return usedDur;
	}

	public void setUsedDur(long usedDur) {
		this.usedDur = usedDur;
	}

	public long getOwnerUsedDur() {
		return ownerUsedDur;
	}

	public void setOwnerUsedDur(long ownerUsedDur) {
		this.ownerUsedDur = ownerUsedDur;
	}

	public long getOpenDur() {
		return openDur;
	}

	public void setOpenDur(long openDur) {
		this.openDur = openDur;
	}

	public long getValidDur() {
		return validDur;
	}

	public void setValidDur(long validDur) {
		this.validDur = validDur;
	}

	public long getTestDur() {
		return testDur;
	}

	public void setTestDur(long testDur) {
		this.testDur = testDur;
	}

	public long getScientificDur() {
		return scientificDur;
	}

	public void setScientificDur(long scientificDur) {
		this.scientificDur = scientificDur;
	}

	public long getTeachDur() {
		return teachDur;
	}

	public void setTeachDur(long teachDur) {
		this.teachDur = teachDur;
	}

	public long getSocietyDur() {
		return societyDur;
	}

	public void setSocietyDur(long societyDur) {
		this.societyDur = societyDur;
	}

	public int getUsedTimes() {
		return usedTimes;
	}

	public void setUsedTimes(int usedTimes) {
		this.usedTimes = usedTimes;
	}

	public int getTestSamCnt() {
		return testSamCnt;
	}

	public void setTestSamCnt(int testSamCnt) {
		this.testSamCnt = testSamCnt;
	}

	public int getUsedSamCnt() {
		return usedSamCnt;
	}

	public void setUsedSamCnt(int usedSamCnt) {
		this.usedSamCnt = usedSamCnt;
	}

	public int getGiveSamCnt() {
		return giveSamCnt;
	}

	public void setGiveSamCnt(int giveSamCnt) {
		this.giveSamCnt = giveSamCnt;
	}

	public int getOwnerSamCnt() {
		return ownerSamCnt;
	}

	public void setOwnerSamCnt(int ownerSamCnt) {
		this.ownerSamCnt = ownerSamCnt;
	}

	public int getStuSamCnt() {
		return stuSamCnt;
	}

	public void setStuSamCnt(int stuSamCnt) {
		this.stuSamCnt = stuSamCnt;
	}

	public double getUsedCharge() {
		return usedCharge;
	}

	public void setUsedCharge(double usedCharge) {
		this.usedCharge = usedCharge;
	}

	public double getOnCamCharge() {
		return onCamCharge;
	}

	public void setOnCamCharge(double onCamCharge) {
		this.onCamCharge = onCamCharge;
	}

	public double getOffCamCharge() {
		return offCamCharge;
	}

	public void setOffCamCharge(double offCamCharge) {
		this.offCamCharge = offCamCharge;
	}

	public double getDelegationCharge() {
		return delegationCharge;
	}

	public void setDelegationCharge(double delegationCharge) {
		this.delegationCharge = delegationCharge;
	}

	public double getEarningsCharge() {
		return earningsCharge;
	}

	public void setEarningsCharge(double earningsCharge) {
		this.earningsCharge = earningsCharge;
	}

	public double getRepairCost() {
		return repairCost;
	}

	public void setRepairCost(double repairCost) {
		this.repairCost = repairCost;
	}

	public int getTrainCnt() {
		return trainCnt;
	}

	public void setTrainCnt(int trainCnt) {
		this.trainCnt = trainCnt;
	}

	public int getTrainStu() {
		return trainStu;
	}

	public void setTrainStu(int trainStu) {
		this.trainStu = trainStu;
	}

	public int getTrainTea() {
		return trainTea;
	}

	public void setTrainTea(int trainTea) {
		this.trainTea = trainTea;
	}

	public int getTrainOth() {
		return trainOth;
	}

	public void setTrainOth(int trainOth) {
		this.trainOth = trainOth;
	}

	public int getServScientificCnt() {
		return servScientificCnt;
	}

	public void setServScientificCnt(int servScientificCnt) {
		this.servScientificCnt = servScientificCnt;
	}

	public int getServTeachCnt() {
		return servTeachCnt;
	}

	public void setServTeachCnt(int servTeachCnt) {
		this.servTeachCnt = servTeachCnt;
	}

	public int getServSocietyCnt() {
		return servSocietyCnt;
	}

	public void setServSocietyCnt(int servSocietyCnt) {
		this.servSocietyCnt = servSocietyCnt;
	}

	public int getEssayCnt() {
		return essayCnt;
	}

	public void setEssayCnt(int essayCnt) {
		this.essayCnt = essayCnt;
	}

	public int getThreeSearch() {
		return threeSearch;
	}

	public void setThreeSearch(int threeSearch) {
		this.threeSearch = threeSearch;
	}

	public int getCorePublication() {
		return corePublication;
	}

	public void setCorePublication(int corePublication) {
		this.corePublication = corePublication;
	}

	public int getAwardsCnt() {
		return awardsCnt;
	}

	public void setAwardsCnt(int awardsCnt) {
		this.awardsCnt = awardsCnt;
	}

	public int getNationalAwardsCnt() {
		return nationalAwardsCnt;
	}

	public void setNationalAwardsCnt(int nationalAwardsCnt) {
		this.nationalAwardsCnt = nationalAwardsCnt;
	}

	public int getProvincialAwardsCnt() {
		return provincialAwardsCnt;
	}

	public void setProvincialAwardsCnt(int provincialAwardsCnt) {
		this.provincialAwardsCnt = provincialAwardsCnt;
	}

	public int getPatentCnt() {
		return patentCnt;
	}

	public void setPatentCnt(int patentCnt) {
		this.patentCnt = patentCnt;
	}

	public int getTeaPatentCnt() {
		return teaPatentCnt;
	}

	public void setTeaPatentCnt(int teaPatentCnt) {
		this.teaPatentCnt = teaPatentCnt;
	}

	public int getStuPatentCnt() {
		return stuPatentCnt;
	}

	public void setStuPatentCnt(int stuPatentCnt) {
		this.stuPatentCnt = stuPatentCnt;
	}

	public double getTrainCost() {
		return trainCost;
	}

	public void setTrainCost(double trainCost) {
		this.trainCost = trainCost;
	}
}
