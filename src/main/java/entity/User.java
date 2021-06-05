package entity;

import java.math.BigDecimal;
import java.util.List;

public class User {
    private final static BigDecimal DEFAULT_BALANCE = BigDecimal.ZERO;

    private final static String DEFAULT_STATUS = "ACTIVE";
    private final static int DEFAULT_STATUS_ID = Status.StatusIdentifier.
            ACTIVE_STATUS.getId();

    private final static String DEFAULT_ROLE_NAME = "USER";
    private final static int DEFAULT_ROLE_ID = Role.RoleIdentifier.
            USER_ROLE.getId();

    private long id;
    private String login;
    private String password;
    private BigDecimal balance;
    private List<IncludedPackage> includedPackages;
    private Status status;
    private Role role;

    public static Builder newBuilder() {
        return new Builder();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public List<IncludedPackage> getIncludedPackages() {
        return includedPackages;
    }

    public void setIncludedPackages(List<IncludedPackage> includedPackages) {
        this.includedPackages = includedPackages;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isManager() {
        return role.getId() == Role.RoleIdentifier.MANAGER_ROLE.getId();
    }

    public boolean isUser() {
        return role.getId() == Role.RoleIdentifier.USER_ROLE.getId();
    }

    public void setDefaultRole() {
        this.role = new Role(DEFAULT_ROLE_ID, DEFAULT_ROLE_NAME);
    }

    public boolean isActive() {
        return status.getId() == Status.StatusIdentifier.ACTIVE_STATUS.getId();
    }

    public boolean isBlocked() {
        return status.getId() == Status.StatusIdentifier.BLOCKED_STATUS.getId();
    }

    public void setDefaultStatus() {
        this.status = new Status(DEFAULT_STATUS_ID, DEFAULT_STATUS);
    }

    public void setDefaultBalance() {
        this.balance = DEFAULT_BALANCE;
    }

    public BigDecimal getDefaultBalance() {
        return DEFAULT_BALANCE;
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", password=" + password +
                ", balance='" + balance + '\'' +
                ", includedPackages='" + includedPackages + '\'' +
                ", status=" + status +
                ", role=" + role +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return (login.equals(user.login));
    }

    public static class Builder {
        private final User user;

        public Builder() {
            user = new User();
        }

        public Builder addId(long id) {
            user.setId(id);
            return this;
        }

        public Builder addLogin(String login) {
            user.setLogin(login);
            return this;
        }

        public Builder addPassword(String password) {
            user.setPassword(password);
            return this;
        }

        public Builder addStatus(Status status) {
            user.setStatus(status);
            return this;
        }

        public Builder addBalance(BigDecimal balance) {
            user.setBalance(balance);
            return this;
        }

        public Builder addDefaultBalance() {
            user.setBalance(DEFAULT_BALANCE);
            return this;
        }

        public Builder addRole(Role role) {
            user.setRole(role);
            return this;
        }

        public Builder addDefaultRole() {
            user.setDefaultRole();
            return this;
        }

        public Builder addDefaultStatus() {
            user.setDefaultStatus();
            return this;
        }

        public Builder addIncludedPackage(List<IncludedPackage> includedPackages) {
            user.setIncludedPackages(includedPackages);
            return this;
        }

        public User build() {
            return user;
        }
    }


}
