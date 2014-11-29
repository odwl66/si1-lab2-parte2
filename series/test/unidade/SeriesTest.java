package unidade;


import static org.fest.assertions.Assertions.assertThat;

import java.util.List;

import models.Episodio;
import models.Serie;
import models.Temporada;
import models.dao.GenericDAO;

import org.junit.Test;

import base.AbstractTest;

public class SeriesTest extends AbstractTest{
	Serie serie1 = new Serie("South Park");
	Serie serie2 = new Serie("Family Guy");
	Temporada temp1 = new Temporada(1, serie1);
	Temporada temp2 = new Temporada(1, serie2);
	Episodio epi1 = new Episodio("Volcano", temp1, 3);
	Episodio epi2 = new Episodio("Death has a Shadow", temp2, 1);
	GenericDAO dao = new GenericDAO();
	List<Serie> series;
	
	@Test
	public void deveIniciarSemSeries() {
		series = dao.findAllByClass(Serie.class);
		assertThat(series.size()==0).isTrue();
	}
	
	@Test
	public void deveAdicionarSerie() {
		dao.persist(serie1);
		series = dao.findAllByClass(Serie.class);
		assertThat(series.size()==1).isTrue();
		assertThat(series.get(0).getNome()).isEqualTo("South Park");
		dao.persist(serie2);
		series = dao.findAllByClass(Serie.class);
		assertThat(series.size()==2).isTrue();
		assertThat(series.get(1).getNome()).isEqualTo("Family Guy");
	}
	
	@Test
	public void deveRegistrarTemporada() {
		serie1.addTemporada(temp1);
		dao.persist(serie1);
		series = dao.findAllByClass(Serie.class);
		assertThat(series.get(0).getTemporadas().size()==1).isTrue();
		assertThat(series.get(0).getTemporadas().get(0).getSerie()).isEqualTo(serie1);
		assertThat(series.get(0).getTemporadas().get(0).getEpisodios().size()==0).isTrue();
	}
	
	@Test
	public void deveRegistrarEpisodio() {
		serie1.addTemporada(temp1);
		temp1.addEpisodio(epi1);
		dao.persist(serie1);
		series = dao.findAllByClass(Serie.class);
		//verifique que a primeira temporada cadastrada da serie tem um episódio cadastrado
		assertThat(series.get(0).getTemporadas().get(0).getEpisodios().size()==1).isTrue();
		//verifique o nome do episódio
		assertThat(series.get(0).getTemporadas().get(0).getEpisodios().get(0).getNome()).isEqualTo("Volcano");
	}
	
	@Test
	public void deveRegistrarSerieAssistida() {
		serie1.addTemporada(temp1);
		temp1.addEpisodio(epi1);
		dao.persist(serie1);
		series = dao.findAllByClass(Serie.class);
		assertThat(series.get(0).isAcompanhada()).isFalse();
		serie1.setAssistida(true);
		series = dao.findAllByClass(Serie.class);
		assertThat(series.get(0).isAcompanhada()).isTrue();
	}
	
	@Test
	public void deveRegistrarEpisodioAssistido() {
		serie1.addTemporada(temp1);
		temp1.addEpisodio(epi1);
		dao.persist(serie1);
		series = dao.findAllByClass(Serie.class);
		//verifique que o episodio adicionado nao esta sendo assistido
		assertThat(series.get(0).getTemporadas().get(0).getEpisodios().get(0).isAssistido()).isFalse();
		epi1.setAssistido(true);
		series = dao.findAllByClass(Serie.class);
		assertThat(series.get(0).getTemporadas().get(0).getEpisodios().get(0).isAssistido()).isTrue();
	}
	//testa a funcionalidade que exige que se saiba se algum episodio
	//de uma temporada foi assistido.
	@Test
	public void deveAtualizarEstadoDeTemporada() {
		//TODO
	}
}