package com.samkruglov.bank.domain;

import com.samkruglov.bank.domain.common.AuditableEntity;
import com.samkruglov.bank.domain.common.Identifiable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.val;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "accounts")
public class Account extends AuditableEntity<Long> {

    @Setter(AccessLevel.PACKAGE)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User user;

    @NonNull
    @Column(length = 20)
    private String number;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Type type;

    @SuppressWarnings("FieldNotUsedInToString")
    @Getter(AccessLevel.NONE)
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Card> cards = new HashSet<>();

    public Account(@NonNull User user, @NonNull String number, Type type) {
        this.user = user;
        user.addAccount(this);
        this.number = number;
        this.type = Optional.ofNullable(type).orElse(Type.REGULAR);
    }

    /**
     * @implNote can only be empty if {@link User#removeAccount} has been called
     */
    public Optional<User> getUser() {
        return Optional.ofNullable(user);
    }

    public boolean addCard(@NonNull Card card) {
        card.setAccount(this);
        return cards.add(card);
    }

    public boolean removeCard(@NonNull Card card) {
        val removed = cards.remove(card);
        card.setAccount(null);
        return removed;
    }

    public boolean removeCardById(@NonNull Long cardId) {
        return cards.stream()
                .filter(card -> card.getIdOptional().filter(id -> id.equals(cardId)).isPresent())
                .findAny()
                .map(cards::remove)
                .orElse(false);
    }

    public Set<Card> getReadOnlyCards() {
        return Collections.unmodifiableSet(cards);
    }

    @Override
    public String toString() {
        return "Account(super=" + super.toString()
                + ", userId=" + getUser().map(Identifiable::getId).orElse(null)
                + ", number=" + number
                + ", type=" + type
                + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Account that = (Account) o;
        if (this.getId() != null && that.getId() != null) {
            return this.getId().equals(that.getId());
        }
        return Objects.equals(this.number, that.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }

    public enum Type {
        BUSINESS,
        REGULAR
    }
}
