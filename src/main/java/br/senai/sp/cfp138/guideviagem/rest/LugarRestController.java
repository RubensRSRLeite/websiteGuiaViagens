package br.senai.sp.cfp138.guideviagem.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.senai.sp.cfp138.guideviagem.model.Lugares;
import br.senai.sp.cfp138.guideviagem.model.TipoLugar;
import br.senai.sp.cfp138.guideviagem.repository.LugarRepository;
import br.senai.sp.cfp138.guideviagem.repository.TipoLugarRepository;


@RestController
@RequestMapping("/api/lugar")
public class LugarRestController {
	
	@Autowired
	private LugarRepository repository;
	
	@Autowired
	private TipoLugarRepository repTipo;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Iterable<Lugares> getLugar() {
		return repository.findAll();
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<Lugares> getLugares(@PathVariable("id") Long idLug) {
		//tenta buscar restaurante no repository
		Optional<Lugares> optional = repository.findById(idLug);
		if(optional.isPresent()) {
			return ResponseEntity.ok(optional.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	// pega lugar pelo tipo
	
	/*@RequestMapping(value = "/tipo", method = RequestMethod.GET)
	public Iterable<TipoLugar> getTipoLugar() {
		return repTipo.findAll();
	}
	
	@RequestMapping(value="/tipo/{id}", method = RequestMethod.GET)
	public ResponseEntity<TipoLugar> getTipoLugar(@PathVariable("id") Long id) {
		Optional<TipoLugar> optional =  repTipo.findById(id);
		if(optional.isPresent()) {
			return ResponseEntity.ok(optional.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}*/
	
	/*@RequestMapping(value = "/tipo/{id", method = RequestMethod.GET)
	public Iterable<Lugares> getLugarByTipo(@PathVariable("id") Long id){
		return repository.findByTipoId(id);
	}*/
	
}
