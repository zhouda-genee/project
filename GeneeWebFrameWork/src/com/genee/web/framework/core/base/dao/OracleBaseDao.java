package com.genee.web.framework.core.base.dao;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import oracle.jdbc.driver.OracleTypes;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;

import com.genee.web.framework.core.base.JdbcTemplateParam;
import com.genee.web.framework.core.base.PageSupport;

public class OracleBaseDao extends BaseDao {
	
	public int querySequencesId(String seqName) {
		String sql = "select " + seqName + ".nextval from dual";
		JdbcTemplateParam jdbcTemplateParam = new JdbcTemplateParam(sql);
		return queryForInt(jdbcTemplateParam);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Map<String, Object>> procedureForList(String productName, final Object... inparam) {
		StringBuffer str = new StringBuffer();
		for (int i = 0; i < inparam.length + 3; i++) {
			str.append("?, ");
		}
		String sql = "{call " + productName + "(" + str.substring(0, str.length() - 2) + ")}";
		
		StringBuffer print = new StringBuffer();
		for (int i = 0; i < inparam.length; i++) {
			print.append(inparam[i] + ", ");
		}
		logger.warn("***********************************************************************\n"
						+ "* Execute Procedure: " + "{call " + productName + "(" + print.toString() + "flag, msg, cursor)}"
						+ "\n***********************************************************************");
		
		List<Map<String, Object>> list = (List<Map<String, Object>>) jdbcTemplate.execute(sql, new CallableStatementCallback() {
			@Override
			public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				for (int i = 1; i <= inparam.length; i++) {
					if (inparam[i - 1] instanceof java.lang.String)
						cs.setString(i, (String) inparam[i - 1]);
					else if (inparam[i - 1] instanceof java.lang.Integer)
						cs.setInt(i, (Integer) inparam[i - 1]);
					else if (inparam[i - 1] instanceof java.lang.Object)
						cs.setObject(i, inparam[i - 1]);
				}
				cs.registerOutParameter(inparam.length + 1, java.sql.Types.INTEGER);
				cs.registerOutParameter(inparam.length + 2, java.sql.Types.VARCHAR);
				cs.registerOutParameter(inparam.length + 3, OracleTypes.CURSOR);
				
				boolean flag = cs.execute();
				logger.warn("Procedure debug: execute " + flag + ", ResultSet " + (cs.getResultSet() == null) + ", UpdateCount " + (cs.getUpdateCount() == -1));
				ResultSet rs = null;
				try {
					rs = (ResultSet) cs.getObject(inparam.length + 3);
					while (rs.next()) {
						Map<String, Object> map = getResult(rs);
						formatDate(map);
						list.add(map);
					}
				} catch (Exception e) {
					logger.error(e);
				} finally {
					if (rs != null){
						rs.close();
						rs = null;
					}
					if (cs != null){
						cs.close();
						cs = null;
					}
				}
				return list;
			}
		});
		return list;
	}

	@Override
	public Map<String, Object> procedureForMap(String productName, Object... inparam) {
		return procedureForList(productName, inparam).get(0);
	}

	@Override
	public void queryForList(JdbcTemplateParam param, PageSupport page) {
		String sql = param.getSql();
		// 查询总记录数
		param.setSql(getSQLCount(sql));
		page.setTotalCount(queryForInt(param));
		// 设置起始值和结束值
		int startIndex = 1;
		if (page.getPage() != 1)
			startIndex = (page.getPage() - 1) * page.getPageSize() + 1;
		int endIndex = page.getPage() * page.getPageSize();
		// 查询结果集
		StringBuffer paginationSQL = new StringBuffer(" ");
		paginationSQL.append("SELECT * FROM (SELECT ROWNUM rn, obj.* FROM ( ");
		paginationSQL.append(sql);
		paginationSQL.append(") obj) WHERE rn BETWEEN " + startIndex + " AND " + endIndex);
		param.setSql(paginationSQL.toString());
		page.setItems(queryForList(param));
	}
	
	private String getSQLCount(String sql){
		String sqlCount = "select count(*) from ("+ sql + ")";
		return sqlCount;
	}

	@Override
	public int insert(JdbcTemplateParam param) {
		String sql = param.getSql();
		Object[] args = param.getArgs();
		int[] argTypes = param.getArgType();
		return jdbcTemplate.update(sql, args, argTypes);
	}

}
