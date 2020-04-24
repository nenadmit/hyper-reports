package database.service.interfaces;

import deserialization.pojo.company.Card;

import java.util.Optional;

public interface CardService extends Service<Card> {

    Optional<Card> findByNumber(long number);

}
