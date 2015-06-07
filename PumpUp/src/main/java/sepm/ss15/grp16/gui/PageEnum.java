package sepm.ss15.grp16.gui;

/**
 * Created by Maximilian on 01.06.2015.
 */
public enum PageEnum {

    Main("fxml/main/Main.fxml", "Main", 1000, 620),
    Registration("fxml/user/Registration.fxml", "Registration", 10, 10),
    Login("fxml/user/Login.fxml", "Login", 400, 400),
    UserEdit("fxml/user/UserEdit.fxml", "Userdaten bearbeiten", 10, 10),
    PhotoDiary("fxml/user/PhotoDiary.fxml", "Fototagebuch", 700, 600),
    Webcam("fxml/user/Webcam.fxml", "Webcam", 900, 690),
    Workoutplan("fxml/workoutPlans/Workoutplans.fxml", "Traininspläne", 1000, 620),
    Workoutplan_create_edit("fxml/workoutPlans/Create_Edit_WorkoutPlans.fxml", "Traininsplan bearbeiten/erstellen", 1000, 620),
    Workoutplan_generate("fxml/workoutPlans/GenerateWorkoutPlan.fxml", "Trainingsplan generieren", 1000, 620),
    Workoutplan_calender_dialog("fxml/workoutPlans/WorkoutPlanIntoCalendar.fxml", "Trainingsplan in Kalender exportieren", 800, 600),
    SessionEdit("fxml/workoutPlans/SessionEdit_v2.fxml", "Session hinzuf\u00fcgen", 600, 400),
    Calendar("fxml/calendar/Calendar.fxml", "Trainingskalender", 1000, 620),
    Exercises("fxml/exercise/Exercises.fxml", "\u00dcbungen", 1000, 620),
    Manage_exercises("fxml/exercise/ManageExercise.fxml", "\\u00dcbungen verwalten", 1300, 750),
    LiveMode("fxml/workout/Workout.fxml", "", 1300, 750),
    Playlist("fxml/workout/Playlist.fxml", "Playlist", 300, 500),
    Workoutstart("fxml/workout/Workoutstart.fxml", "Mit Training beginnen", 800, 600),
    About("fxml/main/About.fxml", "Über", 600, 400),
    Fotos("fxml/user/BodyPhotos.fxml", "Fotos", 1000, 600);

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
