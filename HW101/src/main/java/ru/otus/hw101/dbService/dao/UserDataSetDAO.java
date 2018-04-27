package ru.otus.hw101.dbService.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import ru.otus.hw101.base.dataSets.UserDataSet;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by rel on 25.04.2018.
 */
public class UserDataSetDAO {
    private Session session;

    public UserDataSetDAO(Session session) {
        this.session = session;
    }

    public void save(UserDataSet dataSet) {
        session.save(dataSet);
    }

    public UserDataSet read(long id) {
        return session.load(UserDataSet.class, id);
    }

    public UserDataSet readByName(String name) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<UserDataSet> criteria = builder.createQuery(UserDataSet.class);
        Root<UserDataSet> from = criteria.from(UserDataSet.class);
        criteria.where(builder.equal(from.get("name"), name));
        Query<UserDataSet> query = session.createQuery(criteria);
        return query.uniqueResult();
    }

    public List<UserDataSet> readAll() {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<UserDataSet> criteria = builder.createQuery(UserDataSet.class);
        criteria.from(UserDataSet.class);
        return session.createQuery(criteria).list();
    }

    public List<String> readAllNames() {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<String> criteria = builder.createQuery(String.class);
        Root<UserDataSet> from = criteria.from(UserDataSet.class);
        criteria.select(from.get("name"));
        return session.createQuery(criteria).list();
    }

    public String readNameById(int id) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<String> criteria = builder.createQuery(String.class);
        Root<UserDataSet> from = criteria.from(UserDataSet.class);
        criteria.select(from.get("name")); //select name from user where id = %%;
        criteria.where(builder.equal(from.get("id"), id));
        return session.createQuery(criteria).uniqueResult();
    }
}
