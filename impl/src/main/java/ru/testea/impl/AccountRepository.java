package ru.testea.impl;

import ru.testea.api.Account;

/**
 * Query repository for accounts.
 *
 * @see Account
 *
 * @author Vadim Kuznetskikh
 */
class AccountRepository
extends BaseRepository<Account>
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
