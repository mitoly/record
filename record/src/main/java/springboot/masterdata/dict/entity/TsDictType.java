package springboot.masterdata.dict.entity;

import springboot.base.entity.VersionAbstractEntity;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "TS_DICT_TYPE")
public class TsDictType extends VersionAbstractEntity implements Serializable {

    private String typeCode;
    private String typeName;

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
