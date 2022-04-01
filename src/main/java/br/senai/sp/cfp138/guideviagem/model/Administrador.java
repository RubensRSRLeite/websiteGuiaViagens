package br.senai.sp.cfp138.guideviagem.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import br.senai.sp.cfp138.guideviagem.util.HashUtil;
import lombok.Data;


@Data	//cria classe com getters and setters 
@Entity	//mapeia entidade jpa
public class Administrador {

	
	// gerando variaveis
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotEmpty
	private String nome;
	//torna a coluna email com indice unico
	@NotEmpty
	@Column(unique = true)
	@Email
	private String email;
	@NotEmpty
	private String senha;
	
	//metodo set que aplica o hash na senha
	public void setSenha(String senha) {
		this.senha = HashUtil.hash(senha);
	}
	
	//seta o hash na senha
	public void setSenhaComHash(String hash) {
		this.senha = hash;
		
		
	}
	
	
}
