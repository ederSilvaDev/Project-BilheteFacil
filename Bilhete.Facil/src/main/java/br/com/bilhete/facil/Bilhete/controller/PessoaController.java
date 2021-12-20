package br.com.bilhete.facil.Bilhete.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.bilhete.facil.Bilhete.model.Pessoa;
import br.com.bilhete.facil.Bilhete.model.Telefone;
import br.com.bilhete.facil.Bilhete.repository.PessoaRepository;
import br.com.bilhete.facil.Bilhete.repository.TelefoneRepository;

@Controller
public class PessoaController {

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private TelefoneRepository telefoneRepository;

	@RequestMapping(method = RequestMethod.GET, value = "/cadastropessoa")
	public ModelAndView inicio() {
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
		modelAndView.addObject("pessoaOj", new Pessoa());
		Iterable<Pessoa> pessoaIt = pessoaRepository.findAll();
		modelAndView.addObject("pessoas", pessoaIt);
		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.POST, value = "**/salvarpessoa")
	public ModelAndView salvar(@Valid Pessoa pessoa, BindingResult bindingResult) {

		//CARREGAR OS NUMEROS DE TELEFONES PARA O OBJETO PESSSOA, ISSO É NECESSÁRIO DEVIDO AO ERRO QUE 
		//OCORRE AO TENTAR SALVAR UM REGISTRO EM EDIÇÃO
		pessoa.setTelefones(telefoneRepository.getTelefones(pessoa.getId()));
		
		  if (bindingResult.hasErrors()) {
			  ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
			  Iterable<Pessoa> pessoasIt = pessoaRepository.findAll();
			  modelAndView.addObject("pessoas", pessoasIt);
			  modelAndView.addObject("pessoaOj", pessoa); /*DA O ERRO E CONTINUA NA TELA COM OS OBJETOS PREENCHIDOS*/
		  
			  //MOSTRAR A VALIDAÇÃO NA TELA 
			  List<String> msg = new ArrayList<String>();
			  for (ObjectError objectError : bindingResult.getAllErrors()){ /*QUAIS SÃO OS ERROS*/
			  msg.add(objectError.getDefaultMessage()); /*vem das anotações @NotEmpty*/
			  
		  }
		  
		  modelAndView.addObject("msg", msg);
		  return modelAndView;
		  
		  }
		 

		pessoaRepository.save(pessoa);

		ModelAndView andView = new ModelAndView("cadastro/cadastropessoa");
		Iterable<Pessoa> pessoasIt = pessoaRepository.findAll();
		andView.addObject("pessoas", pessoasIt);
		andView.addObject("pessoaOj", new Pessoa());

		return andView;

	}

	@RequestMapping(method = RequestMethod.GET, value = "/listatodos")
	public ModelAndView listaPessoas() {

		ModelAndView andView = new ModelAndView("cadastro/cadastropessoa");
		Iterable<Pessoa> pessoasIt = pessoaRepository.findAll();
		andView.addObject("pessoas", pessoasIt);
		andView.addObject("pessoaOj", new Pessoa());
		return andView;

	}

	@GetMapping("/editarpessoa/{idpessoa}")
	public ModelAndView editar(@PathVariable("idpessoa") Long idpessoa) {

		Optional<Pessoa> pessoa = pessoaRepository.findById(idpessoa);
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
		modelAndView.addObject("pessoaOj", pessoa.get());
		return modelAndView;

	}

	@GetMapping("/removerpessoa/{idpessoa}")
	public ModelAndView exlcuir(@PathVariable("idpessoa") Long idpessoa) {

		pessoaRepository.deleteById(idpessoa);

		ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
		modelAndView.addObject("pessoa", pessoaRepository.findAll());
		modelAndView.addObject("pessoaOj", new Pessoa());
		return modelAndView;

	}

