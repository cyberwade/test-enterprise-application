package ru.testea.impl;

import org.springframework.stereotype.Repository;
import ru.testea.api.Account;

/**
 * Query repository for accounts.
 *
 * @see Account
 *
 * @author Vadim Kuznetskikh
 */
@Repository
class AccountRepository
extends BaseEntityRepository<Account>
{
    /**
     * Creates a new query repository.
     */
    AccountRepository()
    {
    }

    @Override
    protected Class<Account> getEntityClass()
    {
        return Account.class;
    }
}
