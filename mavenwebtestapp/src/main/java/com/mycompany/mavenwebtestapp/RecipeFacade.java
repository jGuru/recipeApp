package com.mycompany.mavenwebtestapp;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Neeraj 
 * This class represents an stateless EJB for Recipe and extends
 * Generic AbstractFacade for CRUD operations for Recipe Entity
 */
@Stateless
public class RecipeFacade extends AbstractFacade<Recipe> {

    @PersistenceContext(unitName = "myPU")
    private EntityManager em;

    /**
     * @return EntityManager to perform CRUD operations
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * Default Constructor to create RecipeFacade and also calls the
     * AbstractFacade constructor to provide value of generic parameter T to
     * support CRUD operations
     */
    public RecipeFacade() {
        super(Recipe.class);
    }

}