	/*
	 * https://www.projetojavaweb.com/certificado-aluno/plataforma-curso/aulaatual/
	 * 473056094/idcurso/1/idvideoaula/689 - 9:00
	 */
	@PostMapping("**/pequisarpessoa") /* INTERCEPITAR A URL E PEGAR O VALOR DA VARIAVEL NOMEPESQUISA */
	public ModelAndView pesquisar(@RequestParam("nomepesquisa") String nomepesquisa, @RequestParam("pesqsexo") String pesqsexo) {		
		
		
		List<Pessoa> pessoas = new ArrayList<Pessoa>();

		if (pesqsexo != null && !pesqsexo.isEmpty()) {
			pessoas = pessoaRepository.findPessoaByNameSexo(nomepesquisa, pesqsexo);
		} else {
			pessoas = pessoaRepository.findPessoaByName(nomepesquisa);
		} 
		
		/* adicionar o objeto para a variavel pessoas */ /*
		 * vai consultar todos os registro de acordo com a valor da
		 * variavel informanda
		 */
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa"); /* CONSULTA E RETONA NA MESMA TELA DE CADASTRO */
		modelAndView.addObject("pessoas", pessoas);
		modelAndView.addObject("pessoaOj", new Pessoa()); /* como é mesma tela ele esta mando um objeto limpo */
		return modelAndView;

	}

	@GetMapping("/telefones/{idpessoa}") /* METODO QUE CARREGA OS DADOS DA PESSOA VINDO DO LINK GERADO */
	public ModelAndView telefones(@PathVariable("idpessoa") Long idpessoa) {
		Optional<Pessoa> pessoa = pessoaRepository.findById(idpessoa);
		
		ModelAndView modelAndView = new ModelAndView("cadastro/telefones");
		modelAndView.addObject("pessoaOj", pessoa.get());
		modelAndView.addObject("msg", new ArrayList<String>());
		/*
		 * https://www.projetojavaweb.com/certificado-aluno/plataforma-curso/aulaatual/
		 * 473059315/idcurso/1/idvideoaula/694 - 07:30
		 */
		modelAndView.addObject("telefones", telefoneRepository.getTelefones(idpessoa));
		return modelAndView;

	}

	/*
	 * https://www.projetojavaweb.com/certificado-aluno/plataforma-curso/aulaatual/
	 * 473058594/idcurso/1/idvideoaula/693 - 19:05
	 */
	@PostMapping("**/addfonePessoa/{pessoaid}") /*METODO DE ADICIONA NUMERO E TIPO DE TELEFONE A PESSOA*/
	public ModelAndView addfonePessoa(Telefone telefone, @PathVariable("pessoaid") Long pessoaid) {

		Pessoa pessoa = pessoaRepository.findById(pessoaid).get();
		
		if(telefone != null && telefone.getNumero().isEmpty()){
			
			ModelAndView modelAndView = new ModelAndView("cadastro/telefones");
			modelAndView.addObject("pessoaOj", pessoa);
			modelAndView.addObject("telefones", telefoneRepository.getTelefones(pessoaid));
			
			List<String> msg = new ArrayList<String>();
			if (telefone.getNumero().isEmpty()) {
				msg.add("Numero deve ser informado");
			}
						
			modelAndView.addObject("msg", msg);
			
			return modelAndView;
			
		}
		
		ModelAndView modelAndView = new ModelAndView("cadastro/telefones");

		telefone.setPessoa(pessoa);
		telefoneRepository.save(telefone);

		modelAndView.addObject("pessoaOj", pessoa);
		modelAndView.addObject("telefones", telefoneRepository.getTelefones(pessoaid));
		return modelAndView;
	}

	/*
	 * https://www.projetojavaweb.com/certificado-aluno/plataforma-curso/aulaatual/
	 * 473059963/idcurso/1/idvideoaula/695 - 06:15
	 */
	@GetMapping("/removertelefone/{idtelefone}")
	public ModelAndView removertelefone(@PathVariable("idtelefone") Long idtelefone) {
		/* CARREGAR O OBJETO TELEFONE, DENTRO DO TELEFONE TEM UMA PESSOA */
		Pessoa pessoa = telefoneRepository.findById(idtelefone).get().getPessoa();
		telefoneRepository.deleteById(idtelefone); /* DELETA O TELEFONE RECEBIDO */

		ModelAndView modelAndView = new ModelAndView("cadastro/telefones");/* RETORNA PRA MESMA TELA */
		modelAndView.addObject("pessoaOj", pessoa);
		modelAndView.addObject("telefones", telefoneRepository
				.getTelefones(pessoa.getId())); /* CARREGA OS DADOS MENOS O TELEFONE QUE FOI REMOVIDO */
		return modelAndView;

	}
}
