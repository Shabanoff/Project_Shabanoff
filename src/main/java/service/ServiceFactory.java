package service;

/**
 * Intermediate layer between command layer and dao layer.
 * Implements operations of finding, creating, deleting entities.
 * Uses dao layer.
 *
 * @author Shabanoff
 */
public class ServiceFactory {
    private static volatile ServiceFactory instance;

    private ServiceFactory() {
    }

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

    public static TariffService getTariffService() {
        return TariffService.getInstance();
    }

    public static ServiceForService getServiceService() {
        return ServiceForService.getInstance();
    }

    public static IncludedOptionService getIncludedOptionService() {
        return IncludedOptionService.getInstance();
    }

    public static IncludedPackageService getIncludedPackageService() {
        return IncludedPackageService.getInstance();
    }

    public static IncludedOptionToTariffService getIncludedOptionToTariffService() {
        return IncludedOptionToTariffService.getInstance();
    }


}

