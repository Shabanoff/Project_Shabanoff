package entity;

import java.math.BigDecimal;
import java.util.List;

public class Tariff {
    private long id;
    private String tariffName;
    private BigDecimal cost;
    private Service service;
    private List<IncludedOption> includedOptions;

    public static Builder newBuilder() {
        return new Builder();
    }

    public List<IncludedOption> getIncludedOptions() {
        return includedOptions;
    }

    public void setIncludedOptions(List<IncludedOption> includedOptions) {
        this.includedOptions = includedOptions;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
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

    @Override
    public String toString() {
        return "Tariff{" +
                "TariffNumber=" + id +
                ", tariffName=" + tariffName +
                ", cost=" + cost +
                ", serviceId=" + service.getId() +
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

        Tariff tariff = (Tariff) o;

        return this.id == tariff.id;
    }

    public static class Builder {
        private final Tariff tariff;

        public Builder() {
            tariff = new Tariff();
        }

        public Builder addTariffId(long id) {
            tariff.setId(id);
            return this;
        }

        public Builder addTariffName(String tariffName) {
            tariff.setTariffName(tariffName);
            return this;
        }

        public Builder addIncludedOptions(List<IncludedOption> includedOptions) {
            tariff.setIncludedOptions(includedOptions);
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

        public Tariff build() {
            return tariff;
        }
    }
}
