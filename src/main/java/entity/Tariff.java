package entity;

import java.math.BigDecimal;

public class Tariff {
    private long tariffNumber;
    private String tariffName;
    private BigDecimal cost;
    private Service service;

    public static class Builder{
        private final Tariff tariff;

        public Builder(){tariff = new Tariff();}

        public Builder addTariffNumber(long id) {
            tariff.setTariffNumber(id);
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

        public Builder addService(Service service) {
            tariff.setService(service);
            return this;
        }

        public Tariff build(){
            return tariff;
        }
    }


    public long getTariffNumber() {
        return tariffNumber;
    }

    public void setTariffNumber(long tariffNumber) {
        this.tariffNumber = tariffNumber;
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

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    @Override
    public String toString() {
        return "Tariff{" +
                "TariffNumber=" + tariffNumber +
                ", tariffName=" + tariffName +
                ", cost=" + cost +
                ", service=" + getService() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || this.getClass() != o.getClass()) {return false;}

        Tariff tariff = (Tariff) o;

        return this.tariffNumber == tariff.tariffNumber;
    }
}
