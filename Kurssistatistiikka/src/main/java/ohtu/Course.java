/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu;

/**
 *
 * @author Vertti
 */
public class Course {
    private String fullName;
    private String name;
    private int[] exercises;
    private String term;
    private String year;
    private int week;
    
    public String getName(){
        return name;
    }
    
    public int getWeek(){
        return week;
    }
    
    public int getExercises(int week){
        if (week < exercises.length)
            return exercises[week];
        return 0;
    }
    
    public int getTotalExercises(){
        int e = 0;
        for (int i = 0; i <= week; ++i){
            e += exercises[i];
        }
        return e;
    }

    @Override
    public String toString() {
        return fullName + ", " + term + " " + year;
    }
}
