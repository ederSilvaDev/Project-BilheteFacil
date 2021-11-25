package br.com.bilhete.facil.Bilhete.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Entity /*PARA QUE ESSE CLASSE VIRE UMA TABELA NO BANCO */
public class Pessoa implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	
	@NotNull(message="Campo [Nome] Nulo !")
	@NotEmpty(message="Campo [Nome] Vázio !")
	private String nome;
	
	
	@NotNull(message="Nulo ñ Permitido")
	@NotEmpty(message="Vazio ñ Permitido")
	private String sobrenome;
	
	@Min(value = 18, message = "Idade inválida")
	private String idade;
	private String sexo;
	
	@OneToMany(mappedBy = "pessoa")
	private List<Telefone> telefones;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSobrenome() {
		return sobrenome;
	}
	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}
	
	public String getIdade() {
		return idade;
	}
	
	public void setIdade(String idade) {
		this.idade = idade;
	}
	
	public String getSexo() {
		return sexo;
	}
	
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

}
