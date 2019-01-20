package com.samkruglov.bank.domain;

import com.samkruglov.bank.domain.common.AuditableEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor
@Getter
@ToString(callSuper = true)
@Entity
@Table(name = "users")
public class User extends AuditableEntity<Long> {

    @Setter
    @NonNull
    private String firstName;

    @Setter
    @NonNull
    private String lastName;

    @ToString.Exclude
    @Getter(AccessLevel.NONE)
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private Set<Account> accounts = new HashSet<>();

    /**
     * @implNote does not issue an {@code INSERT} to the database, does not call {@link Account#setUser}
     */
    void addAccount(Account account) {
        accounts.add(account);
    }

    public boolean removeAccount(Account account) {
        account.setUser(null);
        return accounts.remove(account);
    }

    public Set<Account> getReadOnlyAccounts() {
        return Collections.unmodifiableSet(accounts);
    }
}
