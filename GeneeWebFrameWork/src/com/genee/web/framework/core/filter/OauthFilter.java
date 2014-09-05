package com.genee.web.framework.core.filter;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.genee.web.framework.core.context.SpringContext;
import com.genee.web.framework.utils.http.HttpClientUtil;
import com.genee.web.framework.utils.prop.PropertiesUtil;
import com.genee.web.framework.utils.stringutil.StringUtil;
import com.genee.web.module.constants.SessionAttributeType;
import com.genee.web.module.service.statistics.UserService;

@WebFilter(description = "oauth过滤器", urlPatterns = { "/abc" })
public class OauthFilter extends HttpServlet implements Filter {
       
	private static final long serialVersionUID = 1L;
	
	private String  client_id,
					client_secert,
					request_token_url, // 获取code地址
					access_token_url, // 获取accesstoken地址
					user_info_url, // 获取用户信息地址
					callback_url; // 回调地址
	
	// oauth请求参数
	private static final String METHOD = "user/get_username";
	private static final String GRANT_TYPE = "authorization_code";
	private static final String SCOPE_USER = "user";
	private static final String RESPONSE_TYPE = "code";
	private static final String STATE = "state";
	// 内部参数
	private static final String PARAM_CODE = "code";
	private static final String PARAM_ACCESS_TOKEN = "access_token";
	private static final String PARAM_TOKEN_TYPE = "token_type";
	private static final String PARAM_ERROR = "error";
	private static final String PARAM_BACK_URL = "backurl";
	private static final String JSONRPC_VERSION = "2.0";
	
	public OauthFilter() {
        super();
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		HttpServletResponse httpServletResponse = (HttpServletResponse)response;
		HttpSession session = httpServletRequest.getSession();
		
		if (session.getAttribute(SessionAttributeType.PARAM_USER) == null){
			
			String code = httpServletRequest.getParameter(PARAM_CODE);
			// code为空，则跳至登录页面，获取code
			if (StringUtils.isEmpty(code)){
				String backUrl = httpServletRequest.getParameter(PARAM_BACK_URL);
				responseSendRedirect(httpServletResponse, request_token_url + "?" 
						+ "client_id=" + client_id 
						+ "&response_type=" + RESPONSE_TYPE 
						+ "&redirect_uri=" + URLEncoder.encode(callback_url, "UTF-8")
						+ "&state=" + backUrl
						+ "&scope=" + SCOPE_USER);
				return;
			} else {
				// 请求页面
				String backUrl = httpServletRequest.getParameter(STATE);
				backUrl = new String(new BASE64Decoder().decodeBuffer(backUrl));
				// 根据code获取accesstoken
				String accessResult = getAccessToken(code);
				JSONObject accessObject = JSONObject.fromObject(accessResult);
				// 正式令牌
				String access_token = accessObject.optString(PARAM_ACCESS_TOKEN);
				// 令牌类型
				String token_type = accessObject.optString(PARAM_TOKEN_TYPE);
				// 获取令牌成功
				if (StringUtils.isNotEmpty(access_token) && StringUtils.isNotEmpty(token_type)){
					// 根据令牌和令牌类型去查找用户信息
					String userResult = getUserInfo(access_token, token_type);
					JSONObject userObject = JSONObject.fromObject(userResult);
					if (userObject.get(PARAM_ERROR) != null){
						// 获取用户信息成功，将结果存入session
						String token = userObject.optString("result");
						Map<String, String> param = new HashMap<String, String>(2);
						param.put(SessionAttributeType.TOKEN, token);
						// 获取用户角色
						UserService userService = (UserService) SpringContext.getBean("userservice");
						String role = userService.queryRoleByUser(token);
						if (StringUtils.isEmpty(role)){
							// 没有角色跳回请求者页面
							responseSendRedirect(httpServletResponse, backUrl);
							return;
						}
						param.put(SessionAttributeType.ROLE, role);
						session.setAttribute(SessionAttributeType.PARAM_USER, param);
					} else {
						// 不成功跳回请求者页面
						responseSendRedirect(httpServletResponse, backUrl);
						return;
					}
				} else {
					// 获取令牌失败，跳回请求者页面
					responseSendRedirect(httpServletResponse, backUrl);
					return;
				}
			}
		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
		Properties prop = PropertiesUtil.getPropertiesBySrc("oauth-config.properties");
		client_id = prop.getProperty("client_id");
		client_secert = prop.getProperty("client_secret");
		request_token_url = prop.getProperty("request_token_url");
		access_token_url = prop.getProperty("access_token_url");
		user_info_url = prop.getProperty("user_info_url");
		callback_url = prop.getProperty("callback_url");
	}
	
	private void responseSendRedirect(HttpServletResponse response, String url) throws IOException{
		response.sendRedirect(url);
	}
	
	/**
	 * 
	 * @Title: getAccessToken 
	 * @Description: 根据code获取accessToken正式令牌
	 * @param code
	 * @return String
	 * @throws
	 */
	private String getAccessToken(String code){
		// 定义参数
		Map<String, String> param = new HashMap<String, String>();
		param.put("grant_type", GRANT_TYPE);
		param.put("client_id", client_id);
		param.put("client_secret", client_secert);
		param.put("code", code);
		param.put("redirect_uri", callback_url);
		String str = HttpClientUtil.post(access_token_url, param, null);
		return str;
	}
	
	/**
	 * 
	 * @Title: getUserInfo 
	 * @Description: 获取用户信息 
	 * @param @param accessToken
	 * @param @param tokenType
	 * @return String
	 * @throws
	 */
	private String getUserInfo(String accessToken, String tokenType){
		// 定义参数
		Map<String, String> param = new HashMap<String, String>();
		param.put("jsonrpc", JSONRPC_VERSION);
		param.put("method", METHOD);
		param.put("params", accessToken);
		param.put("id", String.valueOf(System.currentTimeMillis()));
		// 定义header
		Map<String, String> header = new HashMap<String, String>();
		header.put("Authorization", StringUtil.uppFstChar(tokenType) + " " + new BASE64Encoder().encode(accessToken.getBytes()));
		String str = HttpClientUtil.post(user_info_url, param, header);
		return str;
	}

}
