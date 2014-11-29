package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity(name="Serie")
public class Serie {
	@Id
	@GeneratedValue
	private Long id;
	@Column
	private String nome;
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="TEMP")
	private List<Temporada> temporadas;
	@Column
	private boolean acompanhando;
	
	public Serie(){
		this.temporadas = new ArrayList<Temporada>(); 
	}
	
	public Serie(String nome){
		this();
		this.nome=nome;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public List<Temporada> getTemporadas() {
		return temporadas;
	}
	public void addTemporada(Temporada temporada) {
		this.temporadas.add(temporada);
	}
	public boolean isAcompanhada() {
		return acompanhando;
	}

	public void setAssistida(boolean acompanhando) {
		this.acompanhando = acompanhando;
	}
}
