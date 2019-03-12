package cn.edu.llxy.dw.dao.base.impl;

import cn.edu.llxy.dw.dao.base.IBaseDao;
import org.apache.poi.ss.formula.functions.T;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;

import java.io.Serializable;
import java.util.List;

public class BaseDao implements IBaseDao {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Autowired
    public SessionFactory sessionfactory;


    /**
     * 按hql查询对象列表.
     * @param values 数量可变的参数,按顺序绑定.
     */
    @Override
    public <X> List<X> queryByHql(final String hql, final Object... values) {
        Session session = null;
        List<X> list = null;
        try {
            session = sessionfactory.openSession();
            list = createHqlQuery(session, hql, values).list();
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        return list;
    }

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
    @Override
    public <X> List<X> queryBySql(String sql, Object... values) {
        return null;
    }

    /**
     * createHqlQuery(final String hql, final Object... values)
     * 根据传入的hql语句和参数，查询所需的列表
     * @param hql
     * @param values
     * @return
     */
    private Query createHqlQuery(final Session session, final String hql, final Object... values) {
        Transaction transaction = null;
        Query query = null;
        try {
            transaction = session.beginTransaction();
            query = session.createQuery (hql);
            if(values != null) {
                for (int i = 0; i < values.length; i++) {
                    query.setParameter (i, values[i]);
                }
            }
            transaction.commit ();
        } catch (Exception e) {//如果出现异常，事物回滚
            if(transaction!=null){
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return query;
    }

    public Query createQuery(final String sql, final Object... values) {
        Session session = null;
        Transaction transaction = null;
        Query query = null;
        try {
            session = sessionfactory.openSession();//获取session
            Transaction transaction1 = session.beginTransaction();//开启事物
            query = session.createQuery(sql);
            if (values != null) {
                for (int i = 0; i < values.length; i++) {
                    query.setParameter(i, values[i]);
                }
            }
            transaction.commit();
        } catch (Exception e){
            if (transaction!=null){
                transaction.rollback();
            }
            e.printStackTrace();
        }finally {
            session.close();
        }
        return query;
    }
}
