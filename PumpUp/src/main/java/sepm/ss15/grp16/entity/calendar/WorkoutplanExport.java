package sepm.ss15.grp16.entity.calendar;

import sepm.ss15.grp16.entity.training.Trainingsplan;

import java.time.DayOfWeek;
import java.util.Date;

/**
 * Created by David on 2015.05.17..
 */
public class WorkoutplanExport {

    private Trainingsplan trainingsplan;
    private DayOfWeek[] days; //eg: days = {DayOfWeek.FRIDAY, DayOfWeek.SUNDAY};
    private Date datum;

    public WorkoutplanExport(Trainingsplan trainingsplan, DayOfWeek[] days, Date datum) {
        this.trainingsplan = trainingsplan;
        this.days = days;
        this.datum = datum;
    }

    public Trainingsplan getTrainingsplan() {
        return trainingsplan;
    }

    public void setTrainingsplan(Trainingsplan trainingsplan) {
        this.trainingsplan = trainingsplan;
    }

    public DayOfWeek[] getDays() {
        return days;
    }

    public void setDays(DayOfWeek[] days) {
        this.days = days;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }
}
