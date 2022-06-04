package com.flyme.core.groovy;

import java.util.Map;

import org.springframework.scripting.ScriptEvaluator;
import org.springframework.scripting.ScriptSource;
import org.springframework.scripting.groovy.GroovyScriptEvaluator;
import org.springframework.scripting.support.StaticScriptSource;

import com.flyme.common.util.ObjectUtils;

public class SpringScript {
	public void test() {
		ScriptEvaluator scriptEvaluator = new GroovyScriptEvaluator();
		
		String content = "return Math.ceil(6/2);";
		ScriptSource source = new StaticScriptSource(content);
		Map<String,Object> map=ObjectUtils.getHashMap();
		map.put("建筑面积",100);
		map.put("楼层号",2);
		System.out.println(scriptEvaluator.evaluate(source,map));
	}
	public static void main(String[] args) {
		SpringScript script=new SpringScript();
		script.test();
	}
}
