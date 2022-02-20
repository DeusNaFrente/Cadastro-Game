package br.com.iris.dao;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.RollbackException;

public abstract class DaoImpl<T, I extends Serializable>{
    private EntityManager manager;

    public T save(T entity) {
        T saved=null;
        try{
            getEntityManager().getTransaction().begin();
            saved = getEntityManager().merge(entity);
            getEntityManager().getTransaction().commit();
        }catch(Exception e){
            e.printStackTrace();
        }
        finally{
            getEntityManager().close();
        }
        return saved;
    }

    public void delete(Class<T> classe, I pk) throws RollbackException{
        try{
            getEntityManager().getTransaction().begin();
            T t=getEntityManager().find(classe, pk);
            getEntityManager().remove(t);
            getEntityManager().getTransaction().commit();
        }catch(RollbackException e){
            throw e;
        }catch(Exception e){
            e.printStackTrace();
        }
        finally{
            getEntityManager().close();
        }
    }

    public T getById(Class<T> classe, I pk) throws NoResultException{
        T t;
        try {
            t=getEntityManager().find(classe, pk);
        } catch (NoResultException e) {
            t=null;
        }finally{
            getEntityManager().close();
        }
        return t;
    }

    @SuppressWarnings("unchecked")
    public List<T> getAll(Class<T> classe) {
        List<T>ts=null;
        try{
            Query qry=getEntityManager().createQuery("select o from " + classe.getSimpleName() + " o");
            ts=qry.getResultList();
        }catch(Exception e){
            e.printStackTrace();
        }
        finally{
            getEntityManager().close();
        }
        return ts;
    }

    public EntityManager getEntityManager(){
        if (manager == null || !manager.isOpen()) {
            manager=FabricaEntityManager.getInstance().getEntityManagerFactory().createEntityManager();
        }
        return manager;
    }
}