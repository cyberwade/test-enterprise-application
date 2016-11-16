package ru.testea.api;

import com.google.common.base.MoreObjects;
import org.joda.time.LocalDate;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * Client.
 *
 * @author Vadim Kuznetskikh
 */
@Entity
@Table(
    schema = Constants.DB_SCHEMA_NAME,
    name = "clients")
@AttributeOverride(
    name = "id",
    column = @Column(
        name="client_id",
        nullable = false))
@SequenceGenerator(
    schema = Constants.DB_SCHEMA_NAME,
    name = "sequence",
    sequenceName = "client_id_sequence")
public class Client
extends BaseEntity
{
    @NotNull
    @Column(name = "full_name", nullable = false)
    private String fullName;

    @NotNull
    @Column(name = "address", nullable = false)
    private String address;

    @NotNull
    @Column(name = "birthday", nullable = false)
    private LocalDate birthday;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "client")
    private Set<Account> accounts;

    /**
     * Creates a new client.
     */
    public Client()
    {
    }

    /**
     * Gets the full name.
     *
     * @return full name.
     */
    public String getFullName()
    {
        return fullName;
    }

    /**
     * Sets the client full name.
     *
     * @param fullName
     *        client full name.
     */
    public void setFullName(
        String fullName)
    {
        this.fullName = fullName;
    }

    /**
     * Gets the address.
     *
     * @return address.
     */
    public String getAddress()
    {
        return address;
    }

    /**
     * Sets the address.
     *
     * @param address
     *        address.
     */
    public void setAddress(
        String address)
    {
        this.address = address;
    }

    /**
     * Gets the birthday.
     *
     * @return birthday.
     */
    public LocalDate getBirthday()
    {
        return birthday;
    }

    /**
     * Sets the birthday.
     *
     * @param birthday
     *        birthday.
     */
    public void setBirthday(
        LocalDate birthday)
    {
        this.birthday = birthday;
    }

    /**
     * Gets the set of the client own accounts.
     *
     * @return set of the client own accounts.
     */
    public Set<Account> getAccounts()
    {
        return accounts;
    }

    /**
     * Sets the set of the accounts.
     *
     * @param accounts
     *        set of the accounts.
     */
    public void setAccounts(
        Set<Account> accounts)
    {
        this.accounts = accounts;
    }

    @Override
    protected MoreObjects.ToStringHelper toStringHelper()
    {
        return super.toStringHelper()
            .add("fullName", fullName)
            .add("address", address)
            .add("birthday", birthday);
    }
}
