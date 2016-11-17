package ru.testea.impl;

import org.springframework.stereotype.Service;
import ru.testea.api.Account;
import ru.testea.api.IAccountCRUDService;

/**
 * CRUD service implementation for accounts.
 *
 * @author Vadim Kuznetskikh
 */
@Service
public class AccountCRUDService
extends BaseEntityCRUDService<Account, AccountRepository>
implements IAccountCRUDService
{
    /**
     * Creates new CRUD service.
     */
    public AccountCRUDService()
    {
    }
}
