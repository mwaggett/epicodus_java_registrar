import java.util.Random;
import java.util.HashMap;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
import java.lang.*;
import static spark.Spark.*;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import java.util.Map;

public class App {

  public static void main(String[] args) {

    staticFileLocation("/public"); // Relative path for images, css, etc.
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/students", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      List<Student> students = Student.all();
      model.put("students", students);
      model.put("template", "templates/students.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/students/new", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      int currentYear = Calendar.getInstance().get(Calendar.YEAR);
      ArrayList<Integer> years = new ArrayList<Integer>();
      for (int year = currentYear - 5; year <= currentYear + 5; year++) {
        years.add(year);
      }

      HashMap<Integer, String> months = new HashMap<Integer, String>();
      months.put(1, "January");
      months.put(2, "February");
      months.put(3, "March");
      months.put(4, "April");
      months.put(5, "May");
      months.put(6, "June");
      months.put(7, "July");
      months.put(8, "August");
      months.put(9, "September");
      months.put(10, "October");
      months.put(11, "November");
      months.put(12, "December");

      model.put("years", years);
      model.put("months", months);
      model.put("template", "templates/new-student-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/students/create", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      String name = request.queryParams("name");
      int year = Integer.parseInt(request.queryParams("year"));
      int month = Integer.parseInt(request.queryParams("month"));
      int day = Integer.parseInt(request.queryParams("day"));

      Student newStudent = new Student(name, year, month, day);
      newStudent.save();

      response.redirect("/students");
      return null;
    });

    get("/students/:id/courses", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      Student student = Student.find(Integer.parseInt(request.params(":id")));
      List<Course> courses = student.getCourses();

      model.put("student", student);
      model.put("courses", courses);
      model.put("template", "templates/student-courses.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/students/:id/courses/add", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      Student student = Student.find(Integer.parseInt(request.params(":id")));

      List<Course> courses = Course.all();
      List<Course> enrolledCourses = student.getCourses();
      ArrayList<Course> unenrolledCourses = new ArrayList<Course>();
      for (Course course : courses) {
        if (!(enrolledCourses.contains(course))) {
          unenrolledCourses.add(course);
        }
      }

      model.put("student", student);
      model.put("unenrolledCourses", unenrolledCourses);
      model.put("template", "templates/add-course-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/students/:id/courses/add", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      Student student = Student.find(Integer.parseInt(request.params(":id")));

      Object[] params = request.queryParams().toArray();
      for (Object param : params) {
        String courseId = (String) request.queryParams((String) param);
        Course course = Course.find(Integer.parseInt(courseId));
        student.addCourse(course);
      }

      String redirectPath = String.format("/students/%d/courses", student.getId());

      response.redirect(redirectPath);
      return null;
    });

    get("/courses", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      List<Course> courses = Course.all();
      model.put("courses", courses);
      model.put("template", "templates/courses.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/courses/new", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      model.put("template", "templates/new-course-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/courses/create", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      String name = request.queryParams("name");
      String courseNumber = request.queryParams("coursenumber");

      Course newCourse = new Course(name, courseNumber);
      newCourse.save();

      response.redirect("/courses");
      return null;
    });

    get("/courses/:id/students", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      Course course = Course.find(Integer.parseInt(request.params(":id")));
      List<Student> students = course.getStudents();

      model.put("students", students);
      model.put("course", course);
      model.put("template", "templates/course-students.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/courses/:id/students/add", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      Course course = Course.find(Integer.parseInt(request.params(":id")));
      List<Student> students = Student.all();
      List<Student> enrolledStudents = course.getStudents();
      ArrayList<Student> unenrolledStudents = new ArrayList<Student>();
      for (Student student : students) {
        if (!enrolledStudents.contains(student)) {
          unenrolledStudents.add(student);
        }
      }

      model.put("course", course);
      model.put("unenrolledStudents", unenrolledStudents);
      model.put("template", "templates/add-student-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/courses/:id/students/add", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      Course course = Course.find(Integer.parseInt(request.params(":id")));

      Object[] params = request.queryParams().toArray();
      for (Object param : params) {
        String studentId = (String) request.queryParams((String) param);
        Student student = Student.find(Integer.parseInt(studentId));
        course.addStudent(student);
      }

      String redirectPath = String.format("/courses/%d/students", course.getId());

      response.redirect(redirectPath);
      return null;
    });

  }

}
