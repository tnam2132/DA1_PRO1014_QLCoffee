package Dao;

import Helper.JdbcHelper;
import java.sql.*;
import java.util.*;

public abstract class BaseDao<T> implements BaseDaoInterface<T> {

    public abstract String getQuery(String action);

    public abstract Object[] getParams(String action, T obj);

    public abstract T createEntity(final ResultSet rs);

    @Override
    public void insert(T entity) {
        try {
            JdbcHelper.update(this.getQuery("INSERT"), this.getParams("INSERT", entity));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(T entity) {
        try {
            JdbcHelper.update(this.getQuery("UPDATE"), this.getParams("UPDATE", entity));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

     @Override
    public void updateStatus(Object... args) {
        try {
            JdbcHelper.update(this.getQuery("UPDATE_STATUS"), args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public void delete(Object... args) {
        try {
            JdbcHelper.update(this.getQuery("DELETE"), args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<T> selectBySQL(String sql, Object... args) {
        List<T> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                T entity = createEntity(rs);
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<T> getAll() {
        return this.selectBySQL(this.getQuery("SELECT_ALL"));
    }

    @Override
    public T selectById(int id) {
        List<T> list = this.selectBySQL(this.getQuery("SELECT_BY_ID"), id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
    
    @Override
    public T selectByMa(String ma) {
        List<T> list = this.selectBySQL(this.getQuery("SELECT_BY_MA"), ma);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<T> selectByInfo(Object... args) {
        return this.selectBySQL(this.getQuery("SELECT_BY_INFO"), args);
    }

    @Override
    public List<T> selectByStatus(int trangThai) {
        return this.selectBySQL(this.getQuery("SELECT_BY_STATUS"), trangThai);
    }

    @Override
    public List<T> selectByKeyword(String keyword) {
        return this.selectBySQL(this.getQuery("SELECT_BY_KEYWORD"), "%" + keyword + "%");
    }

    public T selectNew() {
        List<T> list = this.selectBySQL(this.getQuery("SELECT_NEW"));
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
}
