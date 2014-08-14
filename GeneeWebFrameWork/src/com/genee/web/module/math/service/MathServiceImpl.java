package com.genee.web.module.math.service;

import com.genee.web.framework.core.base.service.BaseService;


public class MathServiceImpl extends BaseService implements MathService {

	@Override
	public int add(int a, int b) {
		return a + b;
	}

	@Override
	public int subtract(int a, int b) {
		return a - b;
	}

}
