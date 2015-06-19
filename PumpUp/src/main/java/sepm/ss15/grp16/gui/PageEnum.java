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
    Workoutplan_generate_result("fxml/workoutPlans/GeneratedWorkoutPlanResult.fxml", "Generierter Trainingsplan", 300, 300),
    Workoutplan_calender_dialog("fxml/workoutPlans/WorkoutPlanIntoCalendar.fxml", "Trainingsplan in Kalender exportieren", 800, 600),
    SessionEdit("fxml/workoutPlans/SessionEdit_v2.fxml", "Session hinzuf\u00fcgen", 1000, 400),
    Calendar("fxml/calendar/Calendar.fxml", "Trainingskalender", 1000, 600),
    LiveMode("fxml/workout/Workout.fxml", "Workout", 1300, 750),
    WorkoutResult("fxml/workout/WorkoutResult.fxml", "Workout Resultate", 1300, 750),
    Playlist("fxml/workout/Playlist.fxml", "Playlist", 300, 500),
    Workoutstart("fxml/workout/Workoutstart.fxml", "Mit Training beginnen", 800, 600),
    About("fxml/main/About.fxml", "\u00dcber", 600, 400),
    Fotos("fxml/user/BodyPhotos.fxml", "Fotos", 1000, 750),
    Exercises("fxml/exercise/Exercises.fxml", "\u00dcbungen", 1300, 600),
    Manage_exercises("fxml/exercise/ManageExercise.fxml", "\u00dcbungen verwalten", 1300, 750),
    DisplayExercise("fxml/exercise/DisplayExercise.fxml", "\u00dcbung anzeigen", 1000, 600),
    VideoPlayer("fxml/exercise/VideoPlayer.fxml", "\u00dcbung anzeigen", 300, 600),
    BodyfatHelp("fxml/main/BodyfatHelp.fxml", "Hilfe", 800, 600);

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
