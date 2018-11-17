package ohtu;

public class Submission {
    private int week;
    private int hours;
    private int[] exercises;
    private String course;

    public String getCourse(){
        return course;
    }
    
    public int getHours(){
        return hours;
    }
    
    public int getWeek(){
        return week;
    }
    
    public int getExerciseAmount(){
        return (exercises == null) ? 0 : exercises.length;
    }

    public String contentString(int maxExercises) {
        String format = (hours == 1) ? " tunti" : " tuntia";
        String exerciseString = "";
        for (int i = 0; i < exercises.length; ++i){
            exerciseString += " " + exercises[i];
            if (i < exercises.length - 1)
                exerciseString += ",";
        }
        return "viikko " + week + ": \n tehtyjä tehtäviä " + exercises.length + "/" + maxExercises + ", aikaa kului " + hours + format + ", tehdyt tehtävät:" + exerciseString;
    }
    
}