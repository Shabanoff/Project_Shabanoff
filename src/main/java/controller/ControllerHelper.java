package controller;

import controller.command.DefaultCommand;
import controller.command.HomeCommand;
import controller.command.ICommand;
import controller.command.authorization.*;
import controller.command.manager.*;
import controller.command.user.*;
import controller.util.constants.Views;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ControllerHelper {
    private final static String DELIMITER = ":";
    private final DefaultCommand DEFAULT_COMMAND = new DefaultCommand();
    private Map<String, ICommand> commands = new HashMap<>();
    private static final ResourceBundle bundle = ResourceBundle.
            getBundle(Views.PAGES_BUNDLE);

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
        commands.put(buildKey(bundle.getString("account.path"), "delete"),
                new PostAccountCommand());
        commands.put(buildKey(bundle.getString("replenish.path"), "replenish.post"),
                new PostReplenishCommand());
        commands.put(buildKey(bundle.getString("manager.users"), null),
                new GetManagerCommand());
        commands.put(buildKey(bundle.getString("createUser.path"), "post"),
                new PostCreateUserCommand());
        commands.put(buildKey(bundle.getString("manager.users"), "block"),
                new PostBlockCommand());
        commands.put(buildKey(bundle.getString("manager.users"), "unblock"),
                new PostUnblockCommand());

    }

    public ICommand getCommand(String path, String command) {
        return commands.getOrDefault(buildKey(path, command), DEFAULT_COMMAND);
    }

    private String buildKey(String path, String command) {
        return path + DELIMITER + command;
    }


    public static class Singleton {
        private final static ControllerHelper INSTANCE =
                new ControllerHelper();

        public static ControllerHelper getInstance() {
            return INSTANCE;
        }
    }


}
