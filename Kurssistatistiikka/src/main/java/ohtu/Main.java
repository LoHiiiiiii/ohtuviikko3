package ohtu;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.http.client.fluent.Request;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Main {

    public static void main(String[] args) throws IOException {
        // ÄLÄ laita githubiin omaa opiskelijanumeroasi
        String studentNr = "012345678";
        if (args.length > 0) {
            studentNr = args[0];
        }

        String url = "https://studies.cs.helsinki.fi/courses/students/" + studentNr + "/submissions";

        String bodyTextStudent = Request.Get(url).execute().returnContent().asString();
        String bodyTextCourse = Request.Get("https://studies.cs.helsinki.fi/courses/courseinfo").execute().returnContent().asString();

        Gson mapper = new Gson();
        Submission[] subs = mapper.fromJson(bodyTextStudent, Submission[].class);

        Course[] courses = mapper.fromJson(bodyTextCourse, Course[].class);
        ArrayList<Submission> submissionsForACourse = new ArrayList<>();
        System.out.println("opiskelijanumero " + studentNr + "\n");

        for (Course course : courses) {
            for (Submission submission : subs) {
                if (submission.getCourse().equals(course.getName())) {
                    submissionsForACourse.add(submission);
                }
            }
            if (submissionsForACourse.isEmpty()) {
                continue;
            }
            System.out.println(course + "\n");

            int hours = 0;
            int exercises = 0;
            for (Submission submission : submissionsForACourse) {
                System.out.println(submission.contentString(course.getExercises(submission.getWeek())));
                hours += submission.getHours();
                exercises += submission.getExerciseAmount();
            }
            String exerciseFormat = (exercises == 1) ? " tehtävä, " : " tehtävää, ";
            String hourFormat = (hours == 1) ? " tunti" : " tuntia";
            System.out.println("\nyhteensä: " + exercises + "/" + course.getTotalExercises() + exerciseFormat + hours + hourFormat + "\n");
            String statsResponse = Request.Get("https://studies.cs.helsinki.fi/courses/" + course.getName() + "/stats").execute().returnContent().asString();

            JsonParser parser = new JsonParser();
            JsonObject parsedData = parser.parse(statsResponse).getAsJsonObject();
            int totalHours = 0, totalStudents = 0, totalExercises = 0;
            for (int i = 1; i <= course.getWeek(); ++i){
                totalHours += parsedData.get(""+i).getAsJsonObject().get("hour_total").getAsInt();
                totalStudents += parsedData.get(""+i).getAsJsonObject().get("students").getAsInt();
                totalExercises += parsedData.get(""+i).getAsJsonObject().get("exercise_total").getAsInt();
            }
            String studentFormat = (totalStudents == 1) ? " palautus," : " palautusta, ";
            System.out.println("Kurssilla on yhteensä " + totalStudents + studentFormat + 
                    "palautettuja tehtäviä " + totalExercises + " kpl, aikaa käytetty yhteensä " + totalHours + "\n");
            submissionsForACourse.clear();
            
        }
    }
}
