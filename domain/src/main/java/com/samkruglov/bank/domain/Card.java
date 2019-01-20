package com.samkruglov.bank.domain;

import com.samkruglov.bank.domain.common.AuditableEntity;
import com.samkruglov.bank.domain.common.Identifiable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "cards")
@Immutable
public class Card extends AuditableEntity<Long> {

    @NonNull
    private LocalDate expiryDate;

    private LocalDate issueDate;

    private String number;

    @Setter(AccessLevel.PACKAGE)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Account account;

    public Card(
            @NonNull LocalDate expiryDate,
            LocalDate issueDate,
            String number) {

        this.expiryDate = expiryDate;
        this.issueDate = issueDate;
        this.number = number;
    }

    /**
     * @implNote can only be empty if {@link Account#removeCard} has been called
     */
    public Optional<Account> getAccount() {
        return Optional.ofNullable(account);
    }

    public Optional<LocalDate> getIssueDate() {
        return Optional.ofNullable(issueDate);
    }

    public Optional<String> getNumber() {
        return Optional.ofNullable(number);
    }

    @Override
    public String toString() {
        return "Card(super=" + super.toString()
                + ", expiryDate=" + expiryDate
                + ", issueDate=" + issueDate
                + ", number=" + number
                + ", accountId=" + getAccount().map(Identifiable::getId).orElse(null)
                + ')';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Card that = (Card) o;
        if (this.getId() != null && that.getId() != null) {
            return this.getId().equals(that.getId());
        }
        return Objects.equals(this.account, that.account) && Objects.equals(this.number, that.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(account, number);
    }
}
