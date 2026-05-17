package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private HashMap<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable;

    public Timetable() {
        timetable = new HashMap<>();
    }

    public void addNewTrainingSession(TrainingSession trainingSession) {

        DayOfWeek day = trainingSession.getDayOfWeek();
        TimeOfDay startTime = trainingSession.getTimeOfDay();

        // если для дня ещё нет расписания — создаём
        timetable.putIfAbsent(day, new TreeMap<>());

        TreeMap<TimeOfDay, List<TrainingSession>> daySchedule = timetable.get(day);

        // если для времени ещё нет списка тренировок — создаём
        daySchedule.putIfAbsent(startTime, new ArrayList<>());

        // добавляем тренировку
        daySchedule.get(startTime).add(trainingSession);
    }

    public TreeMap<TimeOfDay, List<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        return timetable.get(dayOfWeek);
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay startTime) {

        TreeMap<TimeOfDay, List<TrainingSession>> daySchedule = timetable.get(dayOfWeek);

        if (daySchedule == null) {
            return new ArrayList<>();
        }

        return daySchedule.getOrDefault(startTime, new ArrayList<>());
    }

    public TreeMap<Integer, List<String>> getCountByCoaches() {

        HashMap<String, Integer> coachCount = new HashMap<>();

        for (TreeMap<TimeOfDay, List<TrainingSession>> daySchedule : timetable.values()) {

            for (List<TrainingSession> sessions : daySchedule.values()) {

                for (TrainingSession session : sessions) {

                    String coach = session.getCoach().toString();

                    coachCount.put(coach,
                            coachCount.getOrDefault(coach, 0) + 1);
                }
            }
        }

        TreeMap<Integer, List<String>> result = new TreeMap<>(Comparator.reverseOrder());

        for (String coach : coachCount.keySet()) {

            int count = coachCount.get(coach);

            result.putIfAbsent(count, new ArrayList<>());

            result.get(count).add(coach);
        }

        return result;
    }
}
