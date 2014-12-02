package funcional;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.contentType;

import java.util.List;

import javax.persistence.EntityManager;

import models.Episodio;
import models.Serie;
import models.Temporada;
import models.dao.GenericDAO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import play.GlobalSettings;
import play.db.jpa.JPA;
import play.db.jpa.JPAPlugin;
import play.test.FakeApplication;
import play.test.Helpers;
import play.twirl.api.Html;
import scala.Option;
import views.html.index;

public class IndexViewTest {
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
    public EntityManager em;
    
    @Before
    public void setUp() {
        FakeApplication app = Helpers.fakeApplication(new GlobalSettings());
        Helpers.start(app);
        Option<JPAPlugin> jpaPlugin = app.getWrappedApplication().plugin(JPAPlugin.class);
        em = jpaPlugin.get().em("default");
        JPA.bindForCurrentThread(em);
        em.getTransaction().begin();
    }
	
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
	
	@After
    public void tearDown() {
        em.getTransaction().commit();
        JPA.bindForCurrentThread(null);
        em.close();
    }
}