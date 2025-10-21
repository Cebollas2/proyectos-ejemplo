package com.magnolia.cione.processors;

import java.util.Map;

import javax.jcr.Node;

import info.magnolia.module.form.processors.AbstractFormProcessor;
import info.magnolia.module.form.processors.FormProcessorFailedException;

public class ChangePwdProcessor extends AbstractFormProcessor {

	@Override
	protected void internalProcess(Node content, Map<String, Object> parameters) throws FormProcessorFailedException {
		// TODO Auto-generated method stub
		
		System.out.println("hola mundo");
	}

}
