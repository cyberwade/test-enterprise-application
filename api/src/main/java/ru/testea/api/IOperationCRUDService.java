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
    <T extends Operation> List<T> listByType(
        Class<T> type);
}
