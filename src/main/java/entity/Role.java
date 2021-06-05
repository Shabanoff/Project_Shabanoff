package entity;

public class Role extends Designation {

    public Role() {
    }


    public Role(int id, String name) {
        super(id, name);
    }

    public enum RoleIdentifier {

        MANAGER_ROLE(1), USER_ROLE(2);

        private final int id;

        RoleIdentifier(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }

}
