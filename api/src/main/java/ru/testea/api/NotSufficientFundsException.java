package ru.testea.api;

/**
 * Exception throwing when account had not sufficient funds on operation
 * processing.
 *
 * @author Vadim Kuznetskikh
 */
public class NotSufficientFundsException
extends OperationException
{
    /**
     * Creates a new exception with specified message.
     *
     * @param message
     *        message.
     */
    public NotSufficientFundsException(
        String message)
    {
        super(message);
    }
}
