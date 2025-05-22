package the305labs.inventario.entity;

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