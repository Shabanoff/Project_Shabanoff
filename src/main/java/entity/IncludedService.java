package entity;

public class IncludedService {
    private long includedServiceNumber;
    private String definition;

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
