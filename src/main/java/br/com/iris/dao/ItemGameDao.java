
package br.com.iris.dao;

import br.com.iris.ConexaoBanco.dataBase;
import br.com.iris.model.ItemGame;
import br.com.iris.model.Pai;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Jr
 */
public class ItemGameDao extends DaoImpl<ItemGame,Integer> {
    
    public List<ItemGame> getByPai(Pai pai) { //Listar os itens da primeira tela pai.
        List<ItemGame>rs = null;

        try {
            Query qry = getEntityManager().createQuery("select i from ItemGame i where i.pai= :pai");
            qry.setParameter("pai", pai);

            rs = qry.getResultList();

        } catch (Exception e) {
        } finally {
            getEntityManager().close();
        }

        return rs;

    } 
    
    public void excluirGame(ItemGame i) throws SQLException { //excluir itens da tabela de composição dos games
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM itemgame ");
        sql.append("WHERE idg = ? and id = ? ");
        
        Connection conexao =null;
        try{
            conexao = dataBase.conectar();

            PreparedStatement comando = conexao.prepareStatement(sql.toString());
            comando.setInt(1, i.getGame().getId());
            comando.setInt(2, i.getId());


            comando.executeUpdate();
            comando.close();
        }catch(Exception e){
        }finally{
            conexao.close();
        }

    }
    
  
    
    
    public void excluirItemGame(Pai t) { //Excluir pai e filhos
        try {
            Query qry = getEntityManager().createQuery("delete from ItemGame i where i.pai=:pai");
            qry.setParameter("pai", t);
            getEntityManager().getTransaction().begin();
            qry.executeUpdate();
            getEntityManager().getTransaction().commit();
        } catch (Exception e) {
        } finally {
            getEntityManager().close();
        }

    }
    
   
    
    
     
    
}
