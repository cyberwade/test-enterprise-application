package ru.testea.api;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Account receipt.
 *
 * @author Vadim Kuznetskikh
 */
@Entity
@DiscriminatorValue("RECEIPT")
public class Receipt
extends Operation
{
    /**
     * Creates a new account receipt.
     */
    public Receipt()
    {
    }
}
