package ru.testea.impl;

import com.google.common.collect.ImmutableList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ru.testea.api.Operation;
import ru.testea.api.Transfer;

import java.util.List;

public class OperationCRUDServiceTest
{
    private OperationCRUDService service;

    @Mock
    private OperationRepository repository;

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);

        service = new OperationCRUDService();
        service.repository = repository;
    }

    @Test
    public void testListByTypeAndClientId()
    {
        Class<? extends Operation> type = Transfer.class;
        Long clientId = 1L;

        List<Transfer> operations = ImmutableList.of(new Transfer());
        Mockito.doReturn(operations).when(repository).listByTypeAndClientId(
            type, clientId);

        Assert.assertSame(operations, service.listByTypeAndClientId(
            type, clientId));
    }
}
