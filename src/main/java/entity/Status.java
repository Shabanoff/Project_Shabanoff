package entity;

public class Status extends Designation {
    public Status() {
    }

    public Status(int id, String name) {
        super(id, name);
    }

    public enum StatusIdentifier {

        ACTIVE_STATUS(1), BLOCKED_STATUS(2);

        private final int id;

        StatusIdentifier(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }

}
