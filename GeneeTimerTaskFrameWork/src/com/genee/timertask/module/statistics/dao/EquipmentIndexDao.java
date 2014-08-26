package com.genee.timertask.module.statistics.dao;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.genee.timertask.framework.core.base.JdbcTemplateParam;
import com.genee.timertask.framework.core.base.dao.BaseDao;
import com.genee.timertask.framework.utils.dateutil.DateUtil;
import com.genee.timertask.framework.utils.timestamp.TimestampUtil;
import com.genee.timertask.module.statistics.pojo.EquipmentIndexEntity;

@Repository
public class EquipmentIndexDao {
	
	@Autowired
	@Qualifier("basedao")
	private BaseDao baseDao;
	
	/**
	 * 
	 * @Title: deleteEquipmentIndexByDay 
	 * @Description: 删除某一天的指标记录 
	 * @param  day	某一天时间的时间戳（不包含时分秒）
	 * @throws
	 */
	public void deleteEquipmentIndexByDay(long day){
		String sql = "delete from s_day_stat where time = ?";
		JdbcTemplateParam param = new JdbcTemplateParam(sql, new Object[]{day}, new int[]{java.sql.Types.INTEGER});
		baseDao.update(param);
	}
	
	/**
	 * 
	 * @Title: insertEquipmentIndexByUser 
	 * @Description: 添加仪器记录，包含使用者 
	 * @param equipmentIndex
	 * @throws
	 */
	public void insertEquipmentIndexByUser(EquipmentIndexEntity equipmentIndex){
		String sql = "insert into s_day_stat (id, equipment_id, user_id, time, optdate, "
				+ "fault_dur, appointment_dur, used_dur, owner_used_dur, open_dur, "
				+ "valid_dur, test_dur, scientific_dur, teach_dur, society_dur, "
				+ "used_times, test_sam_cnt, used_sam_cnt, give_sam_cnt, owner_sam_cnt, "
				+ "stu_sam_cnt, used_charge, on_cam_charge, off_cam_charge, delegation_charge, "
				+ "earnings_charge, repair_cost, train_cnt, train_stu, train_tea, "
				+ "train_oth, serv_scientific_cnt, serv_teach_cnt, serv_society_cnt, essay_cnt, "
				+ "three_search, core_publication, awards_cnt, national_awards_cnt, provincial_awards_cnt, "
				+ "patent_cnt, tea_patent_cnt, stu_patent_cnt, train_cost ) values ("
				+ " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,"
				+ " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,"
				+ " ?, ?, ?, ?)";
		JdbcTemplateParam param = new JdbcTemplateParam(sql, 
				new Object[]{equipmentIndex.getId(), equipmentIndex.getEquipmentId(), equipmentIndex.getUserId(), equipmentIndex.getTime(), TimestampUtil.dateToTimestamp10(DateUtil.currentSystemTimeDate()),
				equipmentIndex.getFaultDur(),equipmentIndex.getAppointmentDur(),equipmentIndex.getUsedDur(), equipmentIndex.getOwnerUsedDur(), equipmentIndex.getOpenDur(),
				equipmentIndex.getValidDur(),equipmentIndex.getTestDur(),equipmentIndex.getScientificDur(),equipmentIndex.getTeachDur(), equipmentIndex.getSocietyDur(), 
				equipmentIndex.getUsedTimes(), equipmentIndex.getTestSamCnt(), equipmentIndex.getUsedSamCnt(), equipmentIndex.getGiveSamCnt(), equipmentIndex.getOwnerSamCnt(), 
				equipmentIndex.getStuSamCnt(), equipmentIndex.getUsedCharge(),equipmentIndex.getOnCamCharge(), equipmentIndex.getOffCamCharge(), equipmentIndex.getDelegationCharge(),
				equipmentIndex.getEarningsCharge(),equipmentIndex.getRepairCost(), equipmentIndex.getTrainCnt(), equipmentIndex.getTrainStu(),equipmentIndex.getTrainTea(),
				equipmentIndex.getTrainOth(),equipmentIndex.getServScientificCnt(),equipmentIndex.getServTeachCnt(), equipmentIndex.getServSocietyCnt(),equipmentIndex.getEssayCnt(),
				equipmentIndex.getThreeSearch(),equipmentIndex.getCorePublication(),equipmentIndex.getAwardsCnt(),equipmentIndex.getNationalAwardsCnt(),equipmentIndex.getProvincialAwardsCnt(),
				equipmentIndex.getPatentCnt(),equipmentIndex.getTeaPatentCnt(),equipmentIndex.getStuPatentCnt(),equipmentIndex.getTrainCost()},
				new int[]{java.sql.Types.VARCHAR, java.sql.Types.INTEGER, StringUtils.isEmpty(equipmentIndex.getUserId()) ? java.sql.Types.NULL:java.sql.Types.INTEGER, java.sql.Types.INTEGER,java.sql.Types.INTEGER,
				java.sql.Types.INTEGER,java.sql.Types.INTEGER,java.sql.Types.INTEGER,java.sql.Types.INTEGER,java.sql.Types.INTEGER,
				java.sql.Types.INTEGER,java.sql.Types.INTEGER,java.sql.Types.INTEGER,java.sql.Types.INTEGER,java.sql.Types.INTEGER,
				java.sql.Types.INTEGER,java.sql.Types.INTEGER,java.sql.Types.INTEGER,java.sql.Types.INTEGER,java.sql.Types.INTEGER,
				java.sql.Types.INTEGER,java.sql.Types.DOUBLE,java.sql.Types.DOUBLE,java.sql.Types.DOUBLE,java.sql.Types.DOUBLE,
				java.sql.Types.DOUBLE,java.sql.Types.DOUBLE,java.sql.Types.INTEGER,java.sql.Types.INTEGER,java.sql.Types.INTEGER,
				java.sql.Types.INTEGER,java.sql.Types.INTEGER,java.sql.Types.INTEGER,java.sql.Types.INTEGER,java.sql.Types.INTEGER,
				java.sql.Types.INTEGER,java.sql.Types.INTEGER,java.sql.Types.INTEGER,java.sql.Types.INTEGER,java.sql.Types.INTEGER,
				java.sql.Types.INTEGER,java.sql.Types.INTEGER,java.sql.Types.INTEGER,java.sql.Types.DOUBLE});
		baseDao.update(param);
	}
}
