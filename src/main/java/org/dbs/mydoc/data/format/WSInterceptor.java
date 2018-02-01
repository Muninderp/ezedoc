
package org.dbs.mydoc.data.format;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


@Component
public class WSInterceptor extends HandlerInterceptorAdapter{

	private static final Logger logger = LogManager.getLogger(WSInterceptor.class);
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		WSMetaData metaData = new WSMetaData();
		metaData.setClientIp(request.getRemoteAddr());
		metaData.setClientHost(request.getRemoteHost());
		metaData.setClientPort(String.valueOf(request.getRemotePort()));
		
		request.setAttribute("request_metadata", metaData);
		
		Date currDate = new Date();
		
		logger.info(" TimeStamp " + currDate +  ", IP address " + request.getRemoteAddr() + ", port " + request.getRemotePort() + ", URL Requested " + request.getRequestURI());
		
		
		return true;
	}
}
