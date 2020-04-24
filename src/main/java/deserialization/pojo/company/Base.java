package deserialization.pojo.company;

import java.util.Objects;

public class Base {

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Base base = (Base) o;
        return id == base.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
