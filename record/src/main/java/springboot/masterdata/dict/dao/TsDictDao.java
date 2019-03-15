package springboot.masterdata.dict.dao;

import org.apache.ibatis.annotations.*;
import springboot.masterdata.dict.entity.TsDictEntry;
import springboot.masterdata.dict.vo.DictVo;

import java.util.List;

@Mapper
public interface TsDictDao {

    @Select("SELECT t.* FROM ts_dict_type t")
    @Results({
        @Result(column = "TYPE_CODE", property = "typeCode"),
        @Result(column = "TYPE_CODE", property = "dictEntryList",
                many = @Many(select = "springboot.masterdata.dict.dao.TsDictDao.getDictEntryByDictType"))
    })
    List<DictVo> getDictList();

    @Select("SELECT e.* " +
            "FROM " +
            "   ts_dict_type t LEFT JOIN ts_dict_entry e " +
            "ON " +
            "   t.ID = e.TYPE_ID AND e.MARK_FOR_DELETE = '0' " +
            "WHERE " +
            "   t.TYPE_CODE = #{typeCode}")
    List<TsDictEntry> getDictEntryByDictType(String typeCode);

}
