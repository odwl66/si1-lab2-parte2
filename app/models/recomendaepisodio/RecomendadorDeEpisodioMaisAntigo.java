package models.recomendaepisodio;

import models.Episodio;
import models.Serie;
import models.Temporada;

import javax.persistence.Entity;

/**
 * Created by orion on 07/02/15.
 */
@Entity
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
