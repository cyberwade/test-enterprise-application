package ru.testea.impl;

import org.springframework.stereotype.Repository;
import ru.testea.api.Account;
import ru.testea.api.Account_;
import ru.testea.api.Client;
import ru.testea.api.Client_;
import ru.testea.api.Operation;
import ru.testea.api.Operation_;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Query repository for operations.
 *
 * @see Operation
 *
 * @author Vadim Kuznetskikh
 */
@Repository
class OperationRepository
extends BaseEntityRepository<Operation>
{
    /**
     * Creates a new query repository.
     */
    OperationRepository()
    {
    }

    /**
     * <p>
     * Gets the operations list by the specified operation type and client
     * identifier.
     * </p>
     *
     * <p>
     * The result list will be ordered by operation identifier.
     * </p>
     *
     * @param type
     *        type (class) of the operation.
     * @param clientId
     *        client identifier.
     * @param <T>
     *        result operation type.
     * @return operations list.
     */
    public <T extends Operation> List<T> listByTypeAndClientId(
        Class<T> type,
        Long clientId)
    {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(type);

        Root<T> root = criteriaQuery.from(type);
        Join<Account, Client> clientJoin = root
            .join(Operation_.sourceAccount)
            .join(Account_.client);

        criteriaQuery.where(
            criteriaBuilder.equal(root.type(), type),
            criteriaBuilder.equal(clientJoin.get(Client_.id), clientId));

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    protected Class<Operation> getEntityClass()
    {
        return Operation.class;
    }
}
