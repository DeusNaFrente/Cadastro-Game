
package br.com.iris.control;

import br.com.iris.dao.GameDao;
import br.com.iris.model.Game;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.PrimeFaces;

@ManagedBean(name = "gameMB")
@ViewScoped
public class GameMB implements Serializable {
    
    private List<Game> listaGame;
    private Game game = new Game();
    
    private int etapa = 0;

    public List<Game> getListaGame() {
        if(listaGame == null){refresh();}
        return listaGame;
    }

    public void setListaGame(List<Game> listaGame) {
        this.listaGame = listaGame;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public int getEtapa() {
        return etapa;
    }

    public void setEtapa(int etapa) {
        this.etapa = etapa;
    }
    
    
    
    public void refresh() {
        setListaGame(new GameDao().getAll(Game.class));
        
    }

     public void salvar(){
        
        if(getGame() == null){
        PrimeFaces.current().dialog().showMessageDynamic(new FacesMessage(FacesMessage.SEVERITY_INFO, "Campo Vazio!", "Não há registros para salvar."));
            return;
        }
        
        if(getGame().getNome() == null){
        PrimeFaces.current().dialog().showMessageDynamic(new FacesMessage(FacesMessage.SEVERITY_INFO, "Campo Vazio!", "Entre com o nome do game."));
            return;
        }
        
        setGame(new GameDao().save(getGame()));
        refresh();
        setEtapa(0);
        novo();
        
    
    } 
    
     public void novo() {
        setGame(new Game());
        etapa = 1;
    }
    
    public void editar() {
        setEtapa(1);
    }

    public void excluir() {
        if (getGame() == null) {
            return;
        }
        if (getGame().getId() == 0) {
            return;
        }
       
        
        
        new GameDao().delete(Game.class, getGame().getId());
        refresh();
        setEtapa(0);
        novo();

    }
    
    
    
    
}
