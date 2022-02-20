package br.com.iris.control;

import br.com.iris.dao.GameDao;
import br.com.iris.dao.ItemGameDao;
import br.com.iris.dao.PaiDao;
import br.com.iris.model.Game;
import br.com.iris.model.ItemGame;
import br.com.iris.model.Pai;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.primefaces.PrimeFaces;

/**
 *
 * @author Jr
 */
@ManagedBean(name = "paiMB")
@ViewScoped

public class PaiMB implements Serializable {

    @ManagedProperty(value = "#{gameMB}")
    private GameMB gameMB;
    public GameMB getGameMB() {return gameMB;}
    public void setGameMB(GameMB gameMB) {this.gameMB = gameMB;}

    private List<Pai> listarPai;
    private Pai pai = new Pai();

    private List<Game> itemGame;
    private Game game = new Game();
    private int etapa = 0;
    private double frete = 10;
    ItemGame itg = new ItemGame(); 

    public void refresh() {

        setListarPai(new PaiDao().getAll(Pai.class));
        setItemGame(new GameDao().getAll(Game.class));
        itemGame = new ArrayList<>();

    }

    public int getEtapa() {
        return etapa;
    }

    public void setEtapa(int etapa) {
        this.etapa = etapa;
    }

    public List<Pai> getListarPai() {
        if (listarPai == null) {
            refresh();
        }
        return listarPai;
    }

    public void setListarPai(List<Pai> listarPai) {
        this.listarPai = listarPai;
    }

    public Pai getPai() {
        return pai;
    }

    public void setPai(Pai pai) {
        this.pai = pai;
    }

    public List<Game> getItemGame() {
        if (itemGame == null) {
            refresh();
        }
        return itemGame;
    }

    public void setItemGame(List<Game> itemGame) {
        this.itemGame = itemGame;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public double getFrete() {
        return frete;
    }

    public void setFrete(double frete) {
        this.frete = frete;
    }

    public ItemGame getItg() {
        return itg;
    }

    public void setItg(ItemGame itg) {
        this.itg = itg;
    }
    
    

    //***********Adicionar Game(dlgGames)
    public void adicionarGame(Game game) throws SQLException {

        List<ItemGame> itensG = getPai().getItensG();

        int posicaoEncontrada = -1;

        for (int pos = 0; pos < itensG.size() && posicaoEncontrada < 0; pos++) {//percorrer a lista de itens
            ItemGame itemTemp = itensG.get(pos);//jogar a posição atual dentro do itemTemp
            if (itemTemp.getGame().equals(game)) {
                posicaoEncontrada = pos;//só sai de -1 qdo cai aqui            
            }
        }

        ItemGame igame = new ItemGame();
        igame.setGame(game);

        if (posicaoEncontrada < 0) {

            if (getPai().getDescricao() == null || "".equals(getPai().getDescricao())) {
                PrimeFaces.current().dialog().showMessageDynamic(new FacesMessage(FacesMessage.SEVERITY_INFO, "Nenhum nome foi digitado!", "Primeiro digite a descrição do item."));
                return;
            }
            igame.setQtd(1);
            igame.setValor(game.getPreco());
            itensG.add(igame);
            if (pai.getVtt() < 250) {
                igame.setValor(igame.getValor() + pai.getFrete());
            }

        } else if (igame.getGame().equals(game)) {

            ItemGame itemTemp = itensG.get(posicaoEncontrada);//posição encontrada anteriormente

            igame.setQtd(itemTemp.getQtd() + 1);//pega o valor anterior e add mais um
            itensG.set(posicaoEncontrada, igame);//posiçao encontrada e o que eu quero add
            igame.setValor(game.getPreco() * igame.getQtd());
            if (game.getPreco() * igame.getQtd() < 250.00) {
                igame.setValor(igame.getValor() + (pai.getFrete() * igame.getQtd()));
            }

        }

        if (game.getPreco() * igame.getQtd() < 250.00) {

            pai.setVtt(pai.getVtt() + game.getPreco() + pai.getFrete());
        } else {
            pai.setVtt(game.getPreco() * igame.getQtd());

        }

    }

    //***********Remover Game(dlgGames)
    public void removerGame(ItemGame itemgame) throws SQLException {

        List<ItemGame> itensG = getPai().getItensG();

        int posicaoEncontrada = -1;

        for (int pos = 0; pos < itensG.size() && posicaoEncontrada < 0; pos++) {
            ItemGame itemTemp = itensG.get(pos);

            if (itemTemp.getGame().equals(itemgame.getGame())) {
                posicaoEncontrada = pos;//só sai de -1 qdo cai aqui

            }
        }

        if (posicaoEncontrada > -1) {

            itemgame.setQtd(itemgame.getQtd() - 1);
            itemgame.setValor(itemgame.getGame().getPreco() * itemgame.getQtd());
            if (itemgame.getValor() < 250) {
                itemgame.setValor(itemgame.getValor() + (itemgame.getQtd() * pai.getFrete()));
            } else {
                pai.setVtt(itemgame.getValor());
            }

            if (itemgame.getQtd() == 0) {

                itensG.remove(posicaoEncontrada);

            }

            if (itemgame.getId() > 0) {

                ItemGameDao dao = new ItemGameDao();
                dao.excluirGame(itemgame);

            }

            if (getPai().getItensG() == null) {
                return;
            }

        }

        pai.setVtt(itemgame.getGame().getPreco() * itemgame.getQtd());

        if (itemgame.getGame().getPreco() * itemgame.getQtd() > 250.00) {

            pai.setVtt(pai.getVtt());

        } else {

            pai.setVtt(itemgame.getGame().getPreco() * itemgame.getQtd() + (itemgame.getQtd() * pai.getFrete()));
        }
    }

    public void salvar() throws SQLException {

        List<ItemGame> itensG = getPai().getItensG();

        if (getPai() == null) {
            PrimeFaces.current().dialog().showMessageDynamic(new FacesMessage(FacesMessage.SEVERITY_INFO, "Campo Vazio!", "Sem dados para salvar."));
            return;
        }

        if (getPai().getDescricao().isEmpty()) {
            PrimeFaces.current().dialog().showMessageDynamic(new FacesMessage(FacesMessage.SEVERITY_INFO, "Campo Vazio!", "Forneça dados em descrição."));
            return;
        }

        if (itensG.size() <= 0) {
            PrimeFaces.current().dialog().showMessageDynamic(new FacesMessage(FacesMessage.SEVERITY_INFO, "Campo Vazio!", "Adicione um ou mais games."));
            return;
        }

        setPai(new PaiDao().save(getPai()));

        for (ItemGame i : itensG) {

            if (i.getId() >= 0) {

                i.setPai(getPai());
                new ItemGameDao().save(i);
            } else {
                PrimeFaces.current().dialog().showMessageDynamic(new FacesMessage(FacesMessage.SEVERITY_INFO, "Erro na Composição dos jogos!", "Inclua os jogos."));
                return;
            }

        }

        setPai(new PaiDao().save(getPai()));
        refresh();
        setEtapa(0);
        novo();

    }

    public void editar() {
        setEtapa(1);
    }

    public void novo() {
        setPai(new Pai());
        etapa = 1;
    }

    

    
    public void excluirPaieFilhos() {
        if (getPai() == null) {
            return;
        }
        if (getPai().getId() == 0) {
            return;
        }
        new PaiDao().delete(getPai());
        refresh();
        setEtapa(0);
        novo();
    }
    

}
