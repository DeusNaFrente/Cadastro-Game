package br.com.iris.model;


import br.com.iris.dao.ItemGameDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "pai", catalog = "milton")
public class Pai implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String descricao;
    private double frete;
    private double vtt;

         

    @OneToOne
    @JoinColumn(name = "idg")
    private Game game;
    
    @Transient
    private List<ItemGame> itensG;

    public Pai() {
        this.frete = 10.00;
    }
    
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

   
    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

   

  
    public List<ItemGame> getItensG() {
        if (itensG == null) {
            if (this.getId() != 0) {
                itensG = new ItemGameDao().getByPai(this);
            } else {
                itensG = new ArrayList<>();
            }
        }
        return itensG;
    }

    public double getFrete() {
        return frete;
    }

    public void setFrete(double frete) {
           this.frete = frete;
    }

    

    public double getVtt() {
     
         return vtt;
    }

    public void setVtt(double vtt) {
        
        this.vtt = vtt;
    }

   
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + this.id;
        return hash;
    }

    @Override //se não sobrescrever os metodos para comparação entre objetos vai retornar nulo
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
        final Pai other = (Pai) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

}
