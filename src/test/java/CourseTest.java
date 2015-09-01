import org.junit.*;
import static org.junit.Assert.*;

public class CourseTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(0, Course.all().size());
  }

  @Test
  public void getName_returnsName() {
    Course myCourse = new Course( "Learn to Food", "FOOD101");
    assertEquals("Learn to Food", myCourse.getName());
  }

  @Test
  public void getId_returnsIdAfterSave() {
    Course instance = new Course("Learn to Food","FOOD101");
    instance.save();
    assertEquals(Course.all().get(0).getId(), instance.getId());
  }

  @Test
  public void equals_returnsTrueWhenParamsMatch() {
    Course firstInstance = new Course("Learn to Food","FOOD101");
    Course secondInstance = new Course("Learn to Food","FOOD101");
    assertEquals(true, firstInstance.equals(secondInstance));
  }

  @Test
  public void equals_returnsFalseWhenParamsAreDifferent() {
    Course firstInstance = new Course("Learn to Food","FOOD101");
    Course secondInstance = new Course("Learn About Herbs", "HERB201");
    assertEquals(false, firstInstance.equals(secondInstance));
  }

  @Test
  public void save_addsToDatabase() {
    Course instance = new Course("Learn to Food","FOOD101");
    instance.save();
    assertEquals(Course.all().get(0), instance);
  }

  @Test
  public void find_findsCourseInDatabase_true() {
    Course myCourse = new Course("Learn to Food","FOOD101");
    myCourse.save();
    Course savedCourse = Course.find(myCourse.getId());
    assertTrue(myCourse.equals(savedCourse));
  }

  @Test
  public void updateName_changesNameInDatabase_true() {
    Course myCourse = new Course("Learn to Food","FOOD101");
    myCourse.save();
    String newName = "Defense Against the Dark Arts";
    myCourse.updateName(newName);
    assertEquals(newName, Course.all().get(0).getName());
  }

  @Test
  public void updateCourseNumber_changesCourseNumberInDatabase_true() {
    Course myCourse = new Course("Learn to Food","FOOD101");
    myCourse.save();
    String newCourseNumber = "YUM101";
    myCourse.updateCourseNumber(newCourseNumber);
    assertEquals(newCourseNumber, Course.all().get(0).getCourseNumber());
  }

  @Test
  public void delete_FromDatabase_true() {
    Course myCourse = new Course("Learn to Food","FOOD101");
    myCourse.save();
    myCourse.delete();
    assertEquals(0, Course.all().size());
  }
}
