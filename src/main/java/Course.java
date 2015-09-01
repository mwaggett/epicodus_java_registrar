import org.sql2o.*;
import java.util.List;

public class Course {

  private int id;
  private String name;
  private String course_number;

  public Course (String name, String course_number) {
    this.name = name;
    this.course_number = course_number;
  }

  public String getName() {
    return name;
  }

  public String getCourseNumber() {
    return course_number;
  }


  public int getId() {
    return id;
  }

  @Override
  public boolean equals(Object otherCourseInstance) {
    if (!(otherCourseInstance instanceof Course)) {
      return false;
    } else {
      Course newCourseInstance = (Course) otherCourseInstance;
      return this.getName().equals(newCourseInstance.getName()) &&
             this.getCourseNumber().equals(newCourseInstance.getCourseNumber()) &&
             this.getId() == newCourseInstance.getId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO courses (name, course_number) VALUES (:name, :course_number);";
      this.id = (int) con.createQuery(sql, true)
          .addParameter("name", name)
          .addParameter("course_number", course_number)
          .executeUpdate()
          .getKey();
    }
  }

  public void updateName(String name) {
    this.name = name;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE courses SET name = :name WHERE id = :id";
      con.createQuery(sql)
        .addParameter("name", name)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public void updateCourseNumber(String course_number) {
    this.course_number = course_number;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE courses SET course_number = :course_number WHERE id = :id";
      con.createQuery(sql)
        .addParameter("course_number", course_number)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String deleteQuery = "DELETE FROM courses WHERE id = :id";
        con.createQuery(deleteQuery)
          .addParameter("id", id)
          .executeUpdate();
      String joinDeleteQuery = "DELETE FROM students_courses WHERE course_id = :id";
        con.createQuery(joinDeleteQuery)
          .addParameter("id", id)
          .executeUpdate();
    }
  }

  public static List<Course> all() {
    String sql = "SELECT * FROM courses";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Course.class);
    }
  }

  public static Course find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM courses WHERE id=:id";
      Course course = con.createQuery(sql)
          .addParameter("id", id)
          .executeAndFetchFirst(Course.class);
      return course;
    }
  }

}
