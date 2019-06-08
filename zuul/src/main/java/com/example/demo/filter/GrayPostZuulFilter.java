package com.example.demo.filter;

import com.example.gray.core.CoreHeaderInterceptor;
import com.netflix.zuul.ZuulFilter;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

/**
 * @author goofly
 * @E-mail 709233178@qq.com
 * @date 2019/1/21
 */

/**
 * @author goofly
 * @E-mail 709233178@qq.com
 * @date 2019/1/21
 */
public class GrayPostZuulFilter extends ZuulFilter {
	@Override
	public String filterType() {
		return FilterConstants.POST_TYPE;
	}

	@Override
	public int filterOrder() {
		return 0;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {
		CoreHeaderInterceptor.shutdownHystrixRequestContext();
		return null;
	}
}
