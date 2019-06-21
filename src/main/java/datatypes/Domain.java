package datatypes;

import anotations.Column;

public class Domain<ID> {
    @Column("id")
    protected ID id;

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }
}
