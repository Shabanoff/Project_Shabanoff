package entity;


import java.time.LocalDate;
import java.util.List;

public class IncludedPackage {
    private long id;
    private List<Long> userId;
    private LocalDate subscriptionDate;
    private long serviceId;
    private long tariffId;

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


        public IncludedPackage.Builder addServiceId(long service) {
            includedPackage.setServiceId(service);
            return this;
        }

        public IncludedPackage.Builder addTariffId(long tariff) {
            includedPackage.setTariffId(tariff);
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

    public List<Long> getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId.add(userId);
    }

    public LocalDate getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(LocalDate subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }

    public long getServiceId() {
        return serviceId;
    }

    public void setServiceId(long serviceId) {
        this.serviceId=serviceId;
    }

    public long getTariffId() {
        return tariffId;
    }

    public void setTariffId(long tariffId) {
        this.tariffId=tariffId;
    }

    @Override
    public String toString() {
        return "IncludedPackage{" +
                "id=" + id +
                ", userId=" + userId.toString() +
                ", subscriptionDate=" + subscriptionDate +
                ", service=" + serviceId +
                ", tariff=" + tariffId +
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
