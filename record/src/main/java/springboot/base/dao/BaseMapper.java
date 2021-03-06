package springboot.base.dao;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * ͨ通用mapper dao继承此类封装了基本的增删改查
 * @author mitol
 */
public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T> {

    /**
     *
     * @param t
     * @return
     * @throws RuntimeException
     */
    default int updateByPrimaryKeyWithVersion (T t) throws RuntimeException {
        int result = updateByPrimaryKey(t);
        if (0 == result) {
            throw new RuntimeException("更新失败，数据过期");
        }
        return result;
    }
}
