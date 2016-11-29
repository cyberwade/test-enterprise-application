package ru.testea.web;

import com.google.common.base.Function;
import org.joda.money.Money;
import ru.testea.api.BusinessLogicException;
import ru.testea.api.Client;
import ru.testea.api.Deposition;
import ru.testea.api.IOperationCRUDService;
import ru.testea.api.IOperationService;
import ru.testea.api.Transfer;
import ru.testea.api.Withdrawal;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Operations bean.
 *
 * @author Vadim Kuznetskikh
 */
@ViewScoped
@Named
@ManagedBean
public class OperationBean
{
    @Inject
    private IOperationService operationService;

    @Inject
    private IOperationCRUDService operationCRUDService;

    @Inject
    private ClientBean clientBean;

    private List<Deposition> selectedClientDepositions;

    private List<Withdrawal> selectedClientWithdrawals;

    private List<Transfer> selectedClientTransfers;

    private Long operationSourceAccountId;

    private Long operationTargetAccountId;

    private Money operationAmount;

    private Boolean operationValid;

    private OperationType operationType;

    /**
     * Creates new bean.
     */
    public OperationBean()
    {
    }

    /**
     * Initializes this bean.
     */
    @PostConstruct
    public void initialize()
    {
        clientBean.setClientSelectionHandler(new Function<Client, Void>()
        {
            @Override
            public Void apply(
                Client client)
            {
                setSelectedClientDepositions(client != null
                    ? operationCRUDService.listByTypeAndClientId(
                        Deposition.class, client.getId())
                    : null);
                setSelectedClientWithdrawals(client != null
                    ? operationCRUDService.listByTypeAndClientId(
                        Withdrawal.class, client.getId())
                    : null);
                setSelectedClientTransfers(client != null
                    ? operationCRUDService.listByTypeAndClientId(
                        Transfer.class, client.getId())
                    : null);
                return null;
            }
        });
    }

    /**
     * Gets the selected client depositions list.
     *
     * @return selected client depositions list.
     */
    public List<Deposition> getSelectedClientDepositions()
    {
        return selectedClientDepositions;
    }

    /**
     * Sets the selected client depositions list.
     *
     * @param selectedClientDepositions
     *        selected client depositions list.
     */
    public void setSelectedClientDepositions(
        List<Deposition> selectedClientDepositions)
    {
        this.selectedClientDepositions = selectedClientDepositions;
    }

    /**
     * Gets the selected client withdrawals list.
     *
     * @return selected client withdrawals list.
     */
    public List<Withdrawal> getSelectedClientWithdrawals()
    {
        return selectedClientWithdrawals;
    }

    /**
     * Sets the selected client withdrawals list.
     *
     * @param selectedClientWithdrawals
     *        selected client withdrawals list.
     */
    public void setSelectedClientWithdrawals(
        List<Withdrawal> selectedClientWithdrawals)
    {
        this.selectedClientWithdrawals = selectedClientWithdrawals;
    }

    /**
     * Gets the selected client transfers list.
     *
     * @return selected client transfers list.
     */
    public List<Transfer> getSelectedClientTransfers()
    {
        return selectedClientTransfers;
    }

    /**
     * Sets the selected client transfers list.
     *
     * @param selectedClientTransfers
     *        selected client transfers list.
     */
    public void setSelectedClientTransfers(
        List<Transfer> selectedClientTransfers)
    {
        this.selectedClientTransfers = selectedClientTransfers;
    }

    /**
     * Gets the operation source account identifier.
     *
     * @return operation source account identifier.
     */
    public Long getOperationSourceAccountId()
    {
        return operationSourceAccountId;
    }

    /**
     * Sets the operation source account identifier.
     *
     * @param operationSourceAccountId
     *        operation source account identifier.
     */
    public void setOperationSourceAccountId(
        Long operationSourceAccountId)
    {
        this.operationSourceAccountId = operationSourceAccountId;
    }

    /**
     * Gets the operation target account identifier.
     *
     * @return operation target account identifier.
     */
    public Long getOperationTargetAccountId()
    {
        return operationTargetAccountId;
    }

    /**
     * Sets the operation target account identifier.
     *
     * @param operationTargetAccountId
     *        operation target account identifier.
     */
    public void setOperationTargetAccountId(
        Long operationTargetAccountId)
    {
        this.operationTargetAccountId = operationTargetAccountId;
    }

    /**
     * Gets the operation amount.
     *
     * @return operation amount.
     */
    public Money getOperationAmount()
    {
        return operationAmount;
    }

    /**
     * Sets the operation amount.
     *
     * @param operationAmount
     *        operation amount.
     */
    public void setOperationAmount(
        Money operationAmount)
    {
        this.operationAmount = operationAmount;
    }

    /**
     * Gets the flag of operation validity.
     *
     * @return flag of operation validity.
     */
    public Boolean getOperationValid()
    {
        return operationValid;
    }

    /**
     * Sets the flag of operation validity.
     *
     * @param operationValid
     *        flag of operation validity.
     */
    public void setOperationValid(
        Boolean operationValid)
    {
        this.operationValid = operationValid;
    }

    /**
     * Handles the selected client data change.
     */
    public void handleOperationChange()
    {
        setOperationValid(operationAmount != null);
    }

    /**
     * Performs the operation.
     *
     * @throws BusinessLogicException
     *         if business logic restrictions was violated.
     */
    public void operate()
    throws BusinessLogicException
    {
        switch (operationType)
        {
            case DEPOSITION:
                operationService.deposit(
                    operationSourceAccountId, operationAmount);
                break;

            case WITHDRAWAL:
                operationService.withdraw(
                    operationSourceAccountId, operationAmount);
                break;

            case TRANSFER:
                operationService.transfer(operationSourceAccountId,
                    operationTargetAccountId, operationAmount);
                break;
        }

        setOperationSourceAccountId(null);
        setOperationTargetAccountId(null);
        setOperationAmount(null);

        handleOperationChange();

        clientBean.reloadClients(clientBean.getSelectedClient());
    }

    /**
     * Sets the deposition operation type.
     */
    public void setDeposition()
    {
        operationType = OperationType.DEPOSITION;
    }

    /**
     * Sets the withdrawal operation type.
     */
    public void setWithdrawal()
    {
        operationType = OperationType.WITHDRAWAL;
    }

    /**
     * Sets the transfer operation type.
     */
    public void setTransfer()
    {
        operationType = OperationType.TRANSFER;
    }

    /**
     * Gets the flag of transfer operation type.
     *
     * @return flag of transfer operation type.
     */
    public boolean isTransferOperation()
    {
        return OperationType.TRANSFER.equals(operationType);
    }

    private enum OperationType
    {
        DEPOSITION,

        WITHDRAWAL,

        TRANSFER;
    }
}
