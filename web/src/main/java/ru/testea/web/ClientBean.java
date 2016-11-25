package ru.testea.web;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableSet;
import org.joda.time.DateTime;
import ru.testea.api.Account;
import ru.testea.api.Client;
import ru.testea.api.EntityNotFoundException;

import javax.faces.bean.ViewScoped;
import javax.inject.Named;

/**
 * Clients bean.
 *
 * @author Vadim Kuznetskikh
 */
@ViewScoped
@Named
public class ClientBean
extends BaseClientBean
{
    private Boolean selectedClientValid;

    /**
     * Creates new bean.
     */
    public ClientBean()
    {
    }

    @Override
    public void setSelectedClient(
        Client selectedClient)
    {
        super.setSelectedClient(selectedClient);

        handleSelectedClientChange();
    }

    /**
     * Adds new client.
     *
     * @throws EntityNotFoundException
     *         if client was not found by identifier.
     */
    public void addClient()
    throws EntityNotFoundException
    {
        reloadClients(new Client());
    }

    /**
     * Saves the selected client.
     *
     * @throws EntityNotFoundException
     *         if client was not found by identifier.
     */
    public void saveClient()
    throws EntityNotFoundException
    {
        selectedClient.setAccounts(
            ImmutableSet.copyOf(selectedClientAccounts));

        if (selectedClient.getId() != null)
        {
            clientCRUDService.merge(selectedClient);
        }
        else
        {
            clientCRUDService.create(selectedClient);
        }

        reloadClients(selectedClient);
    }

    /**
     * Deletes the selected client.
     *
     * @throws EntityNotFoundException
     *         if client was not found by identifier.
     */
    public void deleteClient()
    throws EntityNotFoundException
    {
        clientCRUDService.delete(selectedClient.getId());

        reloadClients(null);
    }

    /**
     * Returns the flag of selected client data validity.
     *
     * @return flag of selected client data validity.
     */
    public Boolean getSelectedClientValid()
    {
        return selectedClientValid;
    }

    /**
     * Sets the flag of selected client data validity.
     *
     * @param selectedClientValid
     *        flag of selected client data validity.
     */
    public void setSelectedClientValid(
        Boolean selectedClientValid)
    {
        this.selectedClientValid = selectedClientValid;
    }

    /**
     * Handles the selected client data change.
     */
    public void handleSelectedClientChange()
    {
        boolean clientValid = selectedClient != null
            && !Strings.nullToEmpty(selectedClient.getFullName()).trim().isEmpty()
            && !Strings.nullToEmpty(selectedClient.getAddress()).trim().isEmpty()
            && selectedClient.getBirthday() != null;

        boolean accountsValid = true;
        if (selectedClientAccounts != null)
        {
            for (Account account : selectedClientAccounts)
            {
                if (Strings.nullToEmpty(account.getName()).trim().isEmpty()
                    || account.getAmount() == null)
                {
                    accountsValid = false;
                    break;
                }
            }
        }

        setSelectedClientValid(clientValid && accountsValid);
    }

    /**
     * Adds new account.
     */
    public void addAccount()
    {
        Account account = new Account();
        account.setCreationTime(DateTime.now());
        account.setClient(selectedClient);
        selectedClientAccounts.add(account);

        handleSelectedClientChange();
    }

    /**
     * Deletes specified account of the client.
     *
     * @param account
     *        account.
     */
    public void deleteAccount(
        Account account)
    {
        selectedClientAccounts.remove(account);

        handleSelectedClientChange();
    }
}
