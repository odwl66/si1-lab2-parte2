package models;

import models.recomendaepisodio.RecomendadorDeEpisodio;
import models.recomendaepisodio.RecomendadorAntigo;
import models.recomendaepisodio.RecomendadorAntigoEspecial;
import models.recomendaepisodio.RecomendadorRecente;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

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
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="RECOMENDADOR")
	private RecomendadorDeEpisodio recomendadorDeEpisodio;
	
	public Serie(){
		this.temporadas = new ArrayList<Temporada>();
	}
	
	public Serie(String nome){
		this();
		this.nome=nome;
		this.recomendadorDeEpisodio = new RecomendadorRecente();
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
	
	public int getTemporadasTotal() {
		return temporadas.size();
	}
	
	public Temporada getUltimaTemporada() throws Exception {
		if (getTemporadasTotal() == 0) {
			throw new Exception("Lista de Temporadas vazia! Nome da serie: " + this.getNome());
		}
		return temporadas.get(temporadas.size()-1);
	}

	public RecomendadorDeEpisodio getRecomendadorDeEpisodio(){
		return recomendadorDeEpisodio;
	}

	public void setRecomendadorDeEpisodio(String recomendador){
		if (recomendador.equals("antigoEspecial")){
			this.recomendadorDeEpisodio = new RecomendadorAntigoEspecial();
		} else if (recomendador.equals("antigo")){
			this.recomendadorDeEpisodio = new RecomendadorAntigo();
		} else {
			this.recomendadorDeEpisodio = new RecomendadorRecente();
		}
	}

	public Episodio getProximoEpisodio(){
		return recomendadorDeEpisodio.getProximoEpisodio(this);
	}
}
