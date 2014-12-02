import java.util.List;

import models.Serie;
import models.dao.GenericDAO;
import play.Application;
import play.GlobalSettings;
import play.Logger;
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
    
	public void run() {
		 
		String csvFile = "/series/app/seriesFinalFile.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
	
		try {
	
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				
				String[] info = line.split(cvsSplitBy);
				
				Serie serie = new Serie(info[0]);
				
				if (dao.findByAttributeName("Serie", "nome", info[0]).size()==0){
					//devo criar serie nova
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
	
		System.out.println("Done");
	}
}
