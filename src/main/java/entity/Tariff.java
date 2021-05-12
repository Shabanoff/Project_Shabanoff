package entity;

import java.math.BigDecimal;

public class Tariff {
    private long id;
    private String tariffName;
    private BigDecimal cost;
    private long serviceId;

    public static class Builder{
        private final Tariff tariff;

        public Builder(){tariff = new Tariff();}

        public Builder addTariffNumber(long id) {
            tariff.setId(id);
            return this;
        }

        public Builder addTariffName(String tariffName) {
            tariff.setTariffName(tariffName);
            return this;
        }

        public Builder addCost(BigDecimal cost) {
            tariff.setCost(cost);
            return this;
        }

        public Builder addServiceId(Long serviceId) {
            tariff.setServiceId(serviceId);
            return this;
        }

        public Tariff build(){
            return tariff;
        }
    }
    public static Builder newBuilder() {
        return new Builder();
    }


    public long getId() {
        return id;
    }

    public void setId(long tariffNumber) {
        this.id = tariffNumber;
    }

    public String getTariffName() {
        return tariffName;
    }

    public void setTariffName(String tariffName) {
        this.tariffName = tariffName;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    @Override
    public String toString() {
        return "Tariff{" +
                "TariffNumber=" + id +
                ", tariffName=" + tariffName +
                ", cost=" + cost +
                ", serviceId=" + serviceId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || this.getClass() != o.getClass()) {return false;}

        Tariff tariff = (Tariff) o;

        return this.id == tariff.id;
    }
}
