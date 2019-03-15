package springboot.masterdata.dict.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springboot.masterdata.dict.dao.TsDictDao;
import springboot.masterdata.dict.service.TsDictService;
import springboot.masterdata.dict.vo.DictVo;

import java.util.List;

@Service
public class TsDictServiceImpl implements TsDictService {

    @Autowired
    private TsDictDao dictTypeDao;

    @Override
    public List<DictVo> getDictList() {
        return dictTypeDao.getDictList();
    }

}
