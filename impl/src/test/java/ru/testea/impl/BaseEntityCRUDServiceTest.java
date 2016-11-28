package ru.testea.impl;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ru.testea.api.BaseEntity;
import ru.testea.api.EntityNotFoundException;

import java.util.List;

@SuppressWarnings("unchecked")
public class BaseEntityCRUDServiceTest
{
    private static final Long ENTITY_ID = 1L;

    private BaseEntityCRUDService service;

    @Mock
    private BaseEntityRepository<BaseEntity> repository;

    @Mock
    private BaseEntity entity;

    @Mock
    private Function<BaseEntity, Void> updater;

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);

        service = new TestBaseEntityCRUDService();
        service.repository = repository;
    }

    @Test
    public void testInitialize()
    {
        service.repository = null;

        service.initialize(repository);

        Assert.assertSame(repository, service.repository);
    }

    @Test(expected = NullPointerException.class)
    public void testCreateWithNullEntity()
    {
        service.create(null);
    }

    @Test
    public void testCreate()
    {
        service.create(entity);

        Mockito.verify(repository).persist(entity);
    }

    @Test(expected = NullPointerException.class)
    public void testFindWithNullId()
    throws EntityNotFoundException
    {
        service.find(null);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testEntityNotFoundById()
    throws EntityNotFoundException
    {
        service.find(ENTITY_ID);
    }

    @Test
    public void testFind()
    throws EntityNotFoundException
    {
        Mockito.doReturn(entity).when(repository).find(ENTITY_ID);

        Assert.assertSame(entity, service.find(ENTITY_ID));
    }

    @Test(expected = NullPointerException.class)
    public void testGetWithNullId()
    throws EntityNotFoundException
    {
        service.get(null);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testEntityNotGottenById()
    throws EntityNotFoundException
    {
        service.get(ENTITY_ID);
    }

    @Test
    public void testGet()
    throws EntityNotFoundException
    {
        Mockito.doReturn(entity).when(repository).get(ENTITY_ID);

        Assert.assertSame(entity, service.get(ENTITY_ID));
    }

    @Test
    public void testListAll()
    throws EntityNotFoundException
    {
        List<BaseEntity> entities = ImmutableList.of(entity);

        Mockito.doReturn(entities).when(repository).listAll();

        Assert.assertSame(entities, service.listAll());
    }

    @Test(expected = NullPointerException.class)
    public void testUpdateWithNullId()
    throws EntityNotFoundException
    {
        service.update(updater, null);
    }

    @Test(expected = NullPointerException.class)
    public void testUpdateWithNullUpdater()
    throws EntityNotFoundException
    {
        service.update(null, ENTITY_ID);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testUpdateWithNotExistentEntity()
    throws EntityNotFoundException
    {
        service.update(updater, ENTITY_ID);
    }

    @Test
    public void testUpdate()
    throws EntityNotFoundException
    {
        mockFindAndLock();

        Assert.assertSame(entity, service.update(updater, ENTITY_ID));

        Mockito.verify(updater).apply(entity);
    }

    @Test(expected = NullPointerException.class)
    public void testMergeWithNullEntity()
    throws EntityNotFoundException
    {
        service.merge(null);
    }

    @Test(expected = NullPointerException.class)
    public void testMergeWithNullEntityId()
    throws EntityNotFoundException
    {
        mockEntityId(null);

        service.merge(entity);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testMergeWithNotExistentEntity()
    throws EntityNotFoundException
    {
        mockEntityId(ENTITY_ID);

        service.merge(entity);
    }

    @Test
    public void testMerge()
    throws EntityNotFoundException
    {
        mockEntityId(ENTITY_ID);
        mockFindAndLock();

        Assert.assertSame(entity, service.merge(entity));

        Mockito.verify(repository).merge(entity);
    }

    @Test(expected = NullPointerException.class)
    public void testDeleteWithNullId()
    throws EntityNotFoundException
    {
        service.delete(null);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testDeleteWithNotExistentEntity()
    throws EntityNotFoundException
    {
        service.delete(ENTITY_ID);
    }

    @Test
    public void testDelete()
    throws EntityNotFoundException
    {
        mockFindAndLock();

        service.delete(ENTITY_ID);

        Mockito.verify(repository).remove(entity);
    }

    private void mockFindAndLock()
    {
        Mockito.doReturn(entity).when(repository).findAndLock(ENTITY_ID);
    }

    private void mockEntityId(
        Long id)
    {
        Mockito.doReturn(id).when(entity).getId();
    }

    private class TestBaseEntityCRUDService
    extends BaseEntityCRUDService<BaseEntity, BaseEntityRepository<BaseEntity>>
    {
        private TestBaseEntityCRUDService()
        {
        }
    }
}
