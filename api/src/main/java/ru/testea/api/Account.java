package ru.testea.api;

import com.google.common.base.MoreObjects;
import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Type;
import org.joda.money.Money;
import org.joda.time.DateTime;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Account.
 *
 * @author Vadim Kuznetskikh
 */
@Entity
@Table(
    schema = Constants.DB_SCHEMA_NAME,
    name = "accounts")
@AttributeOverride(
    name = "id",
    column = @Column(
        name="account_id",
        nullable = false))
@SequenceGenerator(
    schema = Constants.DB_SCHEMA_NAME,
    name = "sequence",
    sequenceName = "account_id_sequence")
public class Account
extends BaseEntity
{
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "creation_time", nullable = false)
    @Type(type = "dateTime")
    private DateTime creationTime;

    @NotNull
    @Columns(columns = {
        @Column(name = "currency", nullable = false),
        @Column(name = "amount", nullable = false)
    })
    @Type(type = "money")
    private Money amount;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    /**
     * Creates a new account.
     */
    public Account()
    {
    }

    /**
     * Gets the name.
     *
     * @return name.
     */
    public String getName()
    {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name
     *        name.
     */
    public void setName(
        String name)
    {
        this.name = name;
    }

    /**
     * Gets the creation time.
     *
     * @return creation time.
     */
    public DateTime getCreationTime()
    {
        return creationTime;
    }

    /**
     * Sets the creation time.
     *
     * @param creationTime
     *        creation time.
     */
    public void setCreationTime(
        DateTime creationTime)
    {
        this.creationTime = creationTime;
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

    /**
     * Gets the client (account owner).
     *
     * @return client (account owner).
     */
    public Client getClient()
    {
        return client;
    }

    /**
     * Sets the client (account owner).
     *
     * @param client
     *        client (account owner).
     */
    public void setClient(
        Client client)
    {
        this.client = client;
    }

    @Override
    protected MoreObjects.ToStringHelper toStringHelper()
    {
        return super.toStringHelper()
            .add("name", name)
            .add("creationTime", creationTime)
            .add("amount", amount);
    }
}
