package entity;


import java.time.LocalDate;
import java.util.List;

public class IncludedPackage {
    private long id;
    private long userId;
    private LocalDate subscriptionDate;
    private Service service;
    private Tariff tariff;

    public IncludedPackage() {
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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public static class Builder {
        private final IncludedPackage includedPackage;

        public Builder() {
            includedPackage = new IncludedPackage();
        }

        public IncludedPackage.Builder addId(long id) {
            includedPackage.setId(id);
            return this;
        }
        public IncludedPackage.Builder addSubscriptionDate(LocalDate date) {
            includedPackage.setSubscriptionDate(date);
            return this;
        }
        public IncludedPackage.Builder addUserId(long userId) {
            includedPackage.setUserId(userId);
            return this;
        }


        public IncludedPackage.Builder addService(Service service) {
            includedPackage.setService(service);
            return this;
        }

        public IncludedPackage.Builder addTariff(Tariff tariff) {
            includedPackage.setTariff(tariff);
            return this;
        }


        public IncludedPackage build() {
            return includedPackage;
        }
    }
    public static Builder newBuilder() {
        return new IncludedPackage.Builder();
    }

    public long getId() {
        return id;
    }

    public void setId(long packageNumber) {
        this.id = packageNumber;
    }


    public LocalDate getSubscriptionDate() {
        return LocalDate.now();
    }

    public void setSubscriptionDate(LocalDate subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }



    @Override
    public String toString() {
        return "IncludedPackage{" +
                "id=" + id +
                ", subscriptionDate=" + subscriptionDate +
                ", service=" + service.toString() +
                ", tariff=" + tariff.toString() +
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

        return this.id == includedPackage.id;
    }


}
