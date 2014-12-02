package controllers;

import java.util.List;

import models.Episodio;
import models.Serie;
import models.Temporada;
import models.dao.GenericDAO;
import play.Logger;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

public class Application extends Controller {
	private static final GenericDAO dao = new GenericDAO();
	private static Form<Serie> serieForm = Form.form(Serie.class);
	
	@Transactional
    public static Result index() {
		Serie serie1 = new Serie("South Park");
		Serie serie2 = new Serie("Emin");
		Temporada temp1 = new Temporada(1, serie1);
		Temporada temp2 = new Temporada(2, serie1);
		Temporada temp3 = new Temporada(1, serie2);
		Episodio epi1 = new Episodio("The Death", temp1, 1);
		Episodio epi2 = new Episodio("Silent", temp2, 2);
		Episodio epi3 = new Episodio("Boom", temp3, 1);
		Episodio epi4 = new Episodio("Boom", temp1, 1);
		temp1.addEpisodio(epi1);
		temp1.addEpisodio(epi4);
		temp2.addEpisodio(epi2);
		temp3.addEpisodio(epi3);
		serie1.addTemporada(temp1);
		serie1.addTemporada(temp2);
		serie2.addTemporada(temp3);
		dao.persist(serie1);
		dao.persist(serie2);
    	List<Serie> series = dao.findAllByClass(Serie.class);
        return ok(index.render(series));
    }
	
	@Transactional
	public static Result acompanharSerie() {
		Form<Serie> filledForm = serieForm.bindFromRequest();
		if (filledForm.hasErrors()) {
            List<Serie> result = dao.findAllByClass(Serie.class);
			return badRequest(views.html.index.render(result));
		} else {
			long id = Long.parseLong(filledForm.data().get("id"));
			Serie serie = dao.findByEntityId(Serie.class, id);			
			serie.setAssistida(true);
            
			dao.merge(serie);
			dao.flush();
			
			Logger.debug("Assistindo a serie: " + filledForm.data().toString() + " como " + serie.getNome() + " ID: "+serie.getId());
            
			return redirect(routes.Application.index());
		}
	}
	
	@Transactional
	public static Result assistirAEpisodio() {
		Form<Serie> filledForm = serieForm.bindFromRequest();
		if (filledForm.hasErrors()) {
            List<Serie> result = dao.findAllByClass(Serie.class);
			return badRequest(views.html.index.render(result));
		} else {
			long id = Long.parseLong(filledForm.data().get("id"));
			Episodio episodio = dao.findByEntityId(Episodio.class, id);
			
			episodio.setAssistido(true);
			
			dao.merge(episodio);
			dao.flush();
			
			Logger.debug("Assistiu a episodio: " + filledForm.data().toString() + " como " + episodio.getNome() + " ID: "+episodio.getId());
            
			return redirect(routes.Application.index());
		}
	}
}
