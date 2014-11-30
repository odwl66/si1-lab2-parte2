package funcional;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.contentType;

import java.util.List;

import models.Serie;
import models.dao.GenericDAO;

import org.junit.Test;

import base.AbstractTest;
import play.twirl.api.Content;


/**
*
* Simple (JUnit) tests that can call all parts of a play app.
* If you are interested in mocking a whole application, see the wiki for more details.
*
*/
public class ApplicationTest extends AbstractTest{
	GenericDAO dao = new GenericDAO();
    @Test
    public void simpleCheck() {
        int a = 1 + 1;
        assertThat(a).isEqualTo(2);
    }

    @Test
    public void renderTemplate() {
    	List<Serie> series = dao.findAllByClass(Serie.class);
        Content html = views.html.index.render(series);
        assertThat(contentType(html)).isEqualTo("text/html");
        assertThat(contentAsString(html)).contains("Sistema de acompanhamento de seriados, desenhos e afins.");
    }


}
