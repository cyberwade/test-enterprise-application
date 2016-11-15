package ru.testea.api;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Account payment.
 *
 * @author Vadim Kuznetskikh
 */
@Entity
@DiscriminatorValue("PAYMENT")
public class Payment
extends Operation
{
    /**
     * Creates a new account payment.
     */
    public Payment()
    {
    }
}
