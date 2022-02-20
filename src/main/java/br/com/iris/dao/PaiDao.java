
package br.com.iris.dao;


import br.com.iris.model.Pai;



/**
 *
 * @author Jr
 */
public class PaiDao extends DaoImpl<Pai,Integer> {
    
    
    
    public void delete(Pai l){
      new ItemGameDao().excluirItemGame(l);   
      
              
      
      this.delete(Pai.class, l.getId());
    }
    
}
