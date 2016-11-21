package ru.testea.api;

/**
 * Exception throwing when business logic restrictions was violated.
 *
 * @author Vadim Kuznetskikh
 */
public class BusinessLogicException
extends Exception
{
    /**
     * Creates a new exception with specified message.
     *
     * @param message
     *        message.
     */
    public BusinessLogicException(
        String message)
    {
        super(message);
    }
}
