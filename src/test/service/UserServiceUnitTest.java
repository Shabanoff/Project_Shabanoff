package service;

import dao.abstraction.UserDao;
import dao.factory.DaoFactory;
import dao.factory.MySqlDaoFactory;
import dao.factory.connection.DaoConnection;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import service.UserService;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@PrepareForTest(MySqlDaoFactory.class)
public class UserServiceUnitTest {

    MySqlDaoFactory daoFactory= Mockito.mock(MySqlDaoFactory.class);

    UserDao userDao = Mockito.mock(UserDao.class);;

    UserService userService = UserService.getInstance();

    @Test
    void test() {

        PowerMockito.mockStatic(MySqlDaoFactory.class);
        BDDMockito.given(MySqlDaoFactory.getInstance()).willReturn(daoFactory);
        when(daoFactory.getUserDao(any(DaoConnection.class))).thenReturn(userDao);
        when(userDao.findAll()).thenReturn(new ArrayList<>());

        userService.findAllUser();

        verify(userDao).findAll();

    }
}
