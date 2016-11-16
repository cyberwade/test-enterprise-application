package ru.testea.impl;

import ru.testea.api.Operation;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Query repository for operations.
 *
 * @see Operation
 *
 * @author Vadim Kuznetskikh
 */
class OperationRepository
extends BaseRepository<Operation>
{
    /**
     * Creates a new query repository.
     */
    OperationRepository()
    {
    }

    /**
     * <p>
     * Gets the operations list by the specified operation type.
     * </p>
     *
     * <p>
     * The result list will be ordered by operation identifier.
     * </p>
     *
     * @param type
     *        type (class) of the operation.
     * @param <T>
     *        result operation type.
     * @return operations list.
     */
    public <T extends Operation> List<T> listByType(
        Class<T> type)
    {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(type);

        Root<T> root = criteriaQuery.from(type);

        criteriaQuery.where(criteriaBuilder.equal(root.type(), type));

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    protected Class<Operation> getEntityClass()
    {
        return Operation.class;
    }
}
