package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {
        UserDaoHibernateImpl userService = new UserDaoHibernateImpl(); // Hibernate
        userService.createUsersTable();
        userService.saveUser("Pit1", "ddddd", (byte) 30);
        userService.saveUser("Pit2", "ccccc", (byte) 35);
        userService.saveUser("Pit3", "eeeee", (byte) 40);
        userService.saveUser("Pit4", "hhhhh", (byte) 45);
        userService.removeUserById(1L);
        userService.getAllUsers().forEach(System.out::println);
        userService.cleanUsersTable();
        userService.dropUsersTable();
        Util.shutdownSession();
    }
}

