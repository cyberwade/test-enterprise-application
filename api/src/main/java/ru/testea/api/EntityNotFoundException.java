package ru.testea.api;

/**
 * Exception throwing when entity was not found, for example, by identifier.
 *
 * @author Vadim Kuznetskikh
 */
public class EntityNotFoundException
extends BusinessLogicException
{
    /**
     * Creates a new exception with specified message.
     *
     * @param message
     *        message.
     */
    public EntityNotFoundException(
        String message)
    {
        super(message);
    }
}
