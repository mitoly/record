package springboot.masterdata.dict.service;

import springboot.masterdata.dict.vo.DictVo;

import java.util.List;

public interface TsDictService {
    List<DictVo> getDictList();
}
