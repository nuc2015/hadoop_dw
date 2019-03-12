package cn.edu.llxy.dw.dao.base;

import java.io.Serializable;
import java.util.List;

public interface IBaseDao {

    /**
     * 按hql查询对象列表.
     *
     * @param values 数量可变的参数,按顺序绑定.
     */
    public <X> List<X> queryByHql(String hql, Object... values);

    /**
     * 按sql查询对象实体集合(可变参数)，sql请用占位符，变量顺序绑定
     *
     * @param <X>
     *            泛型定义
     * @param sql
     *            SQL语句（普通SQL）
     * @param values
     *            不定量参数
     * @return 返回实体集合
     */
    public <X> List<X> queryBySql(final String sql, final Object... values);

}
