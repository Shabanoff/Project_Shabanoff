package entity;

import java.time.LocalDate;

public class IncludedPackage {
    private long packageNumber;
    private User packageHolder;
    private LocalDate subscriptionDate;
    private Service service;
    private Tariff tariff;

    public static class Builder {
        private final IncludedPackage includedPackage;

        public Builder() {
            includedPackage = new IncludedPackage();
        }

        public Builder addPackageNumber(long cardNumber) {
            includedPackage.setPackageNumber(cardNumber);
            return this;
        }

        public Builder addPackageHolder(User user) {
            includedPackage.setPackageHolder(user);
            return this;
        }

        public Builder addSubscriptionDate(LocalDate date) {
            includedPackage.setSubscriptionDate(date);
            return this;
        }

        public Builder addService(Service service) {
            includedPackage.setService(service);
            return this;
        }

        public Builder addTariff(Tariff tariff) {
            includedPackage.setTariff(tariff);
            return this;
        }

        public IncludedPackage build() {
            return includedPackage;
        }
    }
    public static Builder newBuilder() {
        return new Builder();
    }

    public long getPackageNumber() {
        return packageNumber;
    }

    public void setPackageNumber(long packageNumber) {
        this.packageNumber = packageNumber;
    }

    public User getPackageHolder() {
        return packageHolder;
    }

    public void setPackageHolder(User packageHolder) {
        this.packageHolder = packageHolder;
    }

    public LocalDate getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(LocalDate subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Tariff getTariff() {
        return tariff;
    }

    public void setTariff(Tariff tariff) {
        this.tariff = tariff;
    }

    @Override
    public String toString() {
        return "IncludedPackage{" +
                "packageNumber=" + packageNumber +
                ", packageHolder=" + packageHolder +
                ", subscriptionDate=" + subscriptionDate +
                ", service=" + service +
                ", service=" + service +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }

        IncludedPackage includedPackage = (IncludedPackage) o;

        return this.packageNumber == includedPackage.packageNumber;
    }


}
