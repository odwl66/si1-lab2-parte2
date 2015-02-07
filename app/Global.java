import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import models.Episodio;
import models.Serie;
import models.Temporada;
import models.dao.GenericDAO;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.Play;
import play.db.jpa.JPA;

public class Global extends GlobalSettings {

    private static GenericDAO dao = new GenericDAO();
	private static final int NOMEDASERIE = 0;
	private static final int NUMDATEMPORADA = 1;
	private static final int NUMDOEPISODIO = 2;
	private static final int NOMEDOEPISODIO = 3;

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

            	List<Serie> series = dao.findAllByClass(Serie.class);
            	
            	for (Serie serie : series) {
            		dao.removeById(Serie.class, serie.getId());
            	}
            }});    	
    }
    
	public void popularBD() {

		String csvFile = Play.application().getFile("/conf/seriesFinalFile.csv").getAbsolutePath();
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		try {
	
			br = new BufferedReader(new FileReader(csvFile));
			String[] info = line.split(cvsSplitBy);
			Serie serie = new Serie("South Park");
			Temporada temporada =  new Temporada(1, serie);
			Episodio episodio = new Episodio("Cartman Gets an Anal Probe", temporada, 1);
			temporada.addEpisodio(episodio);
			serie.addTemporada(temporada);
			line = br.readLine();
			
			while ((line = br.readLine()) != null) {
				info = line.split(cvsSplitBy);
				//Falta tratar o caso em que a coluna de nome do epi e vazio
				if (serie.getNome().equals(info[NOMEDASERIE])){
					if (serie.getTemporadasTotal()!=0 && serie.getUltimaTemporada().getNumero()==Integer.parseInt(info[NUMDATEMPORADA])) {
						if (episodioTemNome(info)) {
							episodio = new Episodio(info[NOMEDOEPISODIO], serie.getUltimaTemporada(), Integer.parseInt(info[NUMDOEPISODIO]));
						} else {
							episodio = new Episodio("", serie.getUltimaTemporada(), Integer.parseInt(info[NUMDOEPISODIO]));
						}
						serie.getUltimaTemporada().addEpisodio(episodio);
					} else{
						temporada = new Temporada(Integer.parseInt(info[NUMDATEMPORADA]),serie);
						
						if (episodioTemNome(info)) {
							episodio = new Episodio(info[NOMEDOEPISODIO], temporada, Integer.parseInt(info[NUMDOEPISODIO]));
						} else {
							episodio = new Episodio("", temporada, Integer.parseInt(info[NUMDOEPISODIO]));
						}
						temporada.addEpisodio(episodio);
						
						serie.addTemporada(temporada);
					}
				} else {
					dao.persist(serie);
					serie = new Serie(info[NOMEDASERIE]);
					temporada = new Temporada(Integer.parseInt(info[NUMDATEMPORADA]), serie);
					if (episodioTemNome(info)) {
						episodio = new Episodio(info[NOMEDOEPISODIO], temporada, Integer.parseInt(info[NUMDOEPISODIO]));
					} else {
						episodio = new Episodio("", temporada, Integer.parseInt(info[NUMDOEPISODIO]));
					}
					temporada.addEpisodio(episodio);
					serie.addTemporada(temporada);
					
				}
			}
			//adiciona ultima serie do CSV
			dao.persist(serie);
		}
								
	
		catch (Exception e) {
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

	private boolean episodioTemNome(String[] info){
		return info.length >= 4;
	}
}
