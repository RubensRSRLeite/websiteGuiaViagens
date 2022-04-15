package br.senai.sp.cfp138.guideviagem.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class Lugares {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	@Column(columnDefinition = "TEXT")
	private String descricao;
	private String cep;
	private String logradouro;
	private String complemento;
	private String bairro;
	private String cidade;
	private String estado;
	private String fotos;
	private boolean acessibilidade;
	private boolean transporte;
	private boolean saude;
	private boolean seguranca;
	private boolean entreterimento;
	@ManyToOne
	private TipoLugar tipolugar;
	
	//retorna as fotos como array string
	
	public String[] verFotos() {
		return fotos.split(";");
	}

}
