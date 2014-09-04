package com.genee.service.module.pojo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @ClassName: EquipmentIndexEntity
 * @Description: 单台仪器一天的各项指标数据
 * @author da.zhou@geneegroup.com
 * @date 2014年8月18日 下午1:32:57
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class EquipmentIndexEntity implements Serializable {
	
	private static final long serialVersionUID = 4995122871109032905L;

	// 仪器ID
	@XmlAttribute(name="eq_id")
	private long eq_id;
	
	// 仪器名称
	private String eq_name;
	
	// 仪器数量
	private int eq_count;
	
	// 仪器价格
	@XmlAttribute(name="eq_price")
	private String eq_price;
	
	// 仪器负责人
	private String principal;
	
	// 仪器联系人
	private String linkman;
	
	// 入网时长
	private double innet_dur;
	
	// 故障时长
	private double fault_dur;
	
	// 预约机时
	private double appointment_dur;
	
	// 使用机时
	private double used_dur;
	
	// 机主使用机时
	private double owner_used_dur;
	
	// 开放机时
	private double open_dur;
	
	// 有效机时
	private double valid_dur;
	
	// 委托测试机时
	private double test_dur;
	
	// 科研机时
	private double scientific_dur;
	
	// 教学机时
	private double teach_dur;
	
	// 社会项目机时
	private double society_dur;
	
	// 使用次数
	private int used_times;
	
	// 测样数
	private int test_sam_cnt;
	
	// 使用测样数
	private int used_sam_cnt;
	
	// 送样测样数
	private int give_sam_cnt;
	
	// 机主测样数
	private int owner_sam_cnt;
	
	// 学生测样数
	private int stu_sam_cnt;
	
	// 使用收费
	private double used_charge;
	
	// 校内收费
	private double on_cam_charge;
	
	// 校外收费
	private double off_cam_charge;
	
	// 委托测试收费
	private double delegation_charge;
	
	// 创收金额
	private double earnings_charge;
	
	// 维修费
	private double repair_cost;
	
	// 培训费
	private double train_cost;
	
	// 培训人数
	private int train_cnt;
	
	// 培训学生
	private int train_stu;
	
	// 培训教师
	private int train_tea;
	
	// 培训其他人
	private int train_oth;
	
	// 服务科研项目数
	private int serv_scientific_cnt;
	
	// 服务教学项目数
	private int serv_teach_cnt;
	
	// 服务社会项目数
	private int serv_society_cnt;
	
	// 论文数
	private int essay_cnt;
	
	// 三大检索
	private int three_search;
	
	// 核心刊物
	private int core_publication;
	
	// 获奖数
	private int awards_cnt;
	
	// 国家级获奖数
	private int national_awards_cnt;
	
	// 省部级获奖数
	private int provincial_awards_cnt;
	
	// 专利数
	private int patent_cnt;
	
	// 教师专利数
	private int tea_patent_cnt;
	
	// 学生专利数
	private int stu_patent_cnt;

	public long getEq_id() {
		return eq_id;
	}

	public void setEq_id(long eq_id) {
		this.eq_id = eq_id;
	}

	public String getEq_name() {
		return eq_name;
	}

	public void setEq_name(String eq_name) {
		this.eq_name = eq_name;
	}

	public String getEq_price() {
		return eq_price;
	}

	public void setEq_price(String eq_price) {
		this.eq_price = eq_price;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public double getInnet_dur() {
		return innet_dur;
	}

	public void setInnet_dur(double innet_dur) {
		this.innet_dur = innet_dur;
	}

	public double getFault_dur() {
		return fault_dur;
	}

	public void setFault_dur(double fault_dur) {
		this.fault_dur = fault_dur;
	}

	public double getAppointment_dur() {
		return appointment_dur;
	}

	public void setAppointment_dur(double appointment_dur) {
		this.appointment_dur = appointment_dur;
	}

	public double getUsed_dur() {
		return used_dur;
	}

	public void setUsed_dur(double used_dur) {
		this.used_dur = used_dur;
	}

	public double getOwner_used_dur() {
		return owner_used_dur;
	}

	public void setOwner_used_dur(double owner_used_dur) {
		this.owner_used_dur = owner_used_dur;
	}

	public double getOpen_dur() {
		return open_dur;
	}

	public void setOpen_dur(double open_dur) {
		this.open_dur = open_dur;
	}

	public double getValid_dur() {
		return valid_dur;
	}

	public void setValid_dur(double valid_dur) {
		this.valid_dur = valid_dur;
	}

	public double getTest_dur() {
		return test_dur;
	}

	public void setTest_dur(double test_dur) {
		this.test_dur = test_dur;
	}

	public double getScientific_dur() {
		return scientific_dur;
	}

	public void setScientific_dur(double scientific_dur) {
		this.scientific_dur = scientific_dur;
	}

	public double getTeach_dur() {
		return teach_dur;
	}

	public void setTeach_dur(double teach_dur) {
		this.teach_dur = teach_dur;
	}

	public double getSociety_dur() {
		return society_dur;
	}

	public void setSociety_dur(double society_dur) {
		this.society_dur = society_dur;
	}

	public int getUsed_times() {
		return used_times;
	}

	public void setUsed_times(int used_times) {
		this.used_times = used_times;
	}

	public int getTest_sam_cnt() {
		return test_sam_cnt;
	}

	public void setTest_sam_cnt(int test_sam_cnt) {
		this.test_sam_cnt = test_sam_cnt;
	}

	public int getUsed_sam_cnt() {
		return used_sam_cnt;
	}

	public void setUsed_sam_cnt(int used_sam_cnt) {
		this.used_sam_cnt = used_sam_cnt;
	}

	public int getGive_sam_cnt() {
		return give_sam_cnt;
	}

	public void setGive_sam_cnt(int give_sam_cnt) {
		this.give_sam_cnt = give_sam_cnt;
	}

	public int getOwner_sam_cnt() {
		return owner_sam_cnt;
	}

	public void setOwner_sam_cnt(int owner_sam_cnt) {
		this.owner_sam_cnt = owner_sam_cnt;
	}

	public int getStu_sam_cnt() {
		return stu_sam_cnt;
	}

	public void setStu_sam_cnt(int stu_sam_cnt) {
		this.stu_sam_cnt = stu_sam_cnt;
	}

	public double getUsed_charge() {
		return used_charge;
	}

	public void setUsed_charge(double used_charge) {
		this.used_charge = used_charge;
	}

	public double getOn_cam_charge() {
		return on_cam_charge;
	}

	public void setOn_cam_charge(double on_cam_charge) {
		this.on_cam_charge = on_cam_charge;
	}

	public double getOff_cam_charge() {
		return off_cam_charge;
	}

	public void setOff_cam_charge(double off_cam_charge) {
		this.off_cam_charge = off_cam_charge;
	}

	public double getDelegation_charge() {
		return delegation_charge;
	}

	public void setDelegation_charge(double delegation_charge) {
		this.delegation_charge = delegation_charge;
	}

	public double getEarnings_charge() {
		return earnings_charge;
	}

	public void setEarnings_charge(double earnings_charge) {
		this.earnings_charge = earnings_charge;
	}

	public double getRepair_cost() {
		return repair_cost;
	}

	public void setRepair_cost(double repair_cost) {
		this.repair_cost = repair_cost;
	}

	public double getTrain_cost() {
		return train_cost;
	}

	public void setTrain_cost(double train_cost) {
		this.train_cost = train_cost;
	}

	public int getTrain_cnt() {
		return train_cnt;
	}

	public void setTrain_cnt(int train_cnt) {
		this.train_cnt = train_cnt;
	}

	public int getTrain_stu() {
		return train_stu;
	}

	public void setTrain_stu(int train_stu) {
		this.train_stu = train_stu;
	}

	public int getTrain_tea() {
		return train_tea;
	}

	public void setTrain_tea(int train_tea) {
		this.train_tea = train_tea;
	}

	public int getTrain_oth() {
		return train_oth;
	}

	public void setTrain_oth(int train_oth) {
		this.train_oth = train_oth;
	}

	public int getServ_scientific_cnt() {
		return serv_scientific_cnt;
	}

	public void setServ_scientific_cnt(int serv_scientific_cnt) {
		this.serv_scientific_cnt = serv_scientific_cnt;
	}

	public int getServ_teach_cnt() {
		return serv_teach_cnt;
	}

	public void setServ_teach_cnt(int serv_teach_cnt) {
		this.serv_teach_cnt = serv_teach_cnt;
	}

	public int getServ_society_cnt() {
		return serv_society_cnt;
	}

	public void setServ_society_cnt(int serv_society_cnt) {
		this.serv_society_cnt = serv_society_cnt;
	}

	public int getEssay_cnt() {
		return essay_cnt;
	}

	public void setEssay_cnt(int essay_cnt) {
		this.essay_cnt = essay_cnt;
	}

	public int getThree_search() {
		return three_search;
	}

	public void setThree_search(int three_search) {
		this.three_search = three_search;
	}

	public int getCore_publication() {
		return core_publication;
	}

	public void setCore_publication(int core_publication) {
		this.core_publication = core_publication;
	}

	public int getAwards_cnt() {
		return awards_cnt;
	}

	public void setAwards_cnt(int awards_cnt) {
		this.awards_cnt = awards_cnt;
	}

	public int getNational_awards_cnt() {
		return national_awards_cnt;
	}

	public void setNational_awards_cnt(int national_awards_cnt) {
		this.national_awards_cnt = national_awards_cnt;
	}

	public int getProvincial_awards_cnt() {
		return provincial_awards_cnt;
	}

	public void setProvincial_awards_cnt(int provincial_awards_cnt) {
		this.provincial_awards_cnt = provincial_awards_cnt;
	}

	public int getPatent_cnt() {
		return patent_cnt;
	}

	public void setPatent_cnt(int patent_cnt) {
		this.patent_cnt = patent_cnt;
	}

	public int getTea_patent_cnt() {
		return tea_patent_cnt;
	}

	public void setTea_patent_cnt(int tea_patent_cnt) {
		this.tea_patent_cnt = tea_patent_cnt;
	}

	public int getStu_patent_cnt() {
		return stu_patent_cnt;
	}

	public void setStu_patent_cnt(int stu_patent_cnt) {
		this.stu_patent_cnt = stu_patent_cnt;
	}

	public int getEq_count() {
		return eq_count;
	}

	public void setEq_count(int eq_count) {
		this.eq_count = eq_count;
	}
	
}
