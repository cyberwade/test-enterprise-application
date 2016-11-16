package ru.testea.impl;

import ru.testea.api.BaseEntity;
import ru.testea.api.BaseEntity_;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Base query repository for entities of the type {@link E}.
 *
 * @param <E>
 *        entity type.
 */
abstract class BaseRepository<E extends BaseEntity>
{
    /**
     * {@link EntityManager} instance.
     */
    protected EntityManager entityManager;

    /**
     * Creates a new query repository.
     */
    protected BaseRepository()
    {
    }

    /**
     * Delegate for the {@link EntityManager#merge(Object)}.
     *
     * @param entity
     *        entity.
     */
    public void persist(
        E entity)
    {
        entityManager.persist(entity);
    }

    /**
     * Delegate for the {@link EntityManager#merge(Object)}.
     *
     * @param entity
     *        entity.
     */
    public void merge(
        E entity)
    {
        entityManager.merge(entity);
    }

    /**
     * Delegate for the {@link EntityManager#remove(Object)}.
     *
     * @param entity
     *        entity.
     */
    public void remove(
        E entity)
    {
        entityManager.remove(entity);
    }

    /**
     * Finds the entity by specified identifier.
     *
     * @param id
     *        entity identifier
     * @return found entity or {@code null}, if entity was not found.
     */
    public E find(
        Long id)
    {
        return entityManager.find(getEntityClass(), id);
    }

    /**
     * Gets the entity with all associated entities by specified identifier.
     *
     * @param id
     *        entity identifier.
     * @return found entity or {@code null}, if entity was not found.
     */
    public E get(
        Long id)
    {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<E> criteriaQuery = criteriaBuilder.createQuery(
            getEntityClass());

        Root<E> root = criteriaQuery.from(getEntityClass());
        initializeAssociations(root);

        criteriaQuery.where(
            criteriaBuilder.equal(root.get(BaseEntity_.id), id));

        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    /**
     * <p>
     * Gets the list of all entities.
     * </p>
     *
     * <p>
     * The result list will be ordered by entity identifier.
     * </p>
     *
     * @return entities list.
     */
    public List<E> listAll()
    {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<E> criteriaQuery = criteriaBuilder.createQuery(
            getEntityClass());

        Root<E> root = criteriaQuery.from(getEntityClass());

        criteriaQuery.orderBy(criteriaBuilder.desc(root.get(BaseEntity_.id)));

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    /**
     * Gets the entity class for entity type {@link E}.
     *
     * @return entity class for entity type {@link E}.
     */
    protected abstract Class<E> getEntityClass();

    /**
     * Initializes associations of the specified entity root.
     *
     * @param root
     *        entity root.
     */
    protected void initializeAssociations(
        Root<E> root)
    {
        // Empty implementation. Should be overridden if needs.
    }
}
