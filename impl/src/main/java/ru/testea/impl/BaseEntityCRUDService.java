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
    @Autowired
    protected R repository;

    /**
     * Creates new CRUD service.
     */
    public BaseEntityCRUDService()
    {
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
    public void update(
        Function<E, Void> updater,
        E entity)
    {
        Preconditions.checkNotNull(updater, "Updater cannot be null.");
        validateId(entity.getId());
        validateEntity(entity);

        repository.findAndLock(entity.getId());

        updater.apply(entity);
    }

    @Override
    public void delete(
        E entity)
    {
        validateEntity(entity);

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
        Preconditions.checkNotNull(id, "Id cannot be null.");
    }

    private E getInternal(
        Long id,
        Function<Long, E> getter)
    throws EntityNotFoundException
    {
        E entity = getter.apply(id);

        if (entity == null)
        {
            throw new EntityNotFoundException(String.format("Entity was not found by id: %s.", id));
        }

        return entity;
    }
}