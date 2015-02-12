package models.recomendaepisodio;

import models.Episodio;
import models.Serie;
import models.Temporada;

import javax.persistence.Entity;
import java.util.List;

/**
 * Created by orion on 12/02/15.
 */

@Entity
public class RecomendadorDeEpisodioMaisAntigoEspecial extends RecomendadorDeEpisodioMaisAntigo {
    public RecomendadorDeEpisodioMaisAntigoEspecial(){}

    @Override
    public Episodio getProximoEpisodio(Serie serie) {
        Episodio episodioMaisAntigo;
        for (Temporada temporada: serie.getTemporadas()){
            for (Episodio episodio: temporada.getEpisodios()){
                if (!episodio.isAssistido() && !tem3EpisodiosAssistidosApos(serie.getTemporadas(), episodio)){
                    return episodio;
                }
            }
        }
        return new Episodio("3 últimos episódios da série já assistidos!", new Temporada(0, serie), 0);
    }

    private boolean tem3EpisodiosAssistidosApos(List<Temporada> temporadas, Episodio episodio){
        int contador = 0;
        for (int i = temporadas.size() - 1; i >= 0; i--) {
            List<Episodio> episodios = temporadas.get(i).getEpisodios();
            for (int j = episodios.size() - 1; j >= 0; j--) {
                if (episodios.get(j).equals(episodio)){
                    return false;
                } else if (episodios.get(j).isAssistido()){
                    contador++;
                }
                if (contador >= 3){
                    return true;
                }
            }
        }
        return false;
    }
}
