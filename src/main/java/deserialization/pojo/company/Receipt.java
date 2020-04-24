package deserialization.pojo.company;

import deserialization.pojo.company.annotations.Column;
import deserialization.pojo.company.annotations.Table;

import javax.xml.bind.annotation.XmlElement;
import java.util.Date;

@Table(name="receipts")
public class Receipt extends Base {


    @Column(name="total")
    private double total;
    @Column(name="date_time")
    private Date datetime;
    @Column(name="payment_type")
    private String paymentType;
    @Column(name="fk_receipt_store")
    private Integer storeId;
    @Column(name="fk_receipt_card")
    private Integer cardId;
    private Card card;
    private Store store;

    public Receipt(int id,double total, Date datetime, String paymentType) {
        super.setId(id);
        this.total = total;
        this.datetime = datetime;
        this.paymentType = paymentType;
    }


    public Receipt() {
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

    @XmlElement(name="datetime")
    public void setDatetime(Date datetime) {

        this.datetime = datetime;
    }

    public String getPaymentType() {
        return paymentType;
    }

    @XmlElement(name="payment")
    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public Card getCard(){
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

    public enum Fields {

        TABLE("receipts"),
        ID("id"),
        TOTAL("total"),
        DATETIME("date_time"),
        PAYMENT_TYPE("payment_type"),
        FK_RECEIPT_STORE("fk_receipt_store"),
        FK_RECEIPT_CARD("fk_receipt_card");

        private final String value;

        Fields(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }
}
