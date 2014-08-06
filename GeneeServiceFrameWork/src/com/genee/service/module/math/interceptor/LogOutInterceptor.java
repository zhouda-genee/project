package com.genee.service.module.math.interceptor;

import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageContentsList;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.transport.http.AbstractHTTPDestination;

import com.genee.service.framework.utils.datautil.DateUtil;
import com.genee.service.framework.utils.json.JsonUtil;

public class LogOutInterceptor extends AbstractPhaseInterceptor<Message> {
	
	private transient final static Logger logger = Logger.getLogger("genee");

	public LogOutInterceptor(String phase) {
		super(phase);
	}
	
	public LogOutInterceptor() {
		super(Phase.SEND);
	}

	@Override
	public void handleMessage(Message message) throws Fault {
		
		HttpServletResponse response = (HttpServletResponse)message.get(AbstractHTTPDestination.HTTP_RESPONSE);
		
		StringBuilder sb = new StringBuilder();
		sb.append("【 线程号：" + Thread.currentThread().getId() + "(" + Thread.currentThread().getName() + ")" + "\t结束时间：" + DateUtil.currentDate() + " " + DateUtil.currentTime() + " 】").append("\n");
		sb.append("\t").append("返回状态：" + response.getStatus());
		
		String msg = "";
		List<Object> objs = MessageContentsList.getContentsList(message);
		for (Object obj : objs) {
			if (obj instanceof org.apache.cxf.jaxrs.impl.ResponseImpl){
				org.apache.cxf.jaxrs.impl.ResponseImpl result = (org.apache.cxf.jaxrs.impl.ResponseImpl)obj;
				msg = result.getEntity().toString();
			} else if (obj instanceof java.util.List) {
				java.util.List<?> result = (java.util.List<?>)obj;
				msg = JsonUtil.getJsonString4List(result);
			} else if (obj instanceof java.util.Map){
				java.util.Map<?, ?> result = (java.util.Map<?, ?>)obj;
				msg = JsonUtil.getJsonString4JavaPOJO(result);
			} else {
				throw new Fault("无该类型", logger);
			}
		}
		sb.append("\t").append("返回结果：" + msg);
		logger.info(sb.toString());
		
	}
}
