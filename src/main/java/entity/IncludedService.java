package entity;

import java.math.BigDecimal;

public class IncludedService {
    private long includedServiceNumber;
    private String definition;

    public static class Builder{
        private final IncludedService includedService;

        public Builder() {
            includedService = new IncludedService();
        }

        public Builder addId(long id) {
            includedService.setIncludedServiceNumber(id);
            return this;
        }

        public Builder addDefinition(String definition) {
            includedService.setDefinition(definition);
            return this;
        }

        public IncludedService build() {
            return includedService;
        }
    }
    public static Builder newBuilder() {
        return new Builder();
    }

    public long getIncludedServiceNumber() {
        return includedServiceNumber;
    }

    public void setIncludedServiceNumber(long includedServiceNumber) {
        this.includedServiceNumber = includedServiceNumber;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    @Override
    public String toString() {
        return "IncludedService{" +
                "includedServiceNumber=" + includedServiceNumber +
                ", definition=" + definition +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IncludedService includedService = (IncludedService) o;

        return includedServiceNumber == includedService.includedServiceNumber;
    }
}
