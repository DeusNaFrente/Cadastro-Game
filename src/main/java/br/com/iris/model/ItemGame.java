package br.com.iris.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Iris
 */


@Entity
@Table(name = "itemgame", catalog = "milton")
public class ItemGame implements Serializable{
    
       
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;
    private int qtd;
    private double valor;
       
    
    @OneToOne
    @JoinColumn(name="idg")
    private Game game;
    
    @OneToOne
    @JoinColumn(name="idpai")
    private Pai pai;
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getQtd() {
        return qtd;
    }

    public void setQtd(int qtd) {
        this.qtd = qtd;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Pai getPai() {
        return pai;
    }

    public void setPai(Pai pai) {
        this.pai = pai;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    

    
   
   
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ItemGame other = (ItemGame) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
    
    
    
    
    
    
}
