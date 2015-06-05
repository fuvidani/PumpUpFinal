package sepm.ss15.grp16.gui;

/**
 * Created by Maximilian on 01.06.2015.
 */
public enum PageEnum {

    Main("fxml/main/Main.fxml", "Main", 1000, 620),
    Registration("fxml/user/Registration.fxml", "Registration", 10, 10),
    Login("fxml/user/Login.fxml", "Login", 10, 10),
    UserEdit("fxml/user/UserEdit.fxml", "Userdaten bearbeiten", 10, 10),
    Workoutplan("fxml/workoutPlans/Workoutplans.fxml", "Traininspläne", 1000, 620),
    Workoutplan_create_edit("fxml/workoutPlans/Create_Edit_WorkoutPlans.fxml", "Traininsplan bearbeiten/erstellen", 1000, 620),
    Workoutplan_generate("fxml/workoutPlans/GenerateWorkoutPlan.fxml", "Trainingsplan generieren", 1000, 620),
    Workoutplan_calender_dialog("fxml/workoutPlans/WorkoutPlanIntoCalendar.fxml", "Trainingsplan in Kalender exportieren", 800, 600),
    SessionEdit("fxml/workoutPlans/SessionEdit_v2.fxml", "Session hinzuf\u00fcgen", 600, 400),
    Calendar("fxml/calendar/Calendar.fxml", "Trainingskalender", 1000, 620);

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
