package com.genee.service.framework.core.interceptor;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.apache.log4j.Logger;

import com.genee.service.framework.utils.datautil.DateUtil;


public class LogInInterceptor extends AbstractPhaseInterceptor<Message> {
	
	private transient final static Logger logger = Logger.getLogger("genee");

	public LogInInterceptor(String phase) {
		super(phase);
	}
	
	public LogInInterceptor(){  
        super(Phase.RECEIVE);  
    }  

	@Override
	public void handleMessage(Message message) {
		
		HttpServletRequest request = (HttpServletRequest)message.get(AbstractHTTPDestination.HTTP_REQUEST);

		StringBuilder sb = new StringBuilder();
		
		sb.append("【 线程号：" + Thread.currentThread().getId() + "(" + Thread.currentThread().getName() + ")" + "\t地址：" + request.getRequestURL() + "\t开始时间：" + DateUtil.currentDate() + " " + DateUtil.currentTime() + " 】").append("\n");
		sb.append("\t").append("IP：" + request.getRemoteAddr()).append("\n");
		sb.append("\t").append("请求的字符集编码：" + request.getCharacterEncoding()).append("\n");
		
        Enumeration<String> e = request.getHeaderNames();  
        while(e.hasMoreElements()){  
            String str=e.nextElement();
            sb.append("\t").append(str + "：" + request.getHeader(str)).append("\n");
        }
        
        Enumeration<String> params = request.getParameterNames();
		sb.append("\t").append("请求参数列表：").append("\n");
		while(params.hasMoreElements()){
			String param = params.nextElement();
			sb.append("\t\t").append(param + "：").append(request.getParameter(param)).append("\n");
		}
        
		logger.info(sb.toString());
	}

}
