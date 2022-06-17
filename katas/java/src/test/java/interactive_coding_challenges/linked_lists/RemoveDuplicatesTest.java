package interactive_coding_challenges.linked_lists;

import org.junit.Test;

import java.util.LinkedList;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * @author Mike Lowe
 */
public final class RemoveDuplicatesTest {

  @Test(expected = NullPointerException.class)
  public void remove_null_throwsNullPointerException() {
    RemoveDuplicates.remove(null);
  }

  @Test
  public void remove_empty_listStillEmpty() {
    // setup
    final LinkedList<Object> list = new LinkedList<>();

    // execution
    RemoveDuplicates.remove(list);

    // verification
    assertTrue(list.isEmpty());
  }

  @Test
  public void remove_oneElement_listStillContainsOneElement() {
    // setup
    final LinkedList<Object> list = new LinkedList<>();
    list.add("Hello");

    // execution
    RemoveDuplicates.remove(list);

    // verification
    assertEquals(1, list.size());
    assertTrue(list.contains("Hello"));
  }

  @Test
  public void remove_duplicates_removesDuplicates() {
    // setup
    final LinkedList<Object> list = new LinkedList<>();
    list.add("Hello");
    list.add(1);
    list.add(2);
    list.add("Hello");
    list.add("Hello");
    list.add(2);
    list.add("hello");
    list.add("World");
    list.add(3);

    // execution
    RemoveDuplicates.remove(list);

    // verification
    assertEquals(6, list.size());
    assertTrue(list.contains("Hello"));
    assertTrue(list.contains(1));
    assertTrue(list.contains(2));
    assertTrue(list.contains("hello"));
    assertTrue(list.contains("World"));
    assertTrue(list.contains(3));
  }

}
