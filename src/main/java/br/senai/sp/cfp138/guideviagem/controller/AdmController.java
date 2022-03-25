package br.senai.sp.cfp138.guideviagem.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Admin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.senai.sp.cfp138.guideviagem.model.Administrador;
import br.senai.sp.cfp138.guideviagem.repository.AdminRepository;

@Controller
public class AdmController {
	
	//variavel para pesistencia dos dados
	@Autowired
	private AdminRepository admRep;

	@RequestMapping("cadAdmin")
	public String cadAdm() {
		System.out.println("entra no cadAdm");
		return"cadAdm";
	}
	
	@RequestMapping(value = "salvarAdmin", method = RequestMethod.POST)
	public String salvarAdm( @Valid Administrador admin, BindingResult result, RedirectAttributes attr) {
		System.out.println("entra no salvarAdm");
		if(result.hasErrors()) {
			attr.addFlashAttribute("mensagemErro", "Verifique os campos...");
			System.out.println("caiu em um erro");
			return"redirect:cadAdmin";
		}
		
		//not #strings.isEmpty(mensagemErro)
		
		try {
			//salva no bd entidade (sem id)
			admRep.save(admin);
			//adiciona id (apos o admRes.save(admin))
			//mensagem de sucesso
			attr.addFlashAttribute("mensagemSucesso", "Administrador cadastrado com sucesso. ID: "+admin.getId());
			
			
		} catch (Exception e) {
			attr.addFlashAttribute("mensagemErro", "Houve um erro ao cadastrar"+e.getMessage());
		}
		System.out.println("retornou");
		return"redirect:cadAdmin";
		
	}
	//request mapping para listaAdmin
	@RequestMapping("listaAdmin/{page}")
	public String listaAdmin(Model model, @PathVariable("page") int page) {
		//paegable(pageRequest) que informa parâmetros da class
		PageRequest pr = PageRequest.of(page-1, 1, Sort.by(Sort.Direction.ASC, "nome"));
		
		// cria page de administrador por parametros pelo repository
		Page<Administrador> pagina = admRep.findAll(pr);
		
		//addiciona model com lista com admins
		model.addAttribute("admins", pagina.getContent());
		
		//pega total de paginas
		int totalPages = pagina.getTotalPages();
		
		//cria list de inteiros para armazenar os numeros das paginas
		List<Integer> numPag = new ArrayList<Integer>();//faz alterações para alista aqui!!!
		
		//preencher pag
		for (int i = 0; i < totalPages; i++) {
			// add pagina ao list
			numPag.add(i);
		}
		//add na model
		model.addAttribute("numPag", numPag);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("pagAtual", page);
		//retorna para o html da lista
		return "listaAdm";
	}
	@RequestMapping("alterar")
	public String alterar(Model model, Long id) {
		Administrador administrador = admRep.findById(id).get();
		model.addAttribute("adm", administrador);
		return "forward:cadAdmin";
		
	}
	
	@RequestMapping("excluir")
	public String excluir(Long id) {
		admRep.deleteById(id);
		return "redirect:listaAdmin";
	}
	
}
