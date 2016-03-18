/**
 * 
 */
package com.common.util.conversion;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;


/**
 * 转换器适配类(具体的转换器实现此类)
 * @author fansth
 *
 */
public abstract class ConverterAdapter<S, T> implements Converter<S, T> {
	

	@Autowired
	private ConverterService converterService;
	
	
	@SuppressWarnings("unused")
	@PostConstruct
	private void regstierConverter(){
		converterService.registerConverter(this);
	}
	
}
