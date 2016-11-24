package ru.testea.impl;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.testea.api.BaseEntity;
import ru.testea.api.EntityNotFoundException;
import ru.testea.api.IBaseEntityCRUDService;

import java.util.List;

/**
 * Base CRUD service implementation.
 *
 * @param <E>
 *        entity type.
 * @param <R>
 *        repository type.
 *
 * @author Vadim Kuznetskikh
 */
@Transactional
public abstract class BaseEntityCRUDService<
    E extends BaseEntity,
    R extends BaseEntityRepository<E>>
implements IBaseEntityCRUDService<E>
{
    /**
     * Entity query repository.
     */
    protected R repository;

    /**
     * Creates new CRUD service.
     */
    public BaseEntityCRUDService()
    {
    }

    /**
     * Initializes the query repository.
     *
     * @param repository
     *        query repository.
     */
    @Autowired
    public void initialize(
        R repository)
    {
        this.repository = repository;
    }

    @Override
    public void create(
        E entity)
    {
        validateEntity(entity);

        repository.persist(entity);
    }

    @Override
    public E find(
        Long id)
    throws EntityNotFoundException
    {
        validateId(id);

        return getInternal(id, new Function<Long, E>()
        {
            @Override
            public E apply(
                Long id)
            {
                return repository.find(id);
            }
        });
    }

    @Override
    public E get(
        Long id)
    throws EntityNotFoundException
    {
        validateId(id);

        return getInternal(id, new Function<Long, E>()
        {
            @Override
            public E apply(
                Long id)
            {
                return repository.get(id);
            }
        });
    }

    @Override
    public List<E> listAll()
    {
        return repository.listAll();
    }

    @Override
    public E update(
        Function<E, Void> updater,
        Long id)
    {
        validateId(id);

        E entity = repository.findAndLock(id);

        updater.apply(entity);

        return entity;
    }

    @Override
    public E merge(
        E entity)
    throws EntityNotFoundException
    {
        validateEntity(entity);
        validateId(entity.getId());

        repository.findAndLock(entity.getId());
        repository.merge(entity);

        return entity;
    }

    @Override
    public void delete(
        Long id)
    throws EntityNotFoundException
    {
        validateId(id);

        E entity = getInternal(id, new Function<Long, E>()
        {
            @Override
            public E apply(
                Long id)
            {
                return repository.findAndLock(id);
            }
        });

        repository.remove(entity);
    }

    private void validateEntity(
        E entity)
    {
        Preconditions.checkNotNull(entity, "Entity cannot be null.");
    }

    private void validateId(
        Long id)
    {
        Preconditions.checkNotNull(id, "Entity id cannot be null.");
    }

    private E getInternal(
        Long id,
        Function<Long, E> getter)
    throws EntityNotFoundException
    {
        E entity = getter.apply(id);

        if (entity == null)
        {
            throw new EntityNotFoundException(
                String.format("Entity was not found by id: %s.", id));
        }

        return entity;
    }
}
