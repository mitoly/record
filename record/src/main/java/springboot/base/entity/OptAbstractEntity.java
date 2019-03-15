package springboot.base.entity;

import javax.persistence.Column;

public abstract class OptAbstractEntity {

    private String markForDelete;
    private Integer optCounter;

    @Column(name = "MARK_FOR_DELETE")
    public String getMarkForDelete() {
        return markForDelete;
    }
    public void setMarkForDelete(String markForDelete) {
        this.markForDelete = markForDelete;
    }

    @Column(name = "OPT_COUNTER")
    public Integer getOptCounter() {
        return optCounter;
    }
    public void setOptCounter(Integer optCounter) {
        this.optCounter = optCounter;
    }
}
