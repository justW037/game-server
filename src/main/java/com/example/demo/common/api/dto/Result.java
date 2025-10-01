package com.example.demo.common.api.dto;

import java.io.Serializable;

import org.springframework.http.ResponseEntity;

import com.example.demo.common.constants.Message;
import com.example.demo.common.constants.ResultCode;
import com.example.demo.common.utils.JsonUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
// @JsonInclude(content = Include.NON_NULL)
public class Result implements Serializable {
	private static final long serialVersionUID = 1L;
	private int status;
	private String message;
	private Object data;

	public static Result as(int code, String message, Object data) {
		return new Result()
				.setStatus(code)
				.setMessage(message)
				.setData(data);
	}

	public static Result success(Object data, String message) {
		return new Result()
				.setStatus(ResultCode.OK)
				.setMessage(message)
				.setData(data);
	}

	public static Result success() {
		return as(ResultCode.OK, Message.OK, null);
	}

	public static Result success(String message) {
		return new Result()
				.setStatus(ResultCode.OK)
				.setMessage(message)
				.setData(null);
	}

	public static Result successData(Object data) {
		return new Result()
				.setStatus(ResultCode.OK)
				.setMessage(Message.OK)
				.setData(data);
	}

	public static Result error(int code) {
		return as(code, Message.ERROR, null);
	}

	public static Result error(int code, String message) {
		return as(code, message, null);
	}

	private int codeToHttpStatus() {
		String tmp = String.valueOf(status);
		if (tmp.length() > 3) {
			return Integer.parseInt(tmp.substring(0, 3));
		} else
			return status;
	}

	public ResponseEntity<Result> toResponseEntity() {
		return ResponseEntity
				.status(codeToHttpStatus())
				.body(this);
	}

	@Override
	public String toString() {
		return JsonUtil.toJson(this);
	}
}
