package com.example.demo.filter;
import com.example.demo.vo.GrayUserConfigProp;
import com.example.gray.core.CoreHeaderInterceptor;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import java.util.List;

/**
 * @author goofly
 * @E-mail 709233178@qq.com
 * @date 2019/1/21
 */
@Slf4j
public class GrayFilter extends ZuulFilter {

	@Autowired
	private GrayUserConfigProp grayUserConfigProp;

	private static final String HEADER_TOKEN = "token";
	private static final Logger logger = LoggerFactory.getLogger(GrayFilter.class);

	@Override
	public String filterType() {
		return FilterConstants.PRE_TYPE;
	}

	@Override
	public int filterOrder() {
		return 1000;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		String token = ctx.getRequest().getHeader(HEADER_TOKEN);

		String userId = token;
		log.info("======>userId:{}", userId);

		List<String> userIdList = grayUserConfigProp.getUserIdList();
		String version = userIdList.contains(userId) ? grayUserConfigProp.getVersion() : null;
		logger.info("=====>userId:{},version:{}", userId, version);

		// zuul本身调用微服务
		CoreHeaderInterceptor.initHystrixRequestContext(version);
		// 传递给后续微服务
		ctx.addZuulRequestHeader(CoreHeaderInterceptor.HEADER_VERSION, version);

		return null;
	}
}
