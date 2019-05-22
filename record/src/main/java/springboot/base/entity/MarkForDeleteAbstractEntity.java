package springboot.base.entity;

import javax.persistence.Column;

public abstract class MarkForDeleteAbstractEntity {

    private String markForDelete;

    @Column(name = "MARK_FOR_DELETE")
    public String getMarkForDelete() {
        return markForDelete;
    }
    public void setMarkForDelete(String markForDelete) {
        this.markForDelete = markForDelete;
    }

}
