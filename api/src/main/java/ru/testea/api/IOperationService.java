package ru.testea.api;

import org.joda.money.Money;

/**
 * Operation service.
 *
 * @author Vadim Kuznetskikh
 */
public interface IOperationService
{
    /**
     * Deposits specified amount to the account with specified identifier.
     *
     * @param accountId
     *        account identifier.
     * @param amount
     *        deposition amount.
     * @return deposition.
     * @throws OperationException
     *         throws when business logic restrictions was violated on
     *         deposition processing.
     * @throws BusinessLogicException
     *         throws when business logic restrictions was violated.
     */
    Deposition deposit(
        Long accountId,
        Money amount)
    throws BusinessLogicException;

    /**
     * Withdraws specified amount from the account with specified identifier.
     *
     * @param accountId
     *        account identifier.
     * @param amount
     *        withdrawal amount.
     * @return withdrawal.
     * @throws NotSufficientFundsException
     *         throws when account had not sufficient funds on withdrawal
     *         processing.
     * @throws OperationException
     *         throws when business logic restrictions was violated on
     *         withdrawal processing.
     * @throws BusinessLogicException
     *         throws when business logic restrictions was violated.
     */
    Withdrawal withdraw(
        Long accountId,
        Money amount)
    throws BusinessLogicException;

    /**
     * Transfers specified amount from the source account to the target
     * account.
     *
     * @param sourceAccountId
     *        source account identifier.
     * @param targetAccountId
     *        target account identifier.
     * @param amount
     *        transfer amount.
     * @return transfer.
     * @throws NotSufficientFundsException
     *         throws when source account had not sufficient funds on
     *         withdrawal processing.
     * @throws OperationException
     *         throws when business logic restrictions was violated on transfer
     *         processing.
     * @throws BusinessLogicException
     *         throws when business logic restrictions was violated.
     */
    Transfer transfer(
        Long sourceAccountId,
        Long targetAccountId,
        Money amount)
    throws BusinessLogicException;
}
