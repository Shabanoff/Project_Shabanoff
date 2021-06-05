package controller.schedulers;

import entity.IncludedPackage;
import entity.Status;
import entity.User;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import service.IncludedPackageService;
import service.ServiceFactory;
import service.UserService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


public class DebitJob implements Job {
    UserService userService = ServiceFactory.getUserService();
    IncludedPackageService includedPackageService = ServiceFactory.getIncludedPackageService();

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        int currentDay = LocalDate.now().getDayOfMonth();
        BigDecimal cost;
        List<User> users =
                userService.findAllUser();

        for (User user : users) {
            if (user.isActive()) {
                for (IncludedPackage includedPackage : includedPackageService.findByUser(user.getId())) {
                    cost = includedPackage.getTariff().getCost();
                    if (user.getBalance().compareTo(cost) >= 0) {
                        if (includedPackage.getSubscriptionDate().getDayOfMonth() >= currentDay ||
                                includedPackage.getSubscriptionDate().getDayOfMonth() == currentDay) {
                            userService.decreaseUserBalanceScheduler(user, cost);
                        }
                    } else {
                        userService.updateUserStatus(user, Status.StatusIdentifier.BLOCKED_STATUS.getId());
                    }

                }
            }
        }
    }

}
