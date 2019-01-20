package com.samkruglov.bank.domain.common;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Optional;

/**
 * Provides boilerplate implementations of {@code hashcode} & {@code equals} that are based on assumption that "this"
 * is a JPA entity class.
 */
@MappedSuperclass
public abstract class IdentifiableAdapter<T extends Serializable> implements Identifiable<T> {

    @Id
    @GenericGenerator(
            name = "SequencePerEntityGenerator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = @Parameter(name = "prefer_sequence_per_entity", value = "true")
    )
    @GeneratedValue(generator = "SequencePerEntityGenerator")
    private T id;

    /**
     * Is empty if entity is detached (not yet managed).
     *
     * @apiNote this triggers lazy loading in case of Hibernate.
     * For getting an id without fetching the whole entity use {@link #getId} instead.
     */
    @Override
    public Optional<T> getIdOptional() {
        return Optional.ofNullable(id);
    }

    @Override
    public T getId() {
        return id;
    }

    /**
     * Returns a constant to be consistent with the same object having and not having an id.
     *
     * @implSpec should be overridden to use a business key
     * @see <a href="https://vladmihalcea.com/2016/06/06/how-to-implement-equals-and-hashcode-using-the-
     * jpa-entity-identifier/">Vlad's blogpost</a>
     */
    @Override
    public int hashCode() {
        return 0;
    }

    /**
     * Also compares by id if it is not null.
     *
     * @implSpec should be overridden and called with additional check for business key. Do not call super but copy paste this method
     * e.g. <pre>{@code if (this == o) {
     *     return true;
     * }
     * if (o == null || getClass() != o.getClass()) {
     *     return false;
     * }
     * IdentifiableImpl that = (IdentifiableImpl) o;
     * if (this.getId() != null && that.getId() != null) {
     *     return this.getId().equals(that.getId());
     * }
     * return Objects.equals(this.businessKey, that.businessKey);}</pre>
     * {@link #hashCode} must also be overridden
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Identifiable that = (Identifiable) o;
        if (this.getId() != null && that.getId() != null) {
            return this.getId().equals(that.getId());
        }
        return false;
    }

    @Override
    public String toString() {
        return "id=" + id;
    }
}
