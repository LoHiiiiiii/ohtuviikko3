package ohtu;

public class Submission {
    private int week;
    private int hours;
    private int[] exercises;
    private String course;

    public int getHours(){
        return hours;
    }
    
    public int getExerciseAmount(){
        return (exercises == null) ? 0 : exercises.length;
    }
    
    @Override
    public String toString() {
        String format = (hours == 1) ? " tunti" : " tuntia";
        String exerciseString = "";
        for (int i = 0; i < exercises.length; ++i){
            exerciseString += " " + exercises[i];
            if (i < exercises.length - 1)
                exerciseString += ",";
        }
        return course + ", viikko " + week + " tehtyjä tehtäviä yhteensä " + exercises.length + ", aikaa kului " + hours + format + ", tehdyt tehtävät:" + exerciseString;
    }
    
}