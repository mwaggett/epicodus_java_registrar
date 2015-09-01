import org.sql2o.*;
import java.util.List;

public class Student {

  private int id;
  private String name;
  private int enrollment_year;
  private int enrollment_month;
  private int enrollment_day;

  public Student (String name, int enrollment_year, int enrollment_month, int enrollment_day) {
    this.name = name;
    this.enrollment_year = enrollment_year;
    this.enrollment_month = enrollment_month;
    this.enrollment_day = enrollment_day;
  }

  public String getName() {
    return name;
  }

  public int getEnrollmentYear() {
    return enrollment_year;
  }

  public int getEnrollmentMonth() {
    return enrollment_month;
  }

  public int getEnrollmentDay() {
    return enrollment_day;
  }

  public String getEnrollmentDate() {
    return String.format("%d/%d/%d", enrollment_month, enrollment_day, enrollment_year);
  }


  public int getId() {
    return id;
  }

  @Override
  public boolean equals(Object otherStudentInstance) {
    if (!(otherStudentInstance instanceof Student)) {
      return false;
    } else {
      Student newStudentInstance = (Student) otherStudentInstance;
      return this.getName().equals(newStudentInstance.getName()) &&
             this.getEnrollmentYear() == newStudentInstance.getEnrollmentYear() &&
             this.getEnrollmentMonth() == newStudentInstance.getEnrollmentMonth() &&
             this.getEnrollmentDay() == newStudentInstance.getEnrollmentDay() &&
             this.getId() == newStudentInstance.getId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO students (name, enrollment_year, enrollment_month, enrollment_day) VALUES (:name, :enrollment_year, :enrollment_month, :enrollment_day);";
      this.id = (int) con.createQuery(sql, true)
          .addParameter("name", name)
          .addParameter("enrollment_year", enrollment_year)
          .addParameter("enrollment_month", enrollment_month)
          .addParameter("enrollment_day", enrollment_day)
          .executeUpdate()
          .getKey();
    }
  }

  public void updateName(String name) {
    this.name = name;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE students SET name = :name WHERE id = :id";
      con.createQuery(sql)
        .addParameter("name", name)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public void updateEnrollmentDate(int year, int month, int day) {
    this.enrollment_year = year;
    this.enrollment_month = month;
    this.enrollment_day = day;
    try(Connection con = DB.sql2o.open()) {
      String updateYearQuery = "UPDATE students SET enrollment_year = :year WHERE id = :id";
      con.createQuery(updateYearQuery)
        .addParameter("year", year)
        .addParameter("id", id)
        .executeUpdate();
      String updateMonthQuery = "UPDATE students SET enrollment_month = :month WHERE id = :id";
      con.createQuery(updateMonthQuery)
        .addParameter("month", month)
        .addParameter("id", id)
        .executeUpdate();
      String updateDayQuery = "UPDATE students SET enrollment_day = :day WHERE id = :id";
      con.createQuery(updateDayQuery)
        .addParameter("day", day)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String deleteQuery = "DELETE FROM students WHERE id = :id";
        con.createQuery(deleteQuery)
          .addParameter("id", id)
          .executeUpdate();
      String joinDeleteQuery = "DELETE FROM students_courses WHERE student_id = :id";
        con.createQuery(joinDeleteQuery)
          .addParameter("id", id)
          .executeUpdate();
    }
  }

  public static List<Student> all() {
    String sql = "SELECT * FROM students";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Student.class);
    }
  }

  public static Student find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM students WHERE id=:id";
      Student student = con.createQuery(sql)
          .addParameter("id", id)
          .executeAndFetchFirst(Student.class);
      return student;
    }
  }

}
