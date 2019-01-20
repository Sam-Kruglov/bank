package com.samkruglov.bank.service.impl;

import com.samkruglov.bank.dao.interfaces.AccountRepo;
import com.samkruglov.bank.dao.interfaces.CardRepo;
import com.samkruglov.bank.domain.Account;
import com.samkruglov.bank.domain.Card;
import com.samkruglov.bank.service.CardService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.Optional;

import static com.samkruglov.bank.dao.interfaces.common.ImmutableRepo.unwrapped;

@AllArgsConstructor
@Service
class CardServiceImpl implements CardService {

    private final AccountRepo accountRepo;
    private final CardRepo repo;

    @Override
    public Optional<Card> get(@NonNull Long id) {
        return repo.findById(id);
    }

    @Override
    @Transactional
    public Card create(
            @NonNull Long accountId,
            LocalDate expiryDate,
            LocalDate issueDate,
            String number) {

        //we could just check if account exists and insert the card without fetching the account with all the cards
        // but this way we can check for uniqueness by a business key listed in Card#equals and throw an exception

        Account account = unwrapped(accountRepo::findWithCardsById, accountId);
        Card card = new Card(expiryDate, issueDate, number);
        Assert.isTrue(account.addCard(card), "card already exists");
        accountRepo.save(account);
        //noinspection OptionalGetWithoutIsPresent -- we just created it
        return account.getReadOnlyCards().stream().filter(card::equals).findAny().get();
    }

    @Override
    @Transactional
    public boolean remove(Long id) {
        Account account = unwrapped(accountRepo::findWithCardsById, id);
        return account.removeCardById(id);
    }
}
