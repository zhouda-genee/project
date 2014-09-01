package com.genee.service.framework.core.base.dao;

import java.io.StringWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.SqlTypeValue;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.genee.service.framework.core.base.JdbcTemplateParam;
import com.genee.service.framework.core.base.PageSupport;
import com.genee.service.framework.utils.map.MapToBeanUtil;

public class MysqlBaseDao extends BaseDao {
	
	@Override
	public List<Map<String, Object>> procedureForList(String productName,
			Object... inparam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> procedureForMap(String productName,
			Object... inparam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> void queryForList(JdbcTemplateParam param, PageSupport<T> page, Class<T> object) {
		String sql = param.getSql();
		// 查询总记录数
		param.setSql(getSQLCount(sql));
		page.setTotalCount(queryForInt(param));
		// 设置起始值和结束值
		int startIndex = 0;
		if (page.getPage() != 1)
			startIndex = (page.getPage() - 1) * page.getPageSize() + 1;
		// 查询结果集
		StringBuffer paginationSQL = new StringBuffer(" ");
		paginationSQL.append(sql);
		paginationSQL.append(" limit "+ startIndex+"," + (page.getPageSize() - 1));
		param.setSql(paginationSQL.toString());
		List<Map<String, Object>> result = queryForList(param);
		List<T> obj = MapToBeanUtil.MapToBean(object, result);
		page.setItems(obj);

	}
	
	private String getSQLCount(String sql){
		return "select count(*) from ("+ sql + ") table1";
	}

	@Override
	public int insert(final JdbcTemplateParam param) {
	    KeyHolder keyHolder = new GeneratedKeyHolder();
	    jdbcTemplate.update(
	            new PreparedStatementCreator() {
	                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
	                    PreparedStatement ps = con.prepareStatement(param.getSql(), Statement.RETURN_GENERATED_KEYS);
	                    Object[] args = param.getArgs();
	                    int[] argTypes = param.getArgType();
	                    for (int i = 0; i < args.length; i++) {
	                    	setValue(ps, i + 1, argTypes[i], args[i]);
	                    }
	                    return ps;
	                }
	            }, keyHolder);
	    return keyHolder.getKey().intValue();
	}
	
	private static void setValue(PreparedStatement ps, int paramIndex, int sqlType,  Object inValue) throws SQLException {

		if (sqlType == Types.VARCHAR || sqlType == Types.LONGVARCHAR ||
				(sqlType == Types.CLOB && isStringValue(inValue.getClass()))) {
			ps.setString(paramIndex, inValue.toString());
		}
		else if (sqlType == Types.DECIMAL || sqlType == Types.NUMERIC) {
			if (inValue instanceof BigDecimal) {
				ps.setBigDecimal(paramIndex, (BigDecimal) inValue);
			}
			else {
				ps.setObject(paramIndex, inValue, sqlType);
			}
		}
		else if (sqlType == Types.DATE) {
			if (inValue instanceof java.util.Date) {
				if (inValue instanceof java.sql.Date) {
					ps.setDate(paramIndex, (java.sql.Date) inValue);
				}
				else {
					ps.setDate(paramIndex, new java.sql.Date(((java.util.Date) inValue).getTime()));
				}
			}
			else if (inValue instanceof Calendar) {
				Calendar cal = (Calendar) inValue;
				ps.setDate(paramIndex, new java.sql.Date(cal.getTime().getTime()), cal);
			}
			else {
				ps.setObject(paramIndex, inValue, Types.DATE);
			}
		}
		else if (sqlType == Types.TIME) {
			if (inValue instanceof java.util.Date) {
				if (inValue instanceof java.sql.Time) {
					ps.setTime(paramIndex, (java.sql.Time) inValue);
				}
				else {
					ps.setTime(paramIndex, new java.sql.Time(((java.util.Date) inValue).getTime()));
				}
			}
			else if (inValue instanceof Calendar) {
				Calendar cal = (Calendar) inValue;
				ps.setTime(paramIndex, new java.sql.Time(cal.getTime().getTime()), cal);
			}
			else {
				ps.setObject(paramIndex, inValue, Types.TIME);
			}
		}
		else if (sqlType == Types.TIMESTAMP) {
			if (inValue instanceof java.util.Date) {
				if (inValue instanceof java.sql.Timestamp) {
					ps.setTimestamp(paramIndex, (java.sql.Timestamp) inValue);
				}
				else {
					ps.setTimestamp(paramIndex, new java.sql.Timestamp(((java.util.Date) inValue).getTime()));
				}
			}
			else if (inValue instanceof Calendar) {
				Calendar cal = (Calendar) inValue;
				ps.setTimestamp(paramIndex, new java.sql.Timestamp(cal.getTime().getTime()), cal);
			}
			else {
				ps.setObject(paramIndex, inValue, Types.TIMESTAMP);
			}
		}
		else if (sqlType == SqlTypeValue.TYPE_UNKNOWN) {
			if (isStringValue(inValue.getClass())) {
				ps.setString(paramIndex, inValue.toString());
			}
			else if (isDateValue(inValue.getClass())) {
				ps.setTimestamp(paramIndex, new java.sql.Timestamp(((java.util.Date) inValue).getTime()));
			}
			else if (inValue instanceof Calendar) {
				Calendar cal = (Calendar) inValue;
				ps.setTimestamp(paramIndex, new java.sql.Timestamp(cal.getTime().getTime()), cal);
			}
			else {
				ps.setObject(paramIndex, inValue);
			}
		}
		else {
			ps.setObject(paramIndex, inValue, sqlType);
		}
	}
	
	private static boolean isStringValue(Class<?> inValueType) {
		return (CharSequence.class.isAssignableFrom(inValueType) ||
				StringWriter.class.isAssignableFrom(inValueType));
	}
	
	private static boolean isDateValue(Class<?> inValueType) {
		return (java.util.Date.class.isAssignableFrom(inValueType) &&
				!(java.sql.Date.class.isAssignableFrom(inValueType) ||
						java.sql.Time.class.isAssignableFrom(inValueType) ||
						java.sql.Timestamp.class.isAssignableFrom(inValueType)));
	}

}
