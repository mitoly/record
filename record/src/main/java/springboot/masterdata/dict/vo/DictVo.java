package springboot.masterdata.dict.vo;

import springboot.masterdata.dict.entity.TsDictEntry;
import springboot.masterdata.dict.entity.TsDictType;

import java.util.List;

public class DictVo extends TsDictType {

    List<TsDictEntry> dictEntryList;

    public List<TsDictEntry> getDictEntryList() {
        return dictEntryList;
    }

    public void setDictEntryList(List<TsDictEntry> dictEntryList) {
        this.dictEntryList = dictEntryList;
    }
}
