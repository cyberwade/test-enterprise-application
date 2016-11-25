package ru.testea.web;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.richfaces.component.UIExtendedDataTable;
import ru.testea.api.Account;
import ru.testea.api.Client;
import ru.testea.api.EntityNotFoundException;
import ru.testea.api.IClientCRUDService;

import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Base clients bean.
 *
 * @author Vadim Kuznetskikh
 */
public abstract class BaseClientBean
{
    /**
     * CRUD service for clients.
     */
    @Inject
    protected IClientCRUDService clientCRUDService;

    /**
     * Clients list.
     */
    protected List<Client> clients;

    /**
     * Collection of the selected client indexes.
     */
    protected Collection<Integer> clientSelection;

    /**
     * Selected client.
     */
    protected Client selectedClient;

    /**
     * Selected client accounts.
     */
    protected List<Account> selectedClientAccounts;

    /**
     * Creates new bean.
     */
    public BaseClientBean()
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
                    : Collections.<Account>emptyList())
            : null);
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
}
