package br.senai.sp.cfp138.guideviagem.model;



import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class Erro {

	private HttpStatus statusCode;
	private String mensagem;
	private String exception;
	
	public Erro( HttpStatus statusCode, String mensagem, String exception ) {
		
	}
}
