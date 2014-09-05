package com.genee.service.module.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.genee.service.framework.core.base.JdbcTemplateParam;
import com.genee.service.framework.core.base.PageSupport;
import com.genee.service.framework.core.base.dao.BaseDao;
import com.genee.service.module.pojo.EquipmentIndexEntity;

@Repository
public class StatisticsDao {
	
	@Autowired
	@Qualifier("basedao")
	private BaseDao baseDao;
	
	/**
	 * 
	 * @Title: queryEquipmentIndex 
	 * @Description: 仪器统计查询结果 
	 * @param eq_name	仪器名称模糊查询
	 * @param eq_type	仪器类型
	 * @param eq_org	仪器组织机构
	 * @param eq_contact	仪器联系人
	 * @param eq_incharge	仪器负责人
	 * @param lab_org	课题组组织机构
	 * @param lab	课题组
	 * @param user	使用者
	 * @param startDate	开始时间
	 * @param endDate 结束时间
	 * @param sortName	排序字段
	 * @param sort	升序还是降序
	 * @param PageSupport	分页信息 如果为null则表示不分页
	 * @throws
	 */
	public List<EquipmentIndexEntity> queryEquipmentIndex(String eq_name, String eq_type, String eq_org, String eq_contact, String eq_incharge, 
														  String lab_org, String lab, String user, 
														  long startDate, long endDate, 
														  String sortName, String sort,
														  PageSupport<EquipmentIndexEntity> pageSupport) {
		StringBuilder sql = new StringBuilder();
		sql.append("select _eq.eq_id, "); // 仪器ID
		sql.append("       _eq.eq_name, "); // 仪器名称
		sql.append("       _eq.eq_price, "); // 仪器价格
		sql.append("       _eq.principal, "); // 负责人
		sql.append("       _eq.linkman, "); // 联系人
		sql.append("       _eq.innet_dur, "); // 入网时长
		sql.append("       round(sum(_stat.fault_dur) / 60 / 60, 2)                  as fault_dur, "); // 故障时长
		sql.append("       round(sum(_stat.appointment_dur) / 60 / 60, 2)            as appointment_dur, "); // 预约机时
		sql.append("       round(sum(_stat.used_dur) / 60 / 60, 2)                   as used_dur, "); // 使用机时
		sql.append("       round(sum(_stat.owner_used_dur) / 60 / 60, 2)             as owner_used_dur, "); // 机主使用机时
		sql.append("       round(sum(_stat.open_dur) / 60 / 60, 2)                   as open_dur, "); // 开放机时
		sql.append("       round(sum(_stat.valid_dur) / 60 / 60, 2)                  as valid_dur, "); // 有效机时
		sql.append("       round(sum(_stat.test_dur) / 60 / 60, 2)                   as test_dur, "); // 委托测试机时
		sql.append("       round(sum(_stat.scientific_dur) / 60 / 60, 2)             as scientific_dur, "); //科研机时
		sql.append("       round(sum(_stat.teach_dur) / 60 / 60, 2)                  as teach_dur, "); // 教学机时
		sql.append("       round(sum(_stat.society_dur) / 60 / 60, 2)                as society_dur, "); // 社会项目机时
		sql.append("       sum(_stat.used_times)                                     as used_times, "); // 使用次数
		sql.append("       sum(_stat.test_sam_cnt)                                   as test_sam_cnt, "); // 测样数
		sql.append("       sum(_stat.used_sam_cnt)                                   as used_sam_cnt, "); // 使用测样数
		sql.append("       sum(_stat.give_sam_cnt)                                   as give_sam_cnt, "); // 送样测样数
		sql.append("       sum(_stat.owner_sam_cnt)                                  as owner_sam_cnt, "); // 机主测样数
		sql.append("       sum(_stat.stu_sam_cnt)                                    as stu_sam_cnt, "); // 学生测样数
		sql.append("       sum(_stat.used_charge)                                    as used_charge, "); // 使用收费
		sql.append("       sum(_stat.on_cam_charge)                                  as on_cam_charge, "); // 校内收费
		sql.append("       sum(_stat.off_cam_charge)                                 as off_cam_charge, "); // 校外收费
		sql.append("       sum(_stat.delegation_charge)                              as delegation_charge, "); // 委托测试收费
		sql.append("       sum(_stat.earnings_charge)                                as earnings_charge, "); // 创收金额
		sql.append("       sum(_stat.repair_cost)                                    as repair_cost, "); // 维修费
		sql.append("       sum(_stat.train_cost)                                     as train_cost, "); // 培训费
		sql.append("       sum(_stat.train_cnt)                                      as train_cnt, "); // 培训人数
		sql.append("       sum(_stat.train_stu)                                      as train_stu, "); // 培训学生
		sql.append("       sum(_stat.train_tea)                                      as train_tea, "); // 培训教师
		sql.append("       sum(_stat.train_oth)                                      as train_oth, "); // 培训其他人
		sql.append("       sum(_stat.serv_scientific_cnt)                            as serv_scientific_cnt, "); // 服务科研项目数
		sql.append("       sum(_stat.serv_teach_cnt)                                 as serv_teach_cnt, "); // 服务教学项目数
		sql.append("       sum(_stat.serv_society_cnt)                               as serv_society_cnt, "); // 服务社会项目数
		sql.append("       sum(_stat.essay_cnt)                                      as essay_cnt, "); // 论文数
		sql.append("       sum(_stat.three_search)                                   as three_search, "); // 三大检索
		sql.append("       sum(_stat.core_publication)                               as core_publication, "); // 核心刊物
		sql.append("       sum(_stat.awards_cnt)                                     as awards_cnt, "); // 获奖数
		sql.append("       sum(_stat.national_awards_cnt)                            as national_awards_cnt, "); // 国家级获奖数
		sql.append("       sum(_stat.provincial_awards_cnt)                          as provincial_awards_cnt, "); // 省部级获奖数 
		sql.append("       sum(_stat.patent_cnt)                                     as patent_cnt, "); // 专利数
		sql.append("       sum(_stat.tea_patent_cnt)                                 as tea_patent_cnt, "); // 教师专利数
		sql.append("       sum(_stat.stu_patent_cnt)                                 as stu_patent_cnt "); // 学生专利数
		sql.append("from   v_equipment _eq, ");
		sql.append("       s_day_stat _stat ");
		sql.append("where  _eq.eq_id = _stat.equipment_id ");
		sql.append("	   and _stat.time between ? and ? ");
		
		List<Object> paramValue = new ArrayList<Object>();
		List<Integer> paramType = new ArrayList<Integer>();
		
		paramValue.add(startDate);
		paramType.add(java.sql.Types.INTEGER);
		paramValue.add(endDate);
		paramType.add(java.sql.Types.INTEGER);
		
		if (StringUtils.isNotEmpty(eq_name) && !"null".equals(eq_name)) { // 仪器名称查询
			sql.append(" and _eq.eq_name like ? ");
			paramValue.add("%" + eq_name + "%");
			paramType.add(java.sql.Types.VARCHAR);
		} 
		if (StringUtils.isNotEmpty(eq_type) && !"null".equals(eq_type)) { // 仪器分类查询
			sql.append(" and _eq.eq_id in (select _r_t_eq.id2 from _r_tag_equipment _r_t_eq where _r_t_eq.id1 = ?) ");
			paramValue.add(eq_type);
			paramType.add(java.sql.Types.INTEGER);
		}
		if (StringUtils.isNotEmpty(eq_org) && !"null".equals(eq_org)) { // 仪器组织机构
			sql.append(" and _eq.eq_id in (select _r_t_eq.id2 from _r_tag_equipment _r_t_eq where _r_t_eq.id1 = ?) ");
			paramValue.add(eq_org);
			paramType.add(java.sql.Types.INTEGER);
		} 
		if (StringUtils.isNotEmpty(eq_contact) && !"null".equals(eq_contact)) { // 仪器联系人查询
			sql.append(" and _eq.eq_id in (select _r_u_eq.id2 from _r_user_equipment _r_u_eq where _r_u_eq.id1 in (" + eq_contact + ") and _r_u_eq.type = 'contact') ");
		}
		if (StringUtils.isNotEmpty(eq_incharge) && !"null".equals(eq_incharge)) { // 仪器负责人查询
			sql.append(" and _eq.eq_id in (select _r_u_eq.id2 from _r_user_equipment _r_u_eq where _r_u_eq.id1 in (" + eq_incharge + ") and _r_u_eq.type = 'incharge') ");
		}
		if (StringUtils.isNotEmpty(lab_org) && !"null".equals(lab_org)) { // 课题组组织机构查询
			sql.append(" and _stat.user_id in (select _r_u_t.id1 from _r_user_tag _r_u_t where _r_u_t.id2 = ?) ");
			paramValue.add(lab_org);
			paramType.add(java.sql.Types.INTEGER);
		}
		if (StringUtils.isNotEmpty(lab) && !"null".equals(lab)) { // 课题组查询
			sql.append(" and _stat.user_id in (select _u.id from user where user _u where _u.lab_id in (" + lab + ")) ");
		}
		if (StringUtils.isNotEmpty(user) && !"null".equals(user)) { // 使用者查询
			sql.append(" and _stat.user_id in (" + user + ")");
		}
		sql.append("group  by _eq.eq_id, _eq.eq_name, _eq.eq_price, _eq.principal, _eq.linkman, _eq.innet_dur ");
		if (StringUtils.isNotEmpty(sortName) && !"null".equals(sortName)){
			sql.append("order by " + sortName + " " + sort);
		} else {
			sql.append("order by _eq.eq_name");
		}
		// 遍历数据类型
		int[] typeArray = new int[paramType.size()];
		for (int i = 0; i < paramType.size(); i++) {
			typeArray[i] = paramType.get(i);
		}
		JdbcTemplateParam param = new JdbcTemplateParam(sql.toString(), paramValue.toArray(), typeArray);
		List<EquipmentIndexEntity> result = null;
		if (pageSupport != null) {
			baseDao.queryForList(param, pageSupport, EquipmentIndexEntity.class);
		} else {
			result = baseDao.queryForList(param, EquipmentIndexEntity.class);
		}
		return result;
	}
	
