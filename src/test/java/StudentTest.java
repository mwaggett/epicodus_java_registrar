import org.junit.*;
import static org.junit.Assert.*;

public class StudentTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(0, Student.all().size());
  }

  @Test
  public void getName_returnsName() {
    Student myStudent = new Student("Jim Bob", 2015, 9, 1);
    assertEquals("Jim Bob", myStudent.getName());
  }

  @Test
  public void getEnrollmentYear_returnsYear() {
    Student myStudent = new Student("Jim Bob", 2015, 9, 1);
    assertEquals(2015, myStudent.getEnrollmentYear());
  }

  @Test
  public void getEnrollmentMonth_returnsMonth() {
    Student myStudent = new Student("Jim Bob", 2015, 9, 1);
    assertEquals(9, myStudent.getEnrollmentMonth());
  }

  @Test
  public void getEnrollmentDay_returnsDay() {
    Student myStudent = new Student("Jim Bob", 2015, 9, 1);
    assertEquals(1, myStudent.getEnrollmentDay());
  }

  @Test
  public void getId_returnsIdAfterSave() {
    Student instance = new Student("Jim Bob", 2015, 9, 1);
    instance.save();
    assertEquals(Student.all().get(0).getId(), instance.getId());
  }

  @Test
  public void equals_returnsTrueWhenParamsMatch() {
    Student firstInstance = new Student("Jim Bob", 2015, 9, 1);
    Student secondInstance = new Student("Jim Bob", 2015, 9, 1);
    assertEquals(true, firstInstance.equals(secondInstance));
  }

  @Test
  public void equals_returnsFalseWhenParamsAreDifferent() {
    Student firstInstance = new Student("Jim Bob", 2015, 9, 1);
    Student secondInstance = new Student("Billy Joe", 2015, 5, 21);
    assertEquals(false, firstInstance.equals(secondInstance));
  }

  @Test
  public void save_addsToDatabase() {
    Student instance = new Student("Jim Bob", 2015, 9, 1);
    instance.save();
    assertEquals(Student.all().get(0), instance);
  }

  @Test
  public void find_findsStudentInDatabase_true() {
    Student myStudent = new Student("Jim Bob", 2015, 9, 1);
    myStudent.save();
    Student savedStudent = Student.find(myStudent.getId());
    assertTrue(myStudent.equals(savedStudent));
  }

  @Test
  public void updateName_changesNameInDatabase_true() {
    Student myStudent = new Student("Jim Bob", 2015, 9, 1);
    myStudent.save();
    String newName = "Billy Joe";
    myStudent.updateName(newName);
    assertEquals(newName, Student.all().get(0).getName());
  }

  @Test
  public void updateEnrollmentDate_changesDateInDatebase_true(){
    Student myStudent = new Student("Jim Bob", 2015, 9, 1);
    myStudent.save();
    int newYear = 2016;
    int newMonth = 11;
    int newDay = 30;
    myStudent.updateEnrollmentDate(newYear, newMonth, newDay);
    assertEquals("11/30/2016", Student.all().get(0).getEnrollmentDate());
  }

  @Test
  public void delete_FromDatabase_true() {
    Student myStudent = new Student("Jim Bob", 2015, 9, 1);
    myStudent.save();
    myStudent.delete();
    assertEquals(0, Student.all().size());
  }

  @Test
  public void getCourses_returnsAddedCourses() {
    Student myStudent = new Student("Jim Bob", 2015, 9, 1);
    myStudent.save();
    Course firstInstance = new Course("Learn to Food","FOOD101");
    firstInstance.save();
    Course secondInstance = new Course("Learn About Herbs", "HERB201");
    secondInstance.save();
    myStudent.addCourse(firstInstance);
    myStudent.addCourse(secondInstance);
    assertTrue(myStudent.getCourses().contains(firstInstance));
    assertEquals(secondInstance, myStudent.getCourses().get(1));
  }
}
