package the305labs.inventario.entity;

// import jakarta.persistence.Entity;
// import jakarta.persistence.Id;

public class MenuItem {
    private String label;
    private String path;

    public MenuItem(String label, String path) {
        this.label = label;
        this.path = path;
    }

    public String getLabel() {
        return label;
    }

    public String getPath() {
        return path;
    }
}