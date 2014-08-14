package com.genee.web.framework.core.base.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.genee.web.framework.core.base.JdbcTemplateParam;
import com.genee.web.framework.core.base.PageSupport;
import com.genee.web.framework.utils.dateutil.DateUtil;

/**
 * ClassName:BaseService
 * 
 * @author ZhouDa
 * @version
 * @since Ver 1.1
 * @Date 2012-6-19 下午09:52:27
 * 
 */
public abstract class BaseDao {
	
	protected final Logger logger = Logger.getLogger(getClass());

	@Autowired
	@Qualifier("defaultJdbcTemplate")
	protected JdbcTemplate jdbcTemplate;
	
	protected void formatDate(Map<String, Object> result){
		Iterator<Entry<String, Object>> iter = result.entrySet().iterator();
		while(iter.hasNext()){
			Entry<String, Object> entry = iter.next();
			if (entry.getValue() instanceof java.util.Date && entry.getValue() != null){
				entry.setValue(DateUtil.date2String((java.util.Date)entry.getValue()));
			} else if (entry.getValue() instanceof java.math.BigInteger && entry.getValue() != null){
				entry.setValue(((java.math.BigInteger)entry.getValue()).intValue());
			} else if (entry.getValue() instanceof java.math.BigDecimal && entry.getValue() != null){
				try{
					entry.setValue(((java.math.BigDecimal)entry.getValue()).toBigIntegerExact().intValue());
				} catch (ArithmeticException ex){
					entry.setValue(((java.math.BigDecimal)entry.getValue()).toPlainString());
				}
				
			} else if (entry.getValue() == null){
				entry.setValue("");
			}
		}
	}
	
	protected void formatDate(List<Map<String, Object>> results){
		for (Map<String, Object> result : results) {
			formatDate(result);
		}
	}
	
	public Map<String, Object> getResult(ResultSet rs) throws SQLException{
		ResultSetMetaData rsmd = rs.getMetaData();
	    int columnCount = rsmd.getColumnCount();
	    Map<String, Object> result = new HashMap<String, Object>(columnCount);
	    for (int i = 1; i <= columnCount; i++) {
			String key = rsmd.getColumnName(i).toLowerCase();
			if (rsmd.getColumnType(i) == Types.CLOB) {
				String clobTxt = clobToString(rs.getClob(i));
				result.put(key, clobTxt);
			} else {
				Object obj = rs.getObject(i);
				result.put(key, obj);
			}
	    }
		return result;
	}
	
	private String clobToString(Clob clob) {
		String reString = "";
		Reader is = null;
		BufferedReader br = null;
		try {
			is = clob.getCharacterStream();
			// 得到流
			br = new BufferedReader(is);
			String s = null;
			s = br.readLine();
			StringBuffer sb = new StringBuffer();
			while (s != null) {
				// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
				sb.append(s);
				s = br.readLine();
			}
			reString = sb.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (is != null)
					is.close();
				if (br != null)
					br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return reString;
	} 
	
	/**
	 * 查询结果集
	 * @param sql
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map<String, Object>> queryForList(JdbcTemplateParam param) {
		String sql = param.getSql();
		Object[] args = param.getArgs();
		int[] argTypes = param.getArgType();
		List<Map<String, Object>> results = jdbcTemplate.query(sql, args, argTypes, new RowMapper(){
			@Override
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				return getResult(rs);
			}
		});
		formatDate(results);
		return results;
	}
	
	/**
	 * 查询单个结果
	 * @param sql
	 * @return
	 */
	public Map<String, Object> queryForMap(JdbcTemplateParam param) {
		List<Map<String, Object>> result = queryForList(param);
		return result.size() > 0 ? result.get(0) : new HashMap<String, Object>();
	}
	
	public int queryForInt(JdbcTemplateParam param) {
		Object obj = queryForObject(param, Integer.class);
		return obj == null ? 0 : (Integer)obj;
	}
	
	public String queryForString(JdbcTemplateParam param) {
		Object obj = queryForObject(param, String.class);
		return obj == null ? "" : (String)obj; 
	}
	
	public Object queryForObject(JdbcTemplateParam param, Class<?> clazz) {
		String sql = param.getSql();
		Object[] args = param.getArgs();
		int[] argTypes = param.getArgType();
		List<?> result = jdbcTemplate.queryForList(sql, args, argTypes, clazz);
		if (result.size() == 0) {
			return null;
		}
		return result.get(0);
	}
	
	public int update(JdbcTemplateParam param) {
		String sql = param.getSql();
		Object[] args = param.getArgs();
		int[] argTypes = param.getArgType();
		return jdbcTemplate.update(sql, args, argTypes);
	}
	
	/**
	 * 
	 * @Title: insert 
	 * @Description: 添加记录
	 * @param param
	 * @return int
	 * @throws
	 */
	public abstract int insert(JdbcTemplateParam param);
	
	/**
	 * 
	 * @Title: procedureForList 
	 * @Description: 执行存储过程并返回集合
	 * @param productName	存储过程名称
	 * @param inparam		输入项
	 * @return List<Map<String,Object>>
	 * @throws
	 */
	public abstract List<Map<String, Object>> procedureForList(String productName, final Object... inparam);
	
	/**
	 * 
	 * @Title: procedureForMap 
	 * @Description: 执行存储过程并返回Map
	 * @param productName	存储过程名称
	 * @param inparam		输入项
	 * @return Map<String, Object>
	 * @throws
	 */
	public abstract Map<String, Object> procedureForMap(String productName, final Object... inparam);
	
	/**
	 * 
	 * @Title: queryForList 
	 * @Description: 分页查找集合
	 * @param param	数据模版
	 * @param page	分页信息
	 * @return void
	 * @throws
	 */
	public abstract void queryForList(JdbcTemplateParam param, PageSupport page);

}
