package database.repositoryy.v1;
import database.repositoryy.Repository;
import database.specification.QueryInfo;
import database.specification.Specification;
import database.template.Template;
import deserialization.pojo.company.Company;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class CompanyRepository implements Repository<Company> {

    private Template<Company> template;

    public CompanyRepository(Template<Company> template){
        this.template = template;
    }

    @Override
    public Company create(Company company) {
        String sql = "insert into companies (uuid,name,address) values (?,?,?)";

        int generatedKey = template.singleStatementExecution(sql, getObjectArray(company));

        if(generatedKey != 0){
            company.setId(generatedKey);
        }
        return company;
    }

    @Override
    public List<Company> createAll(List<Company> list) {

        String sql = "insert into companies (uuid,name,address) values (?,?,?)";
        List<Object[]> paramList = new ArrayList<>();

        list.forEach(company -> paramList.add(getObjectArray(company)));

        List<Integer> generatedKeys = template.batchExecution(sql,paramList);

        int counter = 0;
        for(Company company:list){
            company.setId(generatedKeys.get(counter++));
        }

        return list;
    }

    @Override
    public Company update(Company company) {
        String sql = "update companies set uuid=?,name=?,address=? where id=?";

        template.singleStatementExecution(sql,new Object[]{
                company.getUuid(),
                company.getName(),
                company.getAddress(),
                company.getId()
        });
        return company;
    }

    @Override
    public void delete(Company company) {

        String sql = "delete from companies where id=?";

        template.singleStatementExecution(sql,new Object[]{company.getId()});

    }

    @Override
    public List<Company> query(Specification specification) {

        QueryInfo info = specification.toQueryInfo();

        return template.findAll(info.getSql(),info.getParams(),resultSet -> map(resultSet));

    }

    private Company map(ResultSet resultSet) throws SQLException {

            return new Company(
                    resultSet.getInt("id"),
                    resultSet.getLong("uuid"),
                    resultSet.getString("name"),
                    resultSet.getString("address")
            );
        }

        private Object[] getObjectArray(Company company){
            return new Object[]{
                    company.getUuid(),
                    company.getName(),
                    company.getAddress()
            };
    }
}
