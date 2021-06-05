package entity;

public class IncludedOption {
    private long id;
    private String definition;

    public static Builder newBuilder() {
        return new Builder();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
                "includedServiceNumber=" + id +
                ", definition=" + definition +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IncludedOption includedOption = (IncludedOption) o;

        return id == includedOption.id;
    }

    public static class Builder {
        private final IncludedOption includedOption;

        public Builder() {
            includedOption = new IncludedOption();
        }

        public Builder addId(long id) {
            includedOption.setId(id);
            return this;
        }

        public Builder addDefinition(String definition) {
            includedOption.setDefinition(definition);
            return this;
        }

        public IncludedOption build() {
            return includedOption;
        }
    }
}
