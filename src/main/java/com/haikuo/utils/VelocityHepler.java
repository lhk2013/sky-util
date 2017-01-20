package com.haikuo.utils;

import java.io.StringWriter;
import java.util.Map;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

public class VelocityHepler {

	public static final VelocityHepler single = new VelocityHepler();

	private VelocityHepler() {
	}

	public String merge(String model, Map<String, Object> data) {
		data.put("tool", YoucheTool.getInstance());
		Template template = engine.getTemplate(model);
		if (template != null) {
			StringWriter getwriter = new StringWriter();
			VelocityContext context = new VelocityContext(data);
			template.merge(context, getwriter);
			return getwriter.toString();
		}
		return "";
	}

	private static VelocityEngine engine = new VelocityEngine();

	public static final void init(String path) {
		Properties properties = new Properties();
		properties.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, path);
		properties.setProperty(Velocity.ENCODING_DEFAULT, "UTF-8");
		properties.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
		properties.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");
		engine.init(properties);
	}
}
