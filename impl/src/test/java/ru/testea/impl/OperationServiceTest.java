package ru.testea.impl;

import com.google.common.base.Function;
import org.joda.money.Money;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ru.testea.api.Account;
import ru.testea.api.BusinessLogicException;
import ru.testea.api.Constants;
import ru.testea.api.EntityNotFoundException;
import ru.testea.api.IAccountCRUDService;
import ru.testea.api.IClientCRUDService;
import ru.testea.api.IOperationCRUDService;
import ru.testea.api.Operation;
import ru.testea.api.Transfer;

import java.math.BigDecimal;

public class OperationServiceTest
{
    private static final Long SOURCE_ACCOUNT_ID = 1L;

    private static final Long TARGET_ACCOUNT_ID = 2L;

    private static final Money OPERATION_AMOUNT = Money.of(
        Constants.CURRENCY, BigDecimal.ONE);

    private OperationService service;

    @Mock
    private IAccountCRUDService accountCRUDService;

    @Mock
    private IOperationCRUDService operationCRUDService;

    @Mock
    private IClientCRUDService clientCRUDService;

    private Account sourceAccount;

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);

        service = new OperationService();
        service.accountCRUDService = accountCRUDService;
        service.operationCRUDService = operationCRUDService;
        service.clientCRUDService = clientCRUDService;

        sourceAccount = new Account();
        sourceAccount.setId(SOURCE_ACCOUNT_ID);
    }

    @Test(expected = NullPointerException.class)
    public void testDepositWithNullAccountId()
    throws BusinessLogicException
    {
        service.deposit(null, OPERATION_AMOUNT);
    }

    @Test(expected = NullPointerException.class)
    public void testDepositWithNullAmount()
    throws BusinessLogicException
    {
        service.deposit(SOURCE_ACCOUNT_ID, null);
    }

    @Test
    public void testDepositWithNotExistentAccount()
    throws BusinessLogicException
    {
        mockUpdateAccountWithException(SOURCE_ACCOUNT_ID);

        service.deposit(SOURCE_ACCOUNT_ID, OPERATION_AMOUNT);
    }

    @Test
    public void testDeposit()
    throws BusinessLogicException
    {
        mockUpdateAccount(SOURCE_ACCOUNT_ID, sourceAccount);

        verifyOperation(service.deposit(SOURCE_ACCOUNT_ID, OPERATION_AMOUNT));
    }

    @Test(expected = NullPointerException.class)
    public void testWithdrawWithNullAccountId()
    throws BusinessLogicException
    {
        service.withdraw(null, OPERATION_AMOUNT);
    }

    @Test(expected = NullPointerException.class)
    public void testWithdrawWithNullAmount()
    throws BusinessLogicException
    {
        service.withdraw(SOURCE_ACCOUNT_ID, null);
    }

    @Test
    public void testWithdrawWithNotExistentAccount()
    throws BusinessLogicException
    {
        mockUpdateAccountWithException(SOURCE_ACCOUNT_ID);

        service.withdraw(SOURCE_ACCOUNT_ID, OPERATION_AMOUNT);
    }

    @Test
    public void testWithdraw()
    throws BusinessLogicException
    {
        mockUpdateAccount(SOURCE_ACCOUNT_ID, sourceAccount);

        verifyOperation(service.withdraw(SOURCE_ACCOUNT_ID, OPERATION_AMOUNT));
    }

    @Test(expected = NullPointerException.class)
    public void testTransferWithNullSourceAccountId()
    throws BusinessLogicException
    {
        service.transfer(null, TARGET_ACCOUNT_ID, OPERATION_AMOUNT);
    }

    @Test(expected = NullPointerException.class)
    public void testTransferWithNullTargetAccountId()
    throws BusinessLogicException
    {
        service.transfer(SOURCE_ACCOUNT_ID, null, OPERATION_AMOUNT);
    }

    @Test(expected = NullPointerException.class)
    public void testTransferWithNullAmount()
    throws BusinessLogicException
    {
        service.transfer(SOURCE_ACCOUNT_ID, TARGET_ACCOUNT_ID, null);
    }

    @Test
    public void testTransferWithNotExistentSourceAccount()
    throws BusinessLogicException
    {
        mockUpdateAccountWithException(SOURCE_ACCOUNT_ID);

        service.transfer(SOURCE_ACCOUNT_ID, TARGET_ACCOUNT_ID,
            OPERATION_AMOUNT);
    }

    @Test
    public void testTransferWithNotExistentTargetAccount()
    throws BusinessLogicException
    {
        mockUpdateAccount(SOURCE_ACCOUNT_ID, sourceAccount);
        mockUpdateAccountWithException(TARGET_ACCOUNT_ID);

        service.transfer(SOURCE_ACCOUNT_ID, TARGET_ACCOUNT_ID,
            OPERATION_AMOUNT);
    }

    @Test
    public void testTransfer()
    throws BusinessLogicException
    {
        mockUpdateAccount(SOURCE_ACCOUNT_ID, sourceAccount);

        Account targetAccount = new Account();
        targetAccount.setId(TARGET_ACCOUNT_ID);
        mockUpdateAccount(TARGET_ACCOUNT_ID, targetAccount);

        Transfer transfer = service.transfer(SOURCE_ACCOUNT_ID,
            TARGET_ACCOUNT_ID, OPERATION_AMOUNT);

        verifyOperation(transfer);
        Assert.assertSame(targetAccount, transfer.getTargetAccount());
    }

    private void mockUpdateAccountWithException(
        Long id)
    throws EntityNotFoundException
    {
        Mockito.doThrow(EntityNotFoundException.class).when(accountCRUDService)
            .find(id);
    }

    @SuppressWarnings("unchecked")
    private void mockUpdateAccount(
        Long id,
        Account account)
    throws EntityNotFoundException
    {
        Mockito.doReturn(account).when(accountCRUDService).update(
            Mockito.any(Function.class), Mockito.eq(id));
    }

    private void verifyOperation(
        Operation operation)
    {
        Assert.assertSame(sourceAccount, operation.getSourceAccount());
        Assert.assertNotNull(operation.getTime());
        Assert.assertEquals(OPERATION_AMOUNT, operation.getAmount());

        Mockito.verify(operationCRUDService).create(operation);
    }
}
