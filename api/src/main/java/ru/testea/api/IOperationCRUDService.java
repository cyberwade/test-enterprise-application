package ru.testea.api;

import java.util.List;

/**
 * CRUD service for operations.
 *
 * @author Vadim Kuznetskikh
 */
public interface IOperationCRUDService
extends IBaseEntityCRUDService<Operation>
{
    /**
     * <p>
     * Gets the operations list by the specified operation type
     * and client identifier.
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
    <T extends Operation> List<T> listByTypeAndClientId(
        Class<T> type,
        Long clientId);
}
