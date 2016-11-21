package ru.testea.api;

/**
 * Exception throwing when business logic restrictions was violated on
 * operation processing.
 *
 * @author Vadim Kuznetskikh
 */
public class OperationException
extends BusinessLogicException
{
    /**
     * Creates a new exception with specified message.
     *
     * @param message
     *        message.
     */
    public OperationException(
        String message)
    {
        super(message);
    }
}
