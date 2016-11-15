package ru.testea.api;

import com.google.common.base.MoreObjects;
import org.joda.money.Money;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Operation.
 *
 * @author Vadim Kuznetskikh.
 */
@Entity
@Table(
    schema = Constants.DB_SCHEMA_NAME,
    name = "operations")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
    name = "operation_type",
    discriminatorType = DiscriminatorType.STRING)
@AttributeOverride(
    name = "id",
    column = @Column(
        name="operation_id",
        nullable = false))
@SequenceGenerator(
    schema = Constants.DB_SCHEMA_NAME,
    name = "operation_id_sequence")
public abstract class Operation
extends BaseEntity
{
    @NotNull
    @Column(name = "amount", nullable = false)
    private Money amount;

    /**
     * Creates a new operation.
     */
    public Operation()
    {
    }

    /**
     * Gets the amount.
     *
     * @return amount.
     */
    public Money getAmount()
    {
        return amount;
    }

    /**
     * Sets the amount.
     *
     * @param amount
     *        amount.
     */
    public void setAmount(
        Money amount)
    {
        this.amount = amount;
    }

    @Override
    protected MoreObjects.ToStringHelper toStringHelper()
    {
        return super.toStringHelper()
            .add("amount", amount);
    }
}
