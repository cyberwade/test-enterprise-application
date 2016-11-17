package ru.testea.api;

import com.google.common.base.MoreObjects;
import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Type;
import org.joda.money.Money;
import org.joda.time.DateTime;

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
    columnDefinition = "operation_type",
    discriminatorType = DiscriminatorType.STRING)
@AttributeOverride(
    name = "id",
    column = @Column(
        name="operation_id",
        nullable = false))
@SequenceGenerator(
    schema = Constants.DB_SCHEMA_NAME,
    name = "sequence",
    sequenceName = "operation_id_sequence",
    allocationSize = 1)
public abstract class Operation
extends BaseEntity
{
    @NotNull
    @Column(name = "time", nullable = false)
    @Type(type = "dateTime")
    private DateTime time;

    @NotNull
    @Columns(columns = {
        @Column(name = "currency", nullable = false),
        @Column(name = "amount", nullable = false)
    })
    @Type(type = "money")
    private Money amount;

    /**
     * Creates a new operation.
     */
    public Operation()
    {
    }

    /**
     * Gets the time.
     *
     * @return time.
     */
    public DateTime getTime()
    {
        return time;
    }

    /**
     * Sets the time.
     *
     * @param time
     *        time.
     */
    public void setTime(
        DateTime time)
    {
        this.time = time;
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
            .add("time", time)
            .add("amount", amount);
    }
}