	/**
	 * 
	 * @Title: queryEquipmentIndexCount 
	 * @Description: 仪器统计信息 
	 * @param eq_name	仪器名称模糊查询
	 * @param eq_type	仪器类型
	 * @param eq_org	仪器组织机构
	 * @param eq_contact	仪器联系人
	 * @param eq_incharge	仪器负责人
	 * @param lab_org	课题组组织机构
	 * @param lab	课题组
	 * @param user	使用者
	 * @param startDate	开始时间
	 * @param endDate 结束时间
	 * @return EquipmentIndexEntity
	 */
	public EquipmentIndexEntity queryEquipmentIndexCount(String eq_name, String eq_type, String eq_org, String eq_contact, String eq_incharge, 
													  String lab_org, String lab, String user, 
													  long startDate, long endDate){
		
		StringBuilder sql = new StringBuilder();
		sql.append("select count(distinct _stat.equipment_id)                		 as eq_count, "); // 仪器数量
		sql.append("	   round(sum(_stat.fault_dur) / 60 / 60, 2)                  as fault_dur, "); // 故障时长
		sql.append("       round(sum(_stat.appointment_dur) / 60 / 60, 2)            as appointment_dur, "); // 预约机时
		sql.append("       round(sum(_stat.used_dur) / 60 / 60, 2)                   as used_dur, "); // 使用机时
		sql.append("       round(sum(_stat.owner_used_dur) / 60 / 60, 2)             as owner_used_dur, "); // 机主使用机时
		sql.append("       round(sum(_stat.open_dur) / 60 / 60, 2)                   as open_dur, "); // 开放机时
		sql.append("       round(sum(_stat.valid_dur) / 60 / 60, 2)                  as valid_dur, "); // 有效机时
		sql.append("       round(sum(_stat.test_dur) / 60 / 60, 2)                   as test_dur, "); // 委托测试机时
		sql.append("       round(sum(_stat.scientific_dur) / 60 / 60, 2)             as scientific_dur, "); //科研机时
		sql.append("       round(sum(_stat.teach_dur) / 60 / 60, 2)                  as teach_dur, "); // 教学机时
		sql.append("       round(sum(_stat.society_dur) / 60 / 60, 2)                as society_dur, "); // 社会项目机时
		sql.append("       sum(_stat.used_times)                                     as used_times, "); // 使用次数
		sql.append("       sum(_stat.test_sam_cnt)                                   as test_sam_cnt, "); // 测样数
		sql.append("       sum(_stat.used_sam_cnt)                                   as used_sam_cnt, "); // 使用测样数
		sql.append("       sum(_stat.give_sam_cnt)                                   as give_sam_cnt, "); // 送样测样数
		sql.append("       sum(_stat.owner_sam_cnt)                                  as owner_sam_cnt, "); // 机主测样数
		sql.append("       sum(_stat.stu_sam_cnt)                                    as stu_sam_cnt, "); // 学生测样数
		sql.append("       sum(_stat.used_charge)                                    as used_charge, "); // 使用收费
		sql.append("       sum(_stat.on_cam_charge)                                  as on_cam_charge, "); // 校内收费
		sql.append("       sum(_stat.off_cam_charge)                                 as off_cam_charge, "); // 校外收费
		sql.append("       sum(_stat.delegation_charge)                              as delegation_charge, "); // 委托测试收费
		sql.append("       sum(_stat.earnings_charge)                                as earnings_charge, "); // 创收金额
		sql.append("       sum(_stat.repair_cost)                                    as repair_cost, "); // 维修费
		sql.append("       sum(_stat.train_cost)                                     as train_cost, "); // 培训费
		sql.append("       sum(_stat.train_cnt)                                      as train_cnt, "); // 培训人数
		sql.append("       sum(_stat.train_stu)                                      as train_stu, "); // 培训学生
		sql.append("       sum(_stat.train_tea)                                      as train_tea, "); // 培训教师
		sql.append("       sum(_stat.train_oth)                                      as train_oth, "); // 培训其他人
		sql.append("       sum(_stat.serv_scientific_cnt)                            as serv_scientific_cnt, "); // 服务科研项目数
		sql.append("       sum(_stat.serv_teach_cnt)                                 as serv_teach_cnt, "); // 服务教学项目数
		sql.append("       sum(_stat.serv_society_cnt)                               as serv_society_cnt, "); // 服务社会项目数
		sql.append("       sum(_stat.essay_cnt)                                      as essay_cnt, "); // 论文数
		sql.append("       sum(_stat.three_search)                                   as three_search, "); // 三大检索
		sql.append("       sum(_stat.core_publication)                               as core_publication, "); // 核心刊物
		sql.append("       sum(_stat.awards_cnt)                                     as awards_cnt, "); // 获奖数
		sql.append("       sum(_stat.national_awards_cnt)                            as national_awards_cnt, "); // 国家级获奖数
		sql.append("       sum(_stat.provincial_awards_cnt)                          as provincial_awards_cnt, "); // 省部级获奖数 
		sql.append("       sum(_stat.patent_cnt)                                     as patent_cnt, "); // 专利数
		sql.append("       sum(_stat.tea_patent_cnt)                                 as tea_patent_cnt, "); // 教师专利数
		sql.append("       sum(_stat.stu_patent_cnt)                                 as stu_patent_cnt "); // 学生专利数
		sql.append("from   v_equipment _eq, ");
		sql.append("       s_day_stat _stat ");
		sql.append("where  _eq.eq_id = _stat.equipment_id ");
		sql.append("	   and _stat.time between ? and ? ");
		
		List<Object> paramValue = new ArrayList<Object>();
		List<Integer> paramType = new ArrayList<Integer>();
		
		paramValue.add(startDate);
		paramType.add(java.sql.Types.INTEGER);
		paramValue.add(endDate);
		paramType.add(java.sql.Types.INTEGER);
		
		if (StringUtils.isNotEmpty(eq_name) && !"null".equals(eq_name)) { // 仪器名称查询
			sql.append(" and _eq.eq_name like ? ");
			paramValue.add("%" + eq_name + "%");
			paramType.add(java.sql.Types.VARCHAR);
		} 
		if (StringUtils.isNotEmpty(eq_type) && !"null".equals(eq_type)) { // 仪器分类查询
			sql.append(" and _eq.eq_id in (select _r_t_eq.id2 from _r_tag_equipment _r_t_eq where _r_t_eq.id1 = ?) ");
			paramValue.add(eq_type);
			paramType.add(java.sql.Types.INTEGER);
		}
		if (StringUtils.isNotEmpty(eq_org) && !"null".equals(eq_org)) { // 仪器组织机构
			sql.append(" and _eq.eq_id in (select _r_t_eq.id2 from _r_tag_equipment _r_t_eq where _r_t_eq.id1 = ?) ");
			paramValue.add(eq_org);
			paramType.add(java.sql.Types.INTEGER);
		} 
		if (StringUtils.isNotEmpty(eq_contact) && !"null".equals(eq_contact)) { // 仪器联系人查询
			sql.append(" and _eq.eq_id in (select _r_u_eq.id2 from _r_user_equipment _r_u_eq where _r_u_eq.id1 in (" + eq_contact + ") and _r_u_eq.type = 'contact') ");
		}
		if (StringUtils.isNotEmpty(eq_incharge) && !"null".equals(eq_incharge)) { // 仪器负责人查询
			sql.append(" and _eq.eq_id in (select _r_u_eq.id2 from _r_user_equipment _r_u_eq where _r_u_eq.id1 in (" + eq_incharge + ") and _r_u_eq.type = 'incharge') ");
		}
		if (StringUtils.isNotEmpty(lab_org) && !"null".equals(lab_org)) { // 课题组组织机构查询
			sql.append(" and _stat.user_id in (select _r_u_t.id1 from _r_user_tag _r_u_t where _r_u_t.id2 = ?) ");
			paramValue.add(lab_org);
			paramType.add(java.sql.Types.INTEGER);
		}
		if (StringUtils.isNotEmpty(lab) && !"null".equals(lab)) { // 课题组查询
			sql.append(" and _stat.user_id in (select _u.id from user where user _u where _u.lab_id in (" + lab + ")) ");
		}
		if (StringUtils.isNotEmpty(user) && !"null".equals(user)) { // 使用者查询
			sql.append(" and _stat.user_id in (" + user + ")");
		}
		
		// 遍历数据类型
		int[] typeArray = new int[paramType.size()];
		for (int i = 0; i < paramType.size(); i++) {
			typeArray[i] = paramType.get(i);
		}
		JdbcTemplateParam param = new JdbcTemplateParam(sql.toString(), paramValue.toArray(), typeArray);
		EquipmentIndexEntity result = baseDao.queryForMap(param, EquipmentIndexEntity.class);
		
		return result;
	}
}
