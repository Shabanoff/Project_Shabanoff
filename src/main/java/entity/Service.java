package entity;

import java.util.List;

public class Service {
    private long id;
    private String serviceName;
    private List<Tariff> tariffs;

    public static Builder newBuilder() {
        return new Builder();
    }

    public List<Tariff> getTariffs() {
        return tariffs;
    }

    public void setTariffs(List<Tariff> tariffs) {
        this.tariffs = tariffs;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    public String toString() {
        return "Service{" +
                "serviceNumber=" + id +
                ", serviceName=" + serviceName +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Service service = (Service) o;

        return id == service.id;
    }

    public static class Builder {
        private final Service service;

        public Builder() {
            service = new Service();
        }

        public Builder addId(long id) {
            service.setId(id);
            return this;
        }

        public Builder addServiceName(String serviceName) {
            service.setServiceName(serviceName);
            return this;
        }

        public Builder addTariffs(List<Tariff> tariffs) {
            service.setTariffs(tariffs);
            return this;
        }

        public Service build() {
            return service;
        }
    }
}
