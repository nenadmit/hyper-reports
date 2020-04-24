package deserialization.pojo.company;

import deserialization.pojo.company.annotations.Column;
import deserialization.pojo.company.annotations.Table;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@Table(name="cards")
public class Card extends Base{

    @Column(name="type")
    private String cardType;
    @Column(name="number")
    private long number;
    @Column(name="contactless")
    private Boolean contactless;
    private List<Invoice> invoices;
    private List<Receipt> receipts;

    public Card(long number,String cardType,  boolean contactless) {
        this.cardType = cardType;
        this.number = number;
        this.contactless = contactless;
    }
    public Card(int id, long number,String cardType,  boolean contactless) {
        this.cardType = cardType;
        this.number = number;
        this.contactless = contactless;
        super.setId(id);
    }

    public Card() {
    }

    public String getCardType() {
        return cardType;
    }

    @XmlElement(name="cardtype")
    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public long getNumber() {
        return number;
    }

    @XmlElement(name="number")
    public void setNumber(long number) {
        this.number = number;
    }

    public boolean isContactless() {
        return contactless;
    }

    @XmlElement(name="contactless")
    public void setContactless(boolean contactless) {
        this.contactless = contactless;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    public List<Receipt> getReceipts() {
        return receipts;
    }

    public void setReceipts(List<Receipt> receipts) {
        this.receipts = receipts;
    }

    public enum Fields {

        TABLE("cards"),
        NUMBER("number"),
        TYPE("type"),
        CONTACTLESS("contactless");

        private final String value;

        Fields(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }


}
