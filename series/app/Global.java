import models.Episodio;
import models.Serie;
import models.Temporada;
import models.dao.GenericDAO;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.Play;
import play.db.jpa.JPA;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Global extends GlobalSettings {

    private static GenericDAO dao = new GenericDAO();

    @Override
    public void onStart(Application app) {
        Logger.info("Aplicação inicializada...");

        JPA.withTransaction(new play.libs.F.Callback0() {
            @Override
            public void invoke() throws Throwable {            	
            	popularBD();
                dao.flush();                
            }});
    }
    
    @Override
    public void onStop(Application app){
    	JPA.withTransaction(new play.libs.F.Callback0() {
            @Override
            public void invoke() throws Throwable {
            	Logger.info("Aplicação finalizando...");
            }});    	
    }
    
	public void popularBD() throws Exception{
		 
		String csvFile = Play.application().getFile("/app/seriesFinalFile.csv").getAbsolutePath();
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
	
		try {
	
			br = new BufferedReader(new FileReader(csvFile));
			
			while ((line = br.readLine()) != null) {
				
				String[] info = line.split(cvsSplitBy);
				Serie serie = new Serie(info[0]);
				Temporada temporada = new Temporada(Integer.parseInt(info[1]), serie);
				Episodio episodio;
				if (info.length==4){
					episodio = new Episodio(info[3], temporada, Integer.parseInt(info[2]));
				}else {
					episodio = new Episodio("", temporada, Integer.parseInt(info[2]));
				}
				
				
				if (dao.findByAttributeName("Serie", "nome", info[0]).size()==0){
					temporada.addEpisodio(episodio);
					serie.addTemporada(temporada);
					dao.persist(serie);
					Logger.debug("Serie adicionada! Nome: "+serie.getNome());
					dao.merge(serie);
				}else {
					serie = (Serie) dao.findByAttributeName("Serie", "nome", info[0]).get(0);
					temporada = new Temporada(Integer.parseInt(info[1]), serie);
					if (info.length==4){
						episodio = new Episodio(info[3], temporada, Integer.parseInt(info[2]));
					}else {
						episodio = new Episodio("", temporada, Integer.parseInt(info[2]));
					}
					//se a ultima temporada adicionada a serie nao for a da linha atual
					if (serie.getUltimaTemporada().getNumero()!=Integer.parseInt(info[1])) {
						temporada.addEpisodio(episodio);
						serie.addTemporada(temporada);
					}else{
						serie.getUltimaTemporada().addEpisodio(episodio);
					}
				}
			}
	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}	
	}
}
