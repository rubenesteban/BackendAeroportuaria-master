package com.backend.aeroportuaria.dto;

public class ResponseCode {

	private Integer code;
	private String mensaje;

	public ResponseCode(Integer code, String mensaje) {
		this.code = code;
		this.mensaje = mensaje;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
}
