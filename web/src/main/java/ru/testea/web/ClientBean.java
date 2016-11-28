package ru.testea.web;

import com.google.common.base.Function;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.joda.time.DateTime;
import org.richfaces.component.UIExtendedDataTable;
import ru.testea.api.Account;
import ru.testea.api.Client;
import ru.testea.api.EntityNotFoundException;
import ru.testea.api.IClientCRUDService;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Clients bean.
 *
 * @author Vadim Kuznetskikh
 */
@ViewScoped
@Named
@ManagedBean
public class ClientBean
{
    @Inject
    private IClientCRUDService clientCRUDService;

    private List<Client> clients;

    private Collection<Integer> clientSelection;

    private Client selectedClient;

    private Function<Client, Void> clientSelectionHandler;

    private List<Account> selectedClientAccounts;

    private Boolean selectedClientValid;

    /**
     * Creates new bean.
     */
    public ClientBean()
    {
    }

    /**
     * Gets the clients list.
     *
     * @return clients list.
     * @throws EntityNotFoundException
     *         if client was not found by identifier.
     */
    public List<Client> getClients()
    throws EntityNotFoundException
    {
        if (clients == null)
        {
            reloadClients(null);
        }

        return clients;
    }

    /**
     * Sets the clients list.
     *
     * @param clients
     *        clients list.
     */
    public void setClients(
        List<Client> clients)
    {
        this.clients = clients;
    }

    /**
     * Gets the clients table selection.
     *
     * @return clients table selection.
     */
    public Collection<Integer> getClientSelection()
    {
        return clientSelection;
    }

    /**
     * Sets the clients table selection.
     *
     * @param clientSelection
     *        clients table selection.
     */
    public void setClientSelection(
        Collection<Integer> clientSelection)
    {
        this.clientSelection = clientSelection;
    }

    /**
     * Handles the client table selection event.
     *
     * @param event
     *        clients table selection event.
     * @throws EntityNotFoundException
     *         if client was not found by identifier.
     */
    public void handleSelection(
        AjaxBehaviorEvent event)
    throws EntityNotFoundException
    {
        if (clientSelection.isEmpty())
        {
            setSelectedClient(null);
        }
        else
        {
            for (Object selected : clientSelection)
            {
                UIExtendedDataTable table =
                    (UIExtendedDataTable) event.getComponent();

                table.setRowKey(selected);
                if (table.isRowAvailable())
                {
                    setSelectedClient(clientCRUDService.get(
                        ((Client) table.getRowData()).getId()));
                }
            }
        }
    }

    /**
     * Gets the selected client.
     *
     * @return selected client.
     */
    public Client getSelectedClient()
    {
        return selectedClient;
    }

    /**
     * Sets the selected client.
     *
     * @param selectedClient
     *        selected client.
     */
    public void setSelectedClient(
        Client selectedClient)
    {
        this.selectedClient = selectedClient;

        setSelectedClientAccounts(selectedClient != null
            ? Lists.newArrayList(
                selectedClient.getAccounts() != null
                    ? selectedClient.getAccounts()
                    : Collections.<Account>emptyList()) : null);

        handleSelectedClientChange();

        if (clientSelectionHandler != null)
        {
            clientSelectionHandler.apply(selectedClient);
        }
    }

    /**
     * Sets the handling function for client selection.
     *
     * @param clientSelectionHandler
     *        handling function for client selection.
     */
    public void setClientSelectionHandler(
        Function<Client, Void> clientSelectionHandler)
    {
        this.clientSelectionHandler = clientSelectionHandler;
    }

    /**
     * Gets the selected client accounts list.
     *
     * @return selected client accounts list.
     */
    public List<Account> getSelectedClientAccounts()
    {
        return selectedClientAccounts;
    }

    /**
     * Sets the selected client accounts list.
     *
     * @param selectedClientAccounts
     *        selected client accounts list.
     */
    public void setSelectedClientAccounts(
        List<Account> selectedClientAccounts)
    {
        this.selectedClientAccounts = selectedClientAccounts;
    }

    /**
     * Reloads the clients list.
     *
     * @param client
     *        selected client.
     * @throws EntityNotFoundException
     *         if client was not found by identifier.
     */
    public void reloadClients(
        Client client)
    throws EntityNotFoundException
    {
        setClients(clientCRUDService.listAll());

        if (client != null && client.getId() != null)
        {
            for (int i = 0; i < clients.size(); i++)
            {
                Client current = clients.get(i);
                if (current.getId().equals(client.getId()))
                {
                    setClientSelection(Sets.newHashSet(i));
                    setSelectedClient(clientCRUDService.get(client.getId()));
                    break;
                }
            }
        }
        else
        {
            setClientSelection(null);
            setSelectedClient(client);
        }
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
