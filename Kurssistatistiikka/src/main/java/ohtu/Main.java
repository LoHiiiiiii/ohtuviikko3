package ohtu;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.http.client.fluent.Request;

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
            submissionsForACourse.clear();
        }
    }
}
