package br.com.iris.dao;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Nilson
 */
public class FabricaEntityManager {

    private static FabricaEntityManager INSTANCE;
    private static EntityManagerFactory emf;
    
    private FabricaEntityManager() {}

    public static FabricaEntityManager getInstance() {
        if (INSTANCE == null) {
                synchronized (FabricaEntityManager.class) {
                    if (INSTANCE == null) {
                        INSTANCE = new FabricaEntityManager();
                    }
                }
            }
        return INSTANCE;
    }
    
    public EntityManagerFactory getEntityManagerFactory(){
        if (emf==null){
            emf=Persistence.createEntityManagerFactory("miltonPU");
        }
        return emf;
    }
}
