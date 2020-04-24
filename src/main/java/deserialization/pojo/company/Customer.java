package deserialization.pojo.company;

import deserialization.pojo.company.annotations.Column;
import deserialization.pojo.company.annotations.Table;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

@Table(name="customers")
public class Customer extends Base {

    @Column(name="uuid")
    private long uuid;
    @Column(name="name")
    private String name;
    @Column(name="address")
    private String address;
    private List<Invoice> invoices = new ArrayList<>();


    public Customer(int id,long uuid,String name, String address) {
        this.name = name;
        this.address = address;
        this.uuid = uuid;
        super.setId(id);
    }

    public Customer(){

    }

    public String getName() {
        return name;
    }

    @XmlElement(name="name")
    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    @XmlElement(name="address")
    public void setAddress(String address) {
        this.address = address;
    }

    public long getUuid() {
        return uuid;
    }

    @XmlElement(name="uuid")
    public void setUuid(long uuid) {
        this.uuid = uuid;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    public enum Fields {

        TABLE("customers"),
        ID("id"),
        UUID("uuid"),
        NAME("name"),
        ADDRESS("address");

        private final String value;

        Fields(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

}
