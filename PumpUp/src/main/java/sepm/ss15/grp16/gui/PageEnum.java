package sepm.ss15.grp16.gui;

/**
 * Created by Maximilian on 01.06.2015.
 */
public enum PageEnum {

    Main("fxml/main/Main.fxml", "Main", 500, 500),
    Registration("fxml/user/Registration.fxml", "Registration", 10, 10),
    Login("fxml/user/Login.fxml", "Login", 10, 10);

    private final String fxml;
    private final String title;
    private final double minWidth;
    private final double minHeight;

    PageEnum(String fxml, String title, double minWidth, double minHeight) {
        this.fxml = fxml;
        this.title = title;
        this.minWidth = minWidth;
        this.minHeight = minHeight;
    }

    public String getFxml() {
        return fxml;
    }

    public String getTitle() {
        return title;
    }

    public double getMinWidth() {
        return minWidth;
    }

    public double getMinHeight() {
        return minHeight;
    }
}
