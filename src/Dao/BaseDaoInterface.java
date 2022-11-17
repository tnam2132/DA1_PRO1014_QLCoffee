package Dao;

import java.util.List;

public interface BaseDaoInterface<T> {

    public void insert(T entity);

    public void update(T entity);
    
    public void updateStatus(Object... args);
    
    public void delete(Object... args);

    public List<T> selectBySQL(String sql, Object... args);

    public List<T> getAll();

    public T selectById(int id);
    
    public T selectByMa(String ma);

    public List<T> selectByInfo(Object... args);

    public List<T> selectByStatus(int trangThai);

    public List<T> selectByKeyword(String keyword);
    
    public T selectNew();
}
