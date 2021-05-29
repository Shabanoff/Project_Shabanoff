package controller;

import controller.command.DefaultCommand;
import controller.command.HomeCommand;
import controller.command.ICommand;
import controller.command.authorization.GetLoginCommand;
import controller.command.authorization.LogoutCommand;
import controller.command.authorization.PostLoginCommand;
import controller.command.authorization.PostSignupCommand;
import controller.command.user.GetReplenishCommand;
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
        commands.put(buildKey(bundle.getString("signup.path"), "signup.post"),
                new PostSignupCommand());
        commands.put(buildKey(bundle.getString("logout.path"), "logout"),
                new LogoutCommand());
        commands.put(buildKey(bundle.getString("user.replenish"), "replenish"),
                new GetReplenishCommand());
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
