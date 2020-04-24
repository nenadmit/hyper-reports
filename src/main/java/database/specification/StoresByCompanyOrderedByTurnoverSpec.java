package database.specification;

public class StoresByCompanyOrderedByTurnoverSpec implements Specification{

    private int companyId;
    private int month;
    private int year;

    public StoresByCompanyOrderedByTurnoverSpec(int companyId,int month,int year){
        this.companyId = companyId;
        this.month = month;
        this.year = year;
    }

    @Override
    public QueryInfo toQueryInfo() {


        String sql = "SELECT s.*," +
                "(SELECT SUM(i.total) FROM invoices i WHERE i.fk_invoice_store = s.id " +
                "AND YEAR(i.date_time) = ? AND MONTH(i.date_time) = ?)+ " +
                "(SELECT SUM(r.total) FROM receipts r WHERE r.fk_receipt_store = s.id " +
                "AND YEAR(r.date_time) = ? AND MONTH(r.date_time) = ?) " +
                " AS turnover " +
                "FROM stores s " +
                "JOIN companies c on c.id = s.fk_store_company " +
                "Where c.id = ? " +
                "GROUP BY s.id " +
                "ORDER BY turnover DESC";

        System.out.println(sql);

        return new QueryInfo(sql,new Object[]{companyId,month,year,month,year});
    }
}
