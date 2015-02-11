package models.recomendaepisodio;

import models.Episodio;
import models.Serie;
import models.Temporada;

import javax.persistence.Entity;
import java.util.List;

/**
 * Created by orion on 07/02/15.
 */
@Entity(name="Recente")
public class RecomendadorDeEpisodioMaisRecente extends RecomendadorDeEpisodio {
    public RecomendadorDeEpisodioMaisRecente(){}

    @Override
    public Episodio getProximoEpisodio(Serie serie) {
        for (int i = serie.getTemporadasTotal(); i > 0; i--) {
            List<Episodio> temp = serie.getTemporadas().get(i-1).getEpisodios();
            for (int j = temp.size(); j >0; j--) {
                //o primeiro episodio achado, de tras para frente
                if (temp.get(j-1).isAssistido()){
                    //se ultimo episodio a que assisti foi o ultimo de uma temporada
                    if (j==temp.size()){
                        //se for o ultimo episodio da ultima temporada
                        if (i==serie.getTemporadasTotal()){
                            return new Episodio("Último episódio da serie já assistido", new Temporada(0, serie), 0);
                        }else{
                            //pegue o primeiro da outra temporada
                            return serie.getTemporadas().get(i).getEpisodios().get(0);
                        }
                    }else{
                        //pegue o proximo episodio da temporada
                        return temp.get(j);
                    }
                }
            }
        }
        return new Episodio("Nenhum episodio assistido", new Temporada(0, serie), 0);
    }
}
