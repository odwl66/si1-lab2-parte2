package models.recomendaepisodio;

import models.Episodio;
import models.Serie;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by orion on 07/02/15.
 */
@Entity(name="Recomendador")
public abstract class RecomendadorDeEpisodio {
    @Id
    @GeneratedValue
    private Long id;

    public RecomendadorDeEpisodio(){}

    public abstract Episodio getProximoEpisodio(Serie serie);

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
