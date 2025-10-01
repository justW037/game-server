package com.example.demo.common.utils;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JsonUtil {
	public static ObjectMapper objectMapper = new ObjectMapper()
			.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

	public JsonUtil(ObjectMapper objectMapper) {
		JsonUtil.objectMapper = objectMapper;
	}

	public static <T> T fromJson(String json, Class<T> classOfT) throws JsonProcessingException {
		return objectMapper.readValue(json, classOfT);
	}

	public static <T> T fromJson(String json, TypeReference<T> typeOfT) throws JsonProcessingException {
		return objectMapper.readValue(json, typeOfT);
	}

	public static <T> String toJson(T t) {
		return toJson(t, false);
	}

	public static <T> String toJson(T t, boolean pretty) {
		try {
			return pretty ? objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(t)
					: objectMapper.writeValueAsString(t);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static class JsonBuilder {
		private Map<String, Object> values = new HashMap<String, Object>();

		public JsonBuilder put(String name, String value) {
			values.put(name, value);
			return this;
		}

		public JsonBuilder put(String name, int value) {
			values.put(name, value);
			return this;
		}

		public JsonBuilder put(String name, long value) {
			values.put(name, value);
			return this;
		}

		public JsonBuilder put(String name, Object value) {
			values.put(name, value);
			return this;
		}

		public Map<String, Object> getValues() {
			return values;
		}

		public String build() {
			return JsonUtil.toJson(values);
		}

		public static JsonBuilder builder() {
			return new JsonBuilder();
		}
	}
}
