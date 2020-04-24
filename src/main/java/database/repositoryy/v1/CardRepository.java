package database.repositoryy.v1;

import database.repositoryy.Repository;
import database.specification.QueryInfo;
import database.specification.Specification;
import database.template.Template;
import deserialization.pojo.company.Card;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class CardRepository implements Repository<Card> {

    private Template<Card> template;

    public CardRepository(Template<Card> template) {
        this.template = template;
    }

    @Override
    public Card create(Card card) {
        String sql = "insert into cards (number,type,contactless) values (?,?,?)";

        int generatedKey = template.singleStatementExecution(sql, getObjectArray(card));

        if (generatedKey != 0) {
            card.setId(generatedKey);
        }
        return card;
    }

    @Override
    public List<Card> createAll(List<Card> list) {

        String sql = "insert into cards (number,type,contactless) values (?,?,?)";
        List<Object[]> paramList = new ArrayList<>();

        list.forEach(card -> paramList.add(getObjectArray(card)));

        List<Integer> generatedKeys = template.batchExecution(sql,paramList);

        int counter = 0;
        for(Card card :list){
            card.setId(generatedKeys.get(counter++));
        }

        return list;
    }

    @Override
    public Card update(Card card) {
        String sql = "update cards set number=?,type=?,contactless=? where id=?";

        template.singleStatementExecution(sql, new Object[]{
                card.getNumber(),
                card.getCardType(),
                card.isContactless()
        });
        return card;
    }

    @Override
    public void delete(Card card) {

        String sql = "delete from cards where id=?";

        template.singleStatementExecution(sql, new Object[]{card.getId()});

    }

    @Override
    public List<Card> query(Specification specification) {

        QueryInfo info = specification.toQueryInfo();

        return template.findAll(info.getSql(), info.getParams(), resultSet -> map(resultSet));

    }


    private Card map(ResultSet resultSet) throws SQLException {

        return new Card(
                resultSet.getInt("id"),
                resultSet.getLong("number"),
                resultSet.getString("type"),
                resultSet.getBoolean("contactless")
        );
    }

    private Object[] getObjectArray(Card card){
        return new Object[]{
                card.getNumber(),
                card.getCardType(),
                card.isContactless()
        };
    }


}
