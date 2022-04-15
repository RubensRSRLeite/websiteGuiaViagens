package br.senai.sp.cfp138.guideviagem.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.senai.sp.cfp138.guideviagem.model.Administrador;
import br.senai.sp.cfp138.guideviagem.model.TipoLugar;
import br.senai.sp.cfp138.guideviagem.repository.TipoLugarRepository;

@Controller
public class TipoLugarController {
	
	@Autowired
	private TipoLugarRepository lugRep;
	
	//retorna para meu lugar.html
	@RequestMapping("LugarCadTipo")
	public String tipoDeLugar() {
		return"cadTipoLugar";
	}
	
	@RequestMapping( value = "salvaLugar", method = RequestMethod.POST)
	public String salvaLugar(@Valid TipoLugar lugar, BindingResult bindRes, RedirectAttributes attr ) {
		System.out.println("\n\n\n entrou em salvaLugar \n\n\n");
		if(bindRes.hasErrors()) {
			attr.addFlashAttribute("mensagemErro", "Verifique os campos...");
			System.out.println("caiu em um erro");
			return"redirect:LugarCadTipo";
		} else {
			
			try {

				lugRep.save(lugar);
				System.out.println("\n\n\n tentou \n\n\n");
				attr.addFlashAttribute("mensagemSucesso", "Lugar cadastrado com sucesso. ID: "+lugar.getId());
				
				
			} catch (Exception e) {
				attr.addFlashAttribute("mensagemErro", "Houve um erro ao cadastrar"+e.getMessage());
				System.out.println("\n\n\n falhou \n\n\n");
			}
			System.out.println("retornou");
			return "forward:LugarCadTipo";
			
		}
		
		
	}
	
	//retorna para listaTipoLugar.html
	@RequestMapping("tipoLugarList/{page}")
	public String listaDeLugares( Model model, @PathVariable("page") int page ) {
		System.out.println("\n\n lista de lugar \n\n");
		//logica para a paginação
		PageRequest pr = PageRequest.of(page-1, 10, Sort.by(Sort.Direction.ASC, "palavraChave"));
		
		//encontra o lugar pelo repository
		Page<TipoLugar> pagina =  lugRep.findAll(pr);
		
		model.addAttribute("tipoLugares", pagina.getContent());
	
		//pegar total de paginas
		int totalPages = pagina.getTotalPages();
		
		//numero da pagina
		List<Integer> numPag =  new ArrayList<Integer>();
		
		//preenche pagina
		for (int i = 1; i <= totalPages; i++) {
			// add pagina ao list
			numPag.add(i);
		}
		
		// add na model
		model.addAttribute("numPag", numPag);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("pagAtual", page);
		System.out.println("\n\n retornou \n\n");
		return "listaTipoLugar";
	}
	
	@RequestMapping("alterarLug")
	public String alterarLug(Model model, Long id) {
		TipoLugar lugar = lugRep.findById(id).get();
		model.addAttribute("lugar", lugar);
		return "forward:LugarCadTipo";
	}
	
	@RequestMapping("excluirLug")
	public String excluirLug(Long id) {
		lugRep.deleteById(id);
		return "redirect:tipoLugarList/1";
	}
	
	@RequestMapping("busca")
	public String buscar( Model model, String buscar ) {
		model.addAttribute("lugares", lugRep.buscar(buscar));
		return "listaTipoLugar";
	}
	
}
