package com.mycompany.mavenwebtestapp;

import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Neeraj
 * Generic class which provides methods to perform CRUD operations
 * for Any domain object in this application
 */
public abstract class AbstractFacade<T> {

    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    /**
     * Method to persist entity
     * @param entity to persist
     */
    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    /**
     * Method to update/edit an existing entity
     * @param entity to update/edit
     */
    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    /**
     * Method to delete/remove an existing entity
     * @param entity to delete/remove
     */
    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    /**
     * Method to find entity in persistent store
     * @param id of entity to find
     * @return entity found in persistent store or null in case no entity present
     */
    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    /**
     * Method to findAll entities present in the persistent store
     * @return List<T> 
     */
    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    /**
     * Method to find entities in given range
     * @return List<T>
     */
    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }
    
    /**
     * Method to find total count of entity present in persistent store
     * @return int value as total count
     */

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    
}
