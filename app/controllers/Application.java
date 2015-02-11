package controllers;

import java.util.List;

import models.Episodio;
import models.Serie;
import models.dao.GenericDAO;
import play.Logger;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

public class Application extends Controller {
	private static final GenericDAO DAO = new GenericDAO();
	private static Form<Serie> serieForm = Form.form(Serie.class);
	
	@Transactional
    public static Result index() {		
    	List<Serie> series = DAO.findAllByClass(Serie.class);
        return ok(index.render(series));
    }
	
	@Transactional
	public static Result acompanharSerie() {
		Form<Serie> filledForm = serieForm.bindFromRequest();
		if (filledForm.hasErrors()) {
            List<Serie> result = DAO.findAllByClass(Serie.class);
			return badRequest(views.html.index.render(result));
		} else {
			long id = Long.parseLong(filledForm.data().get("id"));
			Serie serie = DAO.findByEntityId(Serie.class, id);
			serie.setAssistida(true);
            
			DAO.merge(serie);
			DAO.flush();
			
			Logger.debug("Assistindo a serie: " + filledForm.data().toString() + " como " + serie.getNome() + " ID: "+serie.getId());
            
			return redirect(routes.Application.index());
		}
	}
	
	@Transactional
	public static Result assistirAEpisodio() {
		Form<Serie> filledForm = serieForm.bindFromRequest();
		if (filledForm.hasErrors()) {
            List<Serie> result = DAO.findAllByClass(Serie.class);
			return badRequest(views.html.index.render(result));
		} else {
			long id = Long.parseLong(filledForm.data().get("id"));
			Episodio episodio = DAO.findByEntityId(Episodio.class, id);
			
			episodio.setAssistido(true);			
			
			DAO.merge(episodio);
			DAO.flush();
			
			Logger.debug("Assistiu a episodio: " + filledForm.data().toString() + " como " + episodio.getNome() + " ID: "+episodio.getId());
            
			return redirect(routes.Application.index());
		}
	}

	@Transactional
	public static Result mudaRecomendacaoDeSerie() {
		Form<Serie> filledForm = serieForm.bindFromRequest();
		if (filledForm.hasErrors()) {
			List<Serie> result = DAO.findAllByClass(Serie.class);
			return badRequest(views.html.index.render(result));
		} else {
			long id = Long.parseLong(filledForm.data().get("id"));
			Serie serie = DAO.findByEntityId(Serie.class, id);
			serie.setRecomendadorDeEpisodio(filledForm.data().get("recomendador"));

			Logger.debug("Recomendou: " + filledForm.data().get("recomendador") + " na serie " + serie.getNome());

			DAO.merge(serie);
			DAO.flush();

			return redirect(routes.Application.index());
		}
	}
}
