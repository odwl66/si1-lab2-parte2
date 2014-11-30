package funcional;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.contentType;

import java.util.List;

import models.Episodio;
import models.Serie;
import models.Temporada;
import models.dao.GenericDAO;

import org.junit.Test;

import base.AbstractTest;
import play.twirl.api.Html;
import views.html.index;

public class IndexViewTest extends AbstractTest{
	GenericDAO dao = new GenericDAO();
	Serie serie1 = new Serie("South Park");
	Serie serie2 = new Serie("Family Guy");
	Temporada temp1 = new Temporada(1, serie1);
	Temporada temp2 = new Temporada(1, serie2);
	Episodio epi1 = new Episodio("Volcano", temp1, 3);
	Episodio epi2 = new Episodio("Damien",temp1,10);	
	Episodio epi3 = new Episodio("Death",temp1,6);
	Episodio epi4 = new Episodio("Death has a Shadow", temp2, 1);
	List<Serie> series;
	
	@Test
	public void deveAparecerSerieCadastrada() {
		serie1.addTemporada(temp1);
		temp1.addEpisodio(epi1);
		dao.persist(serie1);
		series = dao.findAllByClass(Serie.class);
		Html html = index.render(series);
		assertThat(contentType(html)).isEqualTo("text/html");
		assertThat(contentAsString(html)).contains("South Park");
	}

}
