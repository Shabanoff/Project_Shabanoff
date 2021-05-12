package entity;

public class IncludedOptionToTariff {
    private long id;
    private long optionId;
    private long tariffId;

    public static class Builder {
        private final IncludedOptionToTariff includedOptionToTariff;

        public Builder() {
            includedOptionToTariff = new IncludedOptionToTariff();
        }

        public Builder addId(long id) {
            includedOptionToTariff.setId(id);
            return this;
        }
        public Builder addOptionId(long optionId) {
            includedOptionToTariff.setOptionId(optionId);
            return this;
        }
        public Builder addTariffId(long tariffId) {
            includedOptionToTariff.setTariffId(tariffId);
            return this;
        }
        public IncludedOptionToTariff build(){return includedOptionToTariff;}
    }
    public static Builder newBuilder() {
        return new IncludedOptionToTariff.Builder();
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOptionId() {
        return optionId;
    }

    public void setOptionId(long optionId) {
        this.optionId = optionId;
    }

    public long getTariffId() {
        return tariffId;
    }

    public void setTariffId(long tariffId) {
        this.tariffId = tariffId;
    }

    @Override
    public String toString() {
        return "IncludedPackage{" +
                "id=" + id +
                ", tariffId=" + tariffId+
                ", optionId=" + optionId +
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

        IncludedOptionToTariff includedOptionToTariff = (IncludedOptionToTariff) o;

        return this.id == includedOptionToTariff.id;
    }
}
