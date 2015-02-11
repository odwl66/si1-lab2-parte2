package models.RecomendaEpisodio;

import models.Episodio;
import models.Serie;
import models.Temporada;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by orion on 07/02/15.
 */
@Entity(name="Antigo")
public class RecomendadorDeEpisodioMaisAntigo extends RecomendadorDeEpisodio {
    public RecomendadorDeEpisodioMaisAntigo(){}

    @Override
    public Episodio getProximoEpisodio(Serie serie) {
        Episodio episodioMaisAntigo;
        for (Temporada temporada: serie.getTemporadas()){
            episodioMaisAntigo= temporada.episodioMaisAntigoNaoAssistido();
            if (episodioMaisAntigo != null){
                return episodioMaisAntigo;
            }
        }
        return new Episodio("Todos os episódios da série já foram assistidos!", new Temporada(0, serie), 0);
    }
}
