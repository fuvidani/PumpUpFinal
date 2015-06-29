package sepm.ss15.grp16.entity.training;

import sepm.ss15.grp16.entity.calendar.Appointment;
import sepm.ss15.grp16.entity.training.helper.ExerciseSet;

import java.util.LinkedHashMap;

/**
 * Created by Maximilian on 07.06.2015.
 */
public class WorkoutResult {

    private Appointment appointment;

    private LinkedHashMap<ExerciseSet, ExecutionTimePair> list = new LinkedHashMap<>();

    public WorkoutResult(Appointment appointment) {
        this.appointment = appointment;
    }

    public void setExecution(ExerciseSet exerciseSet, Integer repetion, Integer duration) {
        list.put(exerciseSet, new ExecutionTimePair(repetion, duration));
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public LinkedHashMap<ExerciseSet, ExecutionTimePair> getList() {
        return list;
    }

    @Override
    public int hashCode() {
        int result = appointment != null ? appointment.hashCode() : 0;
        result = 31 * result + (list != null ? list.hashCode() : 0);
        return result;
    }    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WorkoutResult that = (WorkoutResult) o;

        if (appointment != null ? !appointment.equals(that.appointment) : that.appointment != null) return false;
        return !(list != null ? !list.equals(that.list) : that.list != null);

    }

    public class ExecutionTimePair {
        private final Integer repetion;
        private final Integer duration;

        public ExecutionTimePair(Integer repetion, Integer duration) {
            this.repetion = repetion;
            this.duration = duration;
        }

        public Integer getRepetion() {
            return repetion;
        }

        public Integer getDuration() {
            return duration;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ExecutionTimePair that = (ExecutionTimePair) o;

            if (repetion != null ? !repetion.equals(that.repetion) : that.repetion != null) return false;
            return !(duration != null ? !duration.equals(that.duration) : that.duration != null);

        }

        @Override
        public int hashCode() {
            int result = repetion != null ? repetion.hashCode() : 0;
            result = 31 * result + (duration != null ? duration.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "ExecutionTimePair{" +
                    "repetion=" + repetion +
                    ", duration=" + duration +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "WorkoutResult{" +
                "appointment=" + appointment +
                ", list=" + list +
                '}';
    }


}
