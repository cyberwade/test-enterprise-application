package ru.testea.api;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Account deposition.
 *
 * @author Vadim Kuznetskikh
 */
@Entity
@DiscriminatorValue("DEPOSITION")
public class Deposition
extends Operation
{
    /**
     * Creates a new account deposition.
     */
    public Deposition()
    {
    }
}
