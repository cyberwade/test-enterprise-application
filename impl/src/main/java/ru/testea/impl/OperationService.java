package ru.testea.impl;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import org.joda.money.Money;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.testea.api.Account;
import ru.testea.api.BusinessLogicException;
import ru.testea.api.Constants;
import ru.testea.api.Deposition;
import ru.testea.api.EntityNotFoundException;
import ru.testea.api.IAccountCRUDService;
import ru.testea.api.IClientCRUDService;
import ru.testea.api.IOperationCRUDService;
import ru.testea.api.IOperationService;
import ru.testea.api.Operation;
import ru.testea.api.Transfer;
import ru.testea.api.Withdrawal;

import javax.inject.Inject;

/**
 * Operation service implementation.
 *
 * @author Vadim Kuznetskikh
 */
@Service
@Transactional
public class OperationService
implements IOperationService
{
    /**
     * CRUD service for accounts.
     */
    @Inject
    IAccountCRUDService accountCRUDService;

    /**
     * CRUD service for operations.
     */
    @Inject
    IOperationCRUDService operationCRUDService;

    /**
     * CRUD service for clients.
     */
    @Inject
    IClientCRUDService clientCRUDService;

    /**
     * Creates new service.
     */
    public OperationService()
    {
    }

    @Override
    public Deposition deposit(
        Long accountId,
        final Money amount)
    throws BusinessLogicException
    {
        return operate(getDepositionFunction(amount), accountId, amount,
            new Deposition());
    }

    @Override
    public Withdrawal withdraw(
        Long accountId,
        final Money amount)
    throws BusinessLogicException
    {
        return operate(getWithdrawalFunction(amount), accountId, amount,
            new Withdrawal());
    }

    @Override
    public Transfer transfer(
        Long sourceAccountId,
        Long targetAccountId,
        Money amount)
    throws BusinessLogicException
    {
        validateAccountId(sourceAccountId);
        validateAccountId(targetAccountId);
        validateAmount(amount);

        Transfer transfer = new Transfer();
        transfer.setTargetAccount(
            accountCRUDService.update(getDepositionFunction(amount),
            targetAccountId));

        return createOperation(getWithdrawalFunction(amount), sourceAccountId,
            amount, transfer);
    }

    private void validateAmount(
        Money amount)
    {
        Preconditions.checkNotNull(
            amount, "Operation amount for cannot be null.");
        Preconditions.checkArgument(
            amount.isGreaterThan(Money.zero(Constants.CURRENCY)),
            "Operation amount must be greater than zero.");
    }

    private void validateAccountId(
        Long id)
    {
        Preconditions.checkNotNull(id, "Account id cannot be null.");
    }

    private <T extends Operation> T operate(
        Function<Account, Void> updater,
        Long accountId,
        final Money amount,
        T operation)
    throws BusinessLogicException
    {
        validateAccountId(accountId);
        validateAmount(amount);

        return createOperation(updater, accountId, amount, operation);
    }

    private <T extends Operation> T createOperation(
        Function<Account, Void> updater,
        Long accountId,
        final Money amount,
        T operation)
    throws EntityNotFoundException
    {
        operation.setSourceAccount(
            accountCRUDService.update(updater, accountId));
        operation.setTime(DateTime.now());
        operation.setAmount(amount);
        operationCRUDService.create(operation);

        return operation;
    }

    private Function<Account, Void> getWithdrawalFunction(
        final Money amount)
    {
        return new Function<Account, Void>()
        {
            @Override
            public Void apply(
                Account account)
            {
                // Update account amount.
                account.setAmount(account.getAmount().minus(amount));
                return null;
            }
        };
    }

    private Function<Account, Void> getDepositionFunction(
        final Money amount)
    {
        return new Function<Account, Void>()
        {
            @Override
            public Void apply(
                Account account)
            {
                // Update account amount.
                account.setAmount(account.getAmount().plus(amount));
                return null;
            }
        };
    }
}
