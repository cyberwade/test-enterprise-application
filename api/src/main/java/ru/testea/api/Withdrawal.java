package ru.testea.api;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Account withdrawal.
 *
 * @author Vadim Kuznetskikh
 */
@Entity
@DiscriminatorValue("WITHDRAWAL")
public class Withdrawal
extends Operation
{
    /**
     * Creates a new account withdrawal.
     */
    public Withdrawal()
    {
    }
}
