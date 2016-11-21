package ru.testea.web;

import ru.testea.api.IClientCRUDService;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Clients bean.
 *
 * @author Vadim Kuznetskikh
 */
@ViewScoped
@Named
public class ClientBean
{
    @Inject
    private IClientCRUDService clientCRUDService;

    /**
     * Creates new bean.
     */
    public ClientBean()
    {
    }

    /**
     * Gets the clients list size.
     *
     * @return clients list size.
     */
    public String getSize()
    {
        return clientCRUDService.listAll().size() + "";
    }
}
