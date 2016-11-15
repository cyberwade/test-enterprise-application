package ru.testea.api;

import com.google.common.base.MoreObjects;
import com.google.common.base.MoreObjects.ToStringHelper;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Base entity implementation.
 *
 * @author Vadim Kuznetskikh
 */
@MappedSuperclass
public abstract class BaseEntity
implements Serializable
{
    @NotNull
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    /**
     * Creates a new entity.
     */
    public BaseEntity()
    {
    }

    /**
     * Gets the entity identifier.
     *
     * @return entity identifier.
     */
    public Long getId()
    {
        return id;
    }

    /**
     * Sets the entity identifier.
     *
     * @param id
     *        entity identifier.
     */
    public void setId(
        Long id)
    {
        this.id = id;
    }

    @Override
    public String toString()
    {
        return toStringHelper().toString();
    }

    /**
     * Gets the {@link ToStringHelper} describing this entity.
     *
     * @return {@link ToStringHelper}.
     */
    protected ToStringHelper toStringHelper()
    {
        return MoreObjects.toStringHelper(this)
            .add("id", id);
    }
}
