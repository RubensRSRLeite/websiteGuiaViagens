package br.senai.sp.cfp138.guideviagem.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.senai.sp.cfp138.guideviagem.model.Lugares;
import br.senai.sp.cfp138.guideviagem.model.TipoLugar;
import br.senai.sp.cfp138.guideviagem.repository.LugarRepository;
import br.senai.sp.cfp138.guideviagem.repository.TipoLugarRepository;
import br.senai.sp.cfp138.guideviagem.util.FirebaseUtil;

@Controller
public class LugarController {
	
	@Autowired
	private TipoLugarRepository repTipo;
	
	@Autowired
	private LugarRepository repLug;
	
	@Autowired
	private FirebaseUtil fireBaseUtil;
	
	@RequestMapping("formLugar")
	public String form(Model model){
		model.addAttribute("tipos", repTipo.findAllByOrderByLocalizacaoAsc());
	return "lugarCad";
	}
	
	@RequestMapping(value = "svFormLugar", method = RequestMethod.POST)
	public String salvaFormLugar(@Valid	Lugares lugares, BindingResult bindRes, RedirectAttributes attr, @RequestParam("fileFotos") MultipartFile[] fileFotos) {
		System.out.println("\n\n\n entrou em salvaLugar \n\n\n");
		//string para armazenar as urls
		String fotos = lugares.getFotos();
		for(MultipartFile arquivo : fileFotos) {
			if(arquivo.getOriginalFilename().isEmpty()) {
				//va para a proximo arquivo
				continue;
			}
			
			try {
				fotos += fireBaseUtil.upload(arquivo)+";";
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		
		/*if(bindRes.hasErrors()) {
			attr.addFlashAttribute("mensagemErro", "Verifique os campos...");
			System.out.println("caiu em um erro");
			return"redirect:LugarCadTipo";
		} else {
			try {
				*/
		
				System.out.println("\n\n\n tentou \n\n\n");
				System.out.println(fileFotos.length);
				
				lugares.setFotos(fotos);
				repLug.save(lugares);
				/*attr.addFlashAttribute("mensagemSucesso", "Lugar cadastrado com sucesso. ID: "+lugares.getId());
			} catch (Exception e) {
				attr.addFlashAttribute("mensagemErro", "Houve um erro ao cadastrar"+e.getMessage());
				System.out.println("\n\n\n falhou \n\n\n");
			}*/
				
			System.out.println("retornou");
			return "forward:formLugar";
		//}
	}
	
	@RequestMapping("lugarLista/{page}")
	public String listaDeLugares( Model model, @PathVariable("page") int page ) {
		System.out.println("\n\n lista de lugar \n\n");
		//logica para a paginação
		PageRequest pr = PageRequest.of(page-1, 10, Sort.by(Sort.Direction.ASC, "nome"));
		
		//encontra o lugar pelo repository
		Page<Lugares> pagina =  repLug.findAll(pr);
		
		model.addAttribute("lug", pagina.getContent());
	
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
		return "lugarLista";
	}
	
	@RequestMapping("alterarLugar")
	public String alterarLugar(Long id, Model model ) {
		Lugares lugar = repLug.findById(id).get();
		model.addAttribute("lugar", lugar);
		return "forward:formLugar";
	}
	
	@RequestMapping("deletarLugar")
	public String deletarLugar(Long id) {
		repLug.deleteById(id);
		return "redirect:lugarLista/1";
	}
	
	@RequestMapping("excluirFoto")
	public String excluirFoto(Long idLug, int numFoto, Model model) {
		// buscar lugar no banco
		Lugares lug = repLug.findById(idLug).get();
		// busca url da foto
		String urlFoto = lug.verFotos()[numFoto];
		//deleta foto
		fireBaseUtil.deletar(urlFoto);
		//remove url da foto
		lug.setFotos(lug.getFotos().replace(urlFoto+";", ""));
		//salva no bd
		repLug.save(lug);
		//coloca o lug na Model
		model.addAttribute("lugar", lug);
		return "forward:formLugar";
	}
}