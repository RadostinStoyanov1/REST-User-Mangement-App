package users.rest.model.dto;

public class BooleanResultDTO {
    private boolean data;

    public boolean getData() {
        return data;
    }

    public BooleanResultDTO setData(boolean data) {
        this.data = data;
        return this;
    }

    public BooleanResultDTO(Builder builder) {
        this.data = builder.data;
    }

    public static class Builder {
        private boolean data;

        public Builder data(boolean data) {
            this.data = data;
            return this;
        }

        public BooleanResultDTO build() {
            return new BooleanResultDTO(this);
        }
    }
}
