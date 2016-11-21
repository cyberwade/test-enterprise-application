package ru.testea.impl;

import org.springframework.stereotype.Repository;
import ru.testea.api.Client;
import ru.testea.api.Client_;

import javax.persistence.criteria.Root;

/**
 * Query repository for clients.
 *
 * @see Client
 *
 * @author Vadim Kuznetskikh
 */
@Repository
class ClientRepository
extends BaseEntityRepository<Client>
{
    /**
     * Creates a new query repository.
     */
    ClientRepository()
    {
    }

    @Override
    protected Class<Client> getEntityClass()
    {
        return Client.class;
    }

    @Override
    protected void initializeAssociations(
        Root<Client> root)
    {
        root.fetch(Client_.accounts);
    }
}
