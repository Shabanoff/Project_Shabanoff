package service;
/**
 * Intermediate layer between command layer and dao layer.
 * Implements operations of finding, creating, deleting entities.
 * Account dao layer.
 *
 * @author Shabanoff
 */
public class ServiceFactory {
    private static volatile ServiceFactory instance;

    private ServiceFactory() {}

    public static ServiceFactory getInstance() {
        ServiceFactory localInstance = instance;
        if (localInstance == null) {
            synchronized (ServiceFactory.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ServiceFactory();
                }
            }
        }
        return localInstance;
    }





    public static UserService getUserService() {
        return UserService.getInstance();
    }




}

