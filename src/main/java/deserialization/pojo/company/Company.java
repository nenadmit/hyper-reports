package deserialization.pojo.company;

        import deserialization.pojo.company.annotations.Column;
        import deserialization.pojo.company.annotations.Table;

        import javax.xml.bind.annotation.*;
        import java.util.List;


@XmlRootElement(name="company")
@Table(name="companies")
public class Company extends Base{


    @Column(name = "uuid")
    private long uuid;
    @Column(name ="name")
    private String name;
    @Column(name="address")
    private String address;
    private List<Store> stores;

    public Company() {
    }

    public Company(int id, long uuid, String name, String address) {
        this.uuid = uuid;
        this.name = name;
        this.address = address;
        setId(id);
    }



    public long getUuid() {
        return uuid;
    }

    @XmlAttribute
    public void setUuid(long uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }
    @XmlAttribute
    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }
    @XmlAttribute
    public void setAddress(String address) {
        this.address = address;
    }

    public List<Store> getStores() {
        return this.stores;
    }

    @XmlElementWrapper(name = "stores")
    @XmlElement(name="store")
    public void setStores(List<Store> storeList) {
        this.stores = storeList;
    }

    public enum Fields {

        TABLE("companies"),
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
