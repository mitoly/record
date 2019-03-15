package springboot.masterdata.dict.entity;

import springboot.base.entity.OptAbstractEntity;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "TS_DICT_TYPE")
public class TsDictType extends OptAbstractEntity implements Serializable {

    private Integer id;
    private String typeCode;
    private String typeName;

    @Column(name = "ID")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "TYPE_CODE")
    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    @Column(name = "TYPE_NAME")
    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
