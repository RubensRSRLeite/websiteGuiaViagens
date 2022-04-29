package br.senai.sp.cfp138.guideviagem.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.senai.sp.cfp138.guideviagem.model.Usuario;

public interface UsuarioRepository extends PagingAndSortingRepository<Usuario, Long> {

}
