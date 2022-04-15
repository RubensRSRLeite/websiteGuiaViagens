package br.senai.sp.cfp138.guideviagem.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.senai.sp.cfp138.guideviagem.model.Lugares;

public interface LugarRepository extends PagingAndSortingRepository<Lugares, Long> {

	
}
