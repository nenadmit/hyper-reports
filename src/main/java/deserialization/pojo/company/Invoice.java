package deserialization.pojo.company;

import deserialization.pojo.company.annotations.Column;
import deserialization.pojo.company.annotations.Table;

import javax.xml.bind.annotation.XmlElement;
import java.util.Date;

@Table(name="invoices")
public class Invoice extends Base{

    @Column(name="total")
    private double total;
    @Column(name="date_time")
    private Date datetime;
    @Column(name="payment_type")
    private String paymentType;
    @Column(name="fk_invoice_store")
    private Integer storeId;
    @Column(name="fk_invoice_card")
    private Integer cardId;
    @Column(name="fk_invoice_customer")
    private Integer customerId;
    private Card card;
    private Store store;
    private Customer customer;;

    public Invoice(int id, double total, Date datetime, String paymentType) {
        super.setId(id);
        this.total = total;
        this.datetime = datetime;
        this.paymentType = paymentType;
    }

    public Invoice() {
    }


    public double getTotal() {
        return total;
    }

    @XmlElement(name="total")
    public void setTotal(double total) {
        this.total = total;
    }

    public Date getDatetime() {
        return datetime;
    }

    public String getPaymentType() {
        return paymentType;
    }

    @XmlElement(name="payment")
    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    @XmlElement(name="datetime")
    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public Customer getCustomer() {
        return customer;
    }

    @XmlElement(name="customer")
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Card getCard() {
        return card;
    }

    @XmlElement(name="carddetails")
    public void setCard(Card card) {
        this.card = card;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public enum Fields {

        TABLE("invoices"),
        ID("id"),
        TOTAL("total"),
        DATETIME("date_time"),
        PAYMENT_TYPE("payment_type"),
        FK_INVOICE_STORE("fk_invoice_store"),
        FK_INVOICE_CARD("fk_invoice_card"),
        FK_INVOICE_CUSTOMER("fk_invoice_customer");

        private final String value;

        Fields(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

}
