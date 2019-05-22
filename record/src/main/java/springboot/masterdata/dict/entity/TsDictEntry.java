package springboot.masterdata.dict.entity;

import springboot.base.entity.VersionAbstractEntity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "TS_DICT_ENTRT")
public class TsDictEntry extends VersionAbstractEntity {

    private Integer typeId;
    private String entryCode;
    private String entryName;
    private String entryNameEn;
    /**排序顺位*/
    private Integer orderNo;

    @Column(name = "TYPE_ID")
    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    @Column(name = "ENTRY_CODE")
    public String getEntryCode() {
        return entryCode;
    }

    public void setEntryCode(String entryCode) {
        this.entryCode = entryCode;
    }

    @Column(name = "ENTRY_NAME")
    public String getEntryName() {
        return entryName;
    }

    public void setEntryName(String entryName) {
        this.entryName = entryName;
    }

    @Column(name = "ENTRY_NAME_EN")
    public String getEntryNameEn() {
        return entryNameEn;
    }

    public void setEntryNameEn(String entryNameEn) {
        this.entryNameEn = entryNameEn;
    }

    @Column(name = "ORDER_NO")
    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }
}
