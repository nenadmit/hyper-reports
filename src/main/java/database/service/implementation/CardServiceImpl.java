package database.service.implementation;

import database.repositoryy.Repository;
import database.service.interfaces.CardService;
import database.specification.FindBySingleFieldSpec;
import deserialization.pojo.company.Card;
import deserialization.pojo.company.Store;

import java.util.List;
import java.util.Optional;

public class CardServiceImpl extends GenericService<Card> implements CardService {


    private Repository<Card> repository;

    public CardServiceImpl(Repository<Card> repository) {
        super(repository, Card.Fields.TABLE.getValue());
        this.repository = repository;
    }

    @Override
    public Card save(Card card){

        Optional<Card> optional = findByNumber(card.getNumber());

        if(optional.isPresent()){
            card.setId(optional.get().getId());
            return card;
        }

        return super.save(card);

    }

    @Override
    public List<Card> createAll(List<Card> list) {

        for (Card card : list) {
            Optional<Card> optional = findByNumber(card.getNumber());

            if (optional.isPresent()) {
                card.setId(optional.get().getId());
            } else {
                card = save(card);
            }
        }
        return list;
    }

    @Override
    public Optional<Card> findByNumber(long number) {

        List<Card> list = repository.query(new FindBySingleFieldSpec<>(number,
                Card.Fields.TABLE.getValue(),
                Card.Fields.NUMBER.getValue()));

        return list.isEmpty()? Optional.empty():Optional.of(list.get(0));
    }
}
