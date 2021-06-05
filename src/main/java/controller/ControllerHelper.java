package controller;

import controller.command.DefaultCommand;
import controller.command.HomeCommand;
import controller.command.ICommand;
import controller.command.authorization.GetLoginCommand;
import controller.command.authorization.LogoutCommand;
import controller.command.authorization.PostLoginCommand;
import controller.command.manager.*;
import controller.command.user.*;
import controller.util.constants.Views;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ControllerHelper {
    private final static String DELIMITER = ":";
    private static final ResourceBundle bundle = ResourceBundle.
            getBundle(Views.PAGES_BUNDLE);
    private final DefaultCommand DEFAULT_COMMAND = new DefaultCommand();
    private final Map<String, ICommand> commands = new HashMap<>();

    private ControllerHelper() {
        init();
    }

    private void init() {
        commands.put(buildKey(bundle.getString("home.path"), null),
                new HomeCommand());
        commands.put(buildKey(bundle.getString("home.path"), "home"),
                new HomeCommand());
        commands.put(buildKey(bundle.getString("login.path"), null),
                new GetLoginCommand());
        commands.put(buildKey(bundle.getString("login.path"), "login.post"),
                new PostLoginCommand());
        commands.put(buildKey(bundle.getString("logout.path"), "logout"),
                new LogoutCommand());
        commands.put(buildKey(bundle.getString("replenish.path"), null),
                new GetReplenishCommand());
        commands.put(buildKey(bundle.getString("service.path"), null),
                new GetServiceCommand());
        commands.put(buildKey(bundle.getString("account.path"), null),
                new GetAccountCommand());
        commands.put(buildKey(bundle.getString("service.path"), "plug"),
                new PostServiceCommand());
        commands.put(buildKey(bundle.getString("create.service.path"), "post"),
                new PostCreateServiceCommand());
        commands.put(buildKey(bundle.getString("create.service.path"), null),
                new GetCreateServiceCommand());
        commands.put(buildKey(bundle.getString("account.path"), "delete"),
                new PostAccountCommand());
        commands.put(buildKey(bundle.getString("replenish.path"), "replenish.post"),
                new PostReplenishCommand());
        commands.put(buildKey(bundle.getString("manager.users"), null),
                new GetManagerCommand());
        commands.put(buildKey(bundle.getString("createUser.path"), "post"),
                new PostCreateUserCommand());
        commands.put(buildKey(bundle.getString("createUser.path"), null),
                new GetCreateUserCommand());
        commands.put(buildKey(bundle.getString("manager.users"), "block"),
                new PostBlockCommand());
        commands.put(buildKey(bundle.getString("manager.users"), "unblock"),
                new PostUnblockCommand());
        commands.put(buildKey(bundle.getString("manager.includedOption.path"), null),
                new GetIncludedOptionCommand());
        commands.put(buildKey(bundle.getString("manager.includedOption.path"), "post"),
                new PostDeleteOption());
        commands.put(buildKey(bundle.getString("manager.createOption.path"), "post"),
                new PostCreateOptionCommand());
        commands.put(buildKey(bundle.getString("manager.createOption.path"), null),
                new GetCreateOptionCommand());
        commands.put(buildKey(bundle.getString("manager.create.tariff"), null),
                new GetCreateTariffCommand());
        commands.put(buildKey(bundle.getString("manager.create.tariff"), "post"),
                new PostCreateTariffCommand());
        commands.put(buildKey(bundle.getString("service.path"), "delete.tariff"),
                new PostDeleteTariffCommand());
        commands.put(buildKey(bundle.getString("service.path"), "delete.service"),
                new PostDeleteServiceCommand());
        commands.put(buildKey(bundle.getString("service.path"), "change.cost"),
                new PostChangeTariffCostCommand());
        commands.put(buildKey(bundle.getString("users.path"), null),
                new GetUsersCommand());
        commands.put(buildKey(bundle.getString("service.path"), "asc"),
                new PostAscTariffCommand());
        commands.put(buildKey(bundle.getString("service.path"), "desc"),
                new PostDescCostTariffCommand());
        commands.put(buildKey(bundle.getString("service.path"), "print"),
                new PostServicePdfCommand());


    }

    public ICommand getCommand(String path, String command) {
        return commands.getOrDefault(buildKey(path, command), DEFAULT_COMMAND);
    }

    private String buildKey(String path, String command) {
        return path + DELIMITER + command;
    }


    public static class Singleton {
        private static final ControllerHelper INSTANCE =
                new ControllerHelper();

        public static ControllerHelper getInstance() {
            return INSTANCE;
        }
    }


}
