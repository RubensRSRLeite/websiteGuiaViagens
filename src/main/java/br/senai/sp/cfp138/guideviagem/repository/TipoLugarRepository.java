package br.senai.sp.cfp138.guideviagem.repository;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import br.senai.sp.cfp138.guideviagem.model.Lugares;
import br.senai.sp.cfp138.guideviagem.model.TipoLugar;
public interface TipoLugarRepository extends PagingAndSortingRepository<TipoLugar, Long> {
	
	@Query("SELECT t FROM TipoLugar t WHERE t.localizacao LIKE %:s% OR t.palavraChave LIKE %:s% OR t.proposito LIKE %:s%")
	public List<TipoLugar> buscar(@Param("s") String buscar);
	
	public List<TipoLugar> findAllByOrderByLocalizacaoAsc();
	
}
