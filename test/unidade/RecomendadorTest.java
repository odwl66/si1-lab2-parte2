package unidade;

import models.Episodio;
import models.recomendaepisodio.RecomendadorDeEpisodio;
import models.recomendaepisodio.RecomendadorAntigo;
import models.recomendaepisodio.RecomendadorAntigoEspecial;
import models.recomendaepisodio.RecomendadorRecente;
import models.Serie;
import models.Temporada;
import org.junit.Before;
import org.junit.Test;
import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by orion on 07/02/15.
 */
public class RecomendadorTest {

    Serie serie1;
    Temporada temp1, temp2;
    Episodio epi1, epi2, epi3, epi5, epi6, epi7, e;
    RecomendadorDeEpisodio recomendadorDeEpisodio;

    @Before
    public void setUp() {
        serie1 = new Serie("South Park");
        temp1 = new Temporada(1, serie1);
        epi1 = new Episodio("Volcano", temp1, 1);
        epi2 = new Episodio("Damien", temp1, 2);
        epi3 = new Episodio("Death", temp1, 3);

        temp2 = new Temporada(2, serie1);
        epi5 = new Episodio("Episodio 5", temp2, 1);
        epi6 = new Episodio("Episodio 6", temp2, 2);
        epi7 = new Episodio("Episodio 7", temp2, 3);

        serie1.addTemporada(temp1);
        serie1.addTemporada(temp2);

        temp1.addEpisodio(epi1);
        temp1.addEpisodio(epi2);
        temp1.addEpisodio(epi3);

        temp2.addEpisodio(epi5);
        temp2.addEpisodio(epi6);
        temp2.addEpisodio(epi7);
    }

    @Test
    public void testaRecomendadorDeEpisodioMaisAntigoSemNenhumEpisodioAssistido(){
        recomendadorDeEpisodio = new RecomendadorAntigo();
        e = recomendadorDeEpisodio.getProximoEpisodio(serie1);

        assertThat(e.getNome()).isEqualTo("Volcano");
    }

    @Test
    public void testaRecomendadorDeEpisodioMaisAntigoCom1EpisodioAssistido(){
        recomendadorDeEpisodio = new RecomendadorAntigo();
        epi1.setAssistido(true);

        e = recomendadorDeEpisodio.getProximoEpisodio(serie1);

        assertThat(e.getNome()).isEqualTo("Damien");
    }

    @Test
    public void testaRecomendadorDeEpisodioMaisAntigoCom1aTemporadaAssistida(){
        recomendadorDeEpisodio = new RecomendadorAntigo();
        epi1.setAssistido(true);
        epi2.setAssistido(true);
        epi3.setAssistido(true);

        e = recomendadorDeEpisodio.getProximoEpisodio(serie1);

        assertThat(e.getNome()).isEqualTo("Episodio 5");
    }

    @Test
    public void testaRecomendadorDeEpisodioMaisAntigoCom1oe3oEpisodiosAssistidos(){
        recomendadorDeEpisodio = new RecomendadorAntigo();
        epi1.setAssistido(true);
        epi3.setAssistido(true);

        e = recomendadorDeEpisodio.getProximoEpisodio(serie1);

        assertThat(e.getNome()).isEqualTo("Damien");
    }

    @Test
    public void testaRecomendadorDeEpisodioMaisAntigoComTodosEpisodiosAssistidos(){
        recomendadorDeEpisodio = new RecomendadorAntigo();
        epi1.setAssistido(true);
        epi2.setAssistido(true);
        epi3.setAssistido(true);
        epi5.setAssistido(true);
        epi6.setAssistido(true);
        epi7.setAssistido(true);

        e = recomendadorDeEpisodio.getProximoEpisodio(serie1);

        assertThat(e.getNome()).isEqualTo("Todos os episódios da série já foram assistidos!");
    }





    @Test
    public void testaRecomendadorDeEpisodioMaisAntigoEspecialSemNenhumEpisodioAssistido(){
        recomendadorDeEpisodio = new RecomendadorAntigoEspecial();
        e = recomendadorDeEpisodio.getProximoEpisodio(serie1);

        assertThat(e.getNome()).isEqualTo("Volcano");
    }

    @Test
    public void testaRecomendadorDeEpisodioMaisAntigoEspecialCom1EpisodioAssistido(){
        recomendadorDeEpisodio = new RecomendadorAntigoEspecial();
        epi1.setAssistido(true);

        e = recomendadorDeEpisodio.getProximoEpisodio(serie1);

        assertThat(e.getNome()).isEqualTo("Damien");
    }

