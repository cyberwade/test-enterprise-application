package ru.testea.impl;

import org.springframework.stereotype.Service;
import ru.testea.api.IOperationCRUDService;
import ru.testea.api.Operation;

import java.util.List;

/**
 * CRUD service implementation for operations.
 *
 * @author Vadim Kuznetskikh
 */
@Service
public class OperationCRUDService
extends BaseEntityCRUDService<Operation, OperationRepository>
implements IOperationCRUDService
{
    /**
     * Creates new CRUD service.
     */
    public OperationCRUDService()
    {
    }

    @Override
    public <T extends Operation> List<T> listByTypeAndClientId(
        Class<T> type,
        Long clientId)
    {
        return repository.listByTypeAndClientId(type, clientId);
    }
}
