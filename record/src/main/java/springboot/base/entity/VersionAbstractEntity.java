package springboot.base.entity;

import tk.mybatis.mapper.annotation.Version;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public abstract class VersionAbstractEntity extends MarkForDeleteAbstractEntity{

    @Id
//    @KeySql(useGeneratedKeys = true)  // tk.mybatis 用于替代 @GeneratedValue的注解 效果等同于 @GeneratedValue(generator = "JDBC")
    @GeneratedValue(generator = "JDBC")
    private Integer id;
    @Version
    private Integer optCounter;

    @Column(name = "ID")
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "OPT_COUNTER")
    public Integer getOptCounter() {
        return optCounter;
    }
    public void setOptCounter(Integer optCounter) {
        this.optCounter = optCounter;
    }

}