    @Test
    public void testaRecomendadorDeEpisodioMaisAntigoEspecialCom1aTemporadaAssistida(){
        recomendadorDeEpisodio = new RecomendadorAntigoEspecial();
        epi1.setAssistido(true);
        epi2.setAssistido(true);
        epi3.setAssistido(true);

        e = recomendadorDeEpisodio.getProximoEpisodio(serie1);

        assertThat(e.getNome()).isEqualTo("Episodio 5");
    }

    @Test
    public void testaRecomendadorDeEpisodioMaisAntigoEspecialCom1oe3oEpisodiosAssistidos(){
        recomendadorDeEpisodio = new RecomendadorAntigoEspecial();
        epi1.setAssistido(true);
        epi3.setAssistido(true);

        e = recomendadorDeEpisodio.getProximoEpisodio(serie1);

        assertThat(e.getNome()).isEqualTo("Damien");
    }

    @Test
    public void testaRecomendadorDeEpisodioMaisAntigoEspecialCom1o3oe4oEpisodiosAssistidos(){
        recomendadorDeEpisodio = new RecomendadorAntigoEspecial();
        epi1.setAssistido(true);
        epi3.setAssistido(true);
        epi5.setAssistido(true);

        e = recomendadorDeEpisodio.getProximoEpisodio(serie1);

        assertThat(e.getNome()).isEqualTo("Damien");
    }

    @Test
    public void testaRecomendadorDeEpisodioMaisAntigoEspecialCom3EpisodiosAssistidos(){
        recomendadorDeEpisodio = new RecomendadorAntigoEspecial();
        epi1.setAssistido(true);
        epi3.setAssistido(true);
        epi5.setAssistido(true);
        epi6.setAssistido(true);

        e = recomendadorDeEpisodio.getProximoEpisodio(serie1);

        assertThat(e.getNome()).isEqualTo("Episodio 7");
    }

    @Test
    public void testaRecomendadorDeEpisodioMaisAntigoEspecialComTodosEpisodiosAssistidos(){
        recomendadorDeEpisodio = new RecomendadorAntigoEspecial();
        epi1.setAssistido(true);
        epi2.setAssistido(true);
        epi3.setAssistido(true);
        epi5.setAssistido(true);
        epi6.setAssistido(true);
        epi7.setAssistido(true);

        e = recomendadorDeEpisodio.getProximoEpisodio(serie1);

        assertThat(e.getNome()).isEqualTo("3 últimos episódios da série já assistidos!");
    }





    @Test
    public void testaRecomendadorDeEpisodioMaisRecenteSemNenhumEpisodioAssistido(){
        recomendadorDeEpisodio = new RecomendadorRecente();
        e = recomendadorDeEpisodio.getProximoEpisodio(serie1);

        assertThat(e.getNome()).isEqualTo("Nenhum episodio assistido");
    }

    @Test
    public void testaRecomendadorDeEpisodioMaisRecenteCom1EpisodioAssistido(){
        recomendadorDeEpisodio = new RecomendadorRecente();
        epi1.setAssistido(true);

        e = recomendadorDeEpisodio.getProximoEpisodio(serie1);

        assertThat(e.getNome()).isEqualTo("Damien");
    }

    @Test
    public void testaRecomendadorDeEpisodioMaisRecenteCom1aTemporadaAssistida(){
        recomendadorDeEpisodio = new RecomendadorRecente();
        epi1.setAssistido(true);
        epi2.setAssistido(true);
        epi3.setAssistido(true);

        e = recomendadorDeEpisodio.getProximoEpisodio(serie1);

        assertThat(e.getNome()).isEqualTo("Episodio 5");
    }

    @Test
    public void testaRecomendadorDeEpisodioMaisRecenteCom1oe3oEpisodiosAssistidos(){
        recomendadorDeEpisodio = new RecomendadorRecente();
        epi1.setAssistido(true);
        epi3.setAssistido(true);

        e = recomendadorDeEpisodio.getProximoEpisodio(serie1);

        assertThat(e.getNome()).isEqualTo("Episodio 5");
    }

    @Test
    public void testaRecomendadorDeEpisodioMaisRecenteComTodosEpisodiosAssistidos(){
        recomendadorDeEpisodio = new RecomendadorRecente();
        epi1.setAssistido(true);
        epi2.setAssistido(true);
        epi3.setAssistido(true);
        epi5.setAssistido(true);
        epi6.setAssistido(true);
        epi7.setAssistido(true);

        e = recomendadorDeEpisodio.getProximoEpisodio(serie1);

        assertThat(e.getNome()).isEqualTo("Último episódio da serie já assistido");
    }
}
