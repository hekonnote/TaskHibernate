package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private SessionFactory sessionFactory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {
    }

    public void createUsersTable() {
        Transaction createTableT = null;
        try (Session session = sessionFactory.openSession()) {
            createTableT = session.beginTransaction();
            String createTableQuery = "CREATE TABLE IF NOT EXISTS user" +
                    "(id BIGINT NOT NULL AUTO_INCREMENT, " +
                    " name VARCHAR(50) NOT NULL, " +
                    " lastName VARCHAR (50) NOT NULL, " +
                    " age TINYINT NOT NULL, " +
                    " PRIMARY KEY (id))";
            session.createSQLQuery(createTableQuery).executeUpdate();
            createTableT.commit();
        } catch (Exception e) {
            if (createTableT != null) {
                createTableT.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction saveUserT = null;
        try (Session session = sessionFactory.openSession()) {
            saveUserT = session.beginTransaction();
            session.save(new User(name, lastName, age));
            saveUserT.commit();
            printUserSaved(name);
        } catch (Exception e) {
            if (saveUserT != null) {
                saveUserT.rollback();
            }
            e.printStackTrace();
        }
    }

    public void printUserSaved(String name) {
        System.out.println("User с именем – " + name + " добавлен в базу данных.");
    }

    @Override
    public void dropUsersTable() {
        Transaction dropT = null;
        try (Session session = sessionFactory.openSession()) {
            dropT = session.beginTransaction();
            String dropTableQuery = "DROP TABLE IF EXISTS user";
            session.createSQLQuery(dropTableQuery).executeUpdate();
            dropT.commit();
        } catch (Exception e) {
            if (dropT != null) {
                dropT.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction removeUserT = null;
        try (Session session = sessionFactory.openSession()) {
            removeUserT = session.beginTransaction();
            session.delete(session.get(User.class, id));
            removeUserT.commit();
        } catch (Exception e) {
            if (removeUserT != null) {
                removeUserT.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        Transaction getAllUsersT = null;
        List<User> usersList = null;
        try (Session session = sessionFactory.openSession()) {
            getAllUsersT = session.beginTransaction();
            usersList = session.createQuery("FROM User").list(); // работает
//            usersList = session.createCriteria(User.class).list(); // работает
            getAllUsersT.commit();
        } catch (Exception e) {
            if (getAllUsersT != null) {
                getAllUsersT.rollback();
            }
            e.printStackTrace();
        }
        return usersList;
    }

    @Override
    public void cleanUsersTable() {
        Transaction cleanUserT = null;
        try (Session session = sessionFactory.openSession()) {
            cleanUserT = session.beginTransaction();
//            session.createNativeQuery("TRUNCATE TABLE user").executeUpdate(); // работает
            session.createQuery("delete from User").executeUpdate(); // работает
            cleanUserT.commit();
        } catch (Exception e) {
            if (cleanUserT != null) {
                cleanUserT.rollback();
            }
            e.printStackTrace();
        }
    }
}


