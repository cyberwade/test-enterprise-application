package ru.testea.impl;

import org.springframework.stereotype.Service;
import ru.testea.api.Client;
import ru.testea.api.IClientCRUDService;

/**
 * CRUD service implementation for clients.
 *
 * @author Vadim Kuznetskikh
 */
@Service
public class ClientCRUDService
extends BaseEntityCRUDService<Client, ClientRepository>
implements IClientCRUDService
{
    /**
     * Creates new CRUD service.
     */
    public ClientCRUDService()
    {
    }
}
