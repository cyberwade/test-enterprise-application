package ru.testea.impl;

import org.hibernate.boot.model.TypeContributions;
import org.hibernate.dialect.PostgresPlusDialect;
import org.hibernate.service.ServiceRegistry;
import org.jadira.usertype.dateandtime.joda.PersistentDateTime;
import org.jadira.usertype.moneyandcurrency.joda.PersistentMoneyAmountAndCurrency;

/**
 * <p>
 * Test PostgreSQL dialect.
 * </p>
 *
 * <p>
 * Registers custom user data types.
 * </p>
 *
 * @author Vadim Kuznetskikh
 */
public class TestPostgreSQLDialect
extends PostgresPlusDialect
{
    /**
     * Creates new PostgreSQL dialect.
     */
    public TestPostgreSQLDialect()
    {
    }

    @Override
    public void contributeTypes(
        TypeContributions typeContributions,
        ServiceRegistry serviceRegistry)
    {
        super.contributeTypes(typeContributions, serviceRegistry);

        typeContributions.contributeType(
            new PersistentMoneyAmountAndCurrency(), "money");
        typeContributions.contributeType(
            new PersistentDateTime(), "dateTime");
    }
}
