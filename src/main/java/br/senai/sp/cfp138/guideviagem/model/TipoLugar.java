package br.senai.sp.cfp138.guideviagem.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
@Entity
public class TipoLugar {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	/*
	 * @NotEmpty private String nome;
	 */
	@NotEmpty
	private String localizacao;
	@NotEmpty
	private String palavraChave;
	/*
	 * @NotEmpty private String descricao;
	 */
	@NotEmpty
	private String proposito;
	
}
