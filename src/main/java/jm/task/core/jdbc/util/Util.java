package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//  Hibernate вариант
public class Util {
    private static SessionFactory sessionFactory = createSessionFactory();

    private static SessionFactory createSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration()
                        .setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/task1_1_4")
                        .setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver")
                        .setProperty("hibernate.connection.username", "root")
                        .setProperty("hibernate.connection.password", "sososo")
                        .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
                        .addAnnotatedClass(User.class)
//                        .setProperty("hibernate.id.new_generator_mappings", "true")
//                        .setProperty("hibernate.hbm2ddl.auto", "update")
                        .setProperty("show_sql", "true");
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdownSession() {
        getSessionFactory().close();
    }
}

//public class Util {   //  JDBC вариант
//
//    private static final String URL = "jdbc:mysql://localhost:3306/task1_1_3";
//    private static final String USERNAME = "root";
//    private static final String PASSWORD = "sososo";
//
//    public static Connection getConnection() throws SQLException {
//        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
//    }
//}

/* Пример использования PreparedStatement:
public List<User> getUser(int userId) {
    try (Connection con = DriverManager.getConnection(myConnectionURL);
         PreparedStatement ps = createPreparedStatement(con, userId);
         ResultSet rs = ps.executeQuery()) {

         // process the resultset here, all resources will be cleaned up

    } catch (SQLException e) {
        e.printStackTrace();
    }
}

private PreparedStatement createPreparedStatement(Connection con, int userId) throws SQLException {
    String sql = "SELECT id, username FROM users WHERE id = ?";
    PreparedStatement ps = con.prepareStatement(sql);
    ps.setInt(1, userId);
    return ps;
}
 */


