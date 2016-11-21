package ru.testea.api;

import com.google.common.base.Function;

import java.util.List;

/**
 * Base CRUD service for entities of the type {@link E}.
 *
 * @param <E>
 *        entity type.
 *
 * @author Vadim Kuznetskikh
 */
public interface IBaseEntityCRUDService<E extends BaseEntity>
{
    /**
     * Creates the specified entity.
     *
     * @param entity
     *        entity.
     */
    void create(
        E entity);

    /**
     * Finds the entity by specified identifier.
     *
     * @param id
     *        entity identifier.
     * @return found entity.
     * @throws EntityNotFoundException
     *         throws when entity was not found by specified identifier.
     */
    E find(
        Long id)
    throws EntityNotFoundException;

    /**
     * Gets the entity with all associated entities by specified identifier.
     *
     * @param id
     *        entity identifier.
     * @return found entity.
     * @throws EntityNotFoundException
     *         throws when entity was not found by specified identifier.
     */
    E get(
        Long id)
    throws EntityNotFoundException;

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
    List<E> listAll();

    /**
     * Updates the specified entity.
     *
     * @param updater
     *        function for update entity data.
     * @param id
     *        entity identifier.
     * @return entity.
     * @throws EntityNotFoundException
     *         throws when entity was not found by specified identifier.
     */
    E update(
        Function<E, Void> updater,
        Long id)
    throws EntityNotFoundException;

    /**
     * Deletes the specified entity.
     *
     * @param entity
     *        entity.
     */
    void delete(
        E entity);
}
