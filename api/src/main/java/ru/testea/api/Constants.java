package ru.testea.api;

import org.joda.money.CurrencyUnit;

/**
 * API constants.
 *
 * @author Vadim Kuznetskikh
 */
public interface Constants
{
    /**
     * Default DB schema name.
     */
    String DB_SCHEMA_NAME = "test";

    /**
     * Default currency for operations.
     */
    CurrencyUnit CURRENCY = CurrencyUnit.of("RUB");
}
