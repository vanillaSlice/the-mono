package interactive_coding_challenges.arrays_and_strings;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;

/**
 * @author Mike Lowe
 */
public final class HashTableTest {

  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  private HashTable hashTable;

  @Before
  public void setup() {
    hashTable = new HashTable(10);
  }

  @Test
  public void constructor_negativeSize_throwsIllegalArgumentException() {
    expectedException.expect(IllegalArgumentException.class);
    new HashTable(-1);
  }

  @Test
  public void get_noMatchingKey_throwsNoSuchElementException() {
    expectedException.expect(NoSuchElementException.class);
    hashTable.get(1);
  }

  @Test
  public void get_matchingKey_returnsValue() {
    hashTable.set(1, "Hello");
    assertEquals("Hello", hashTable.get(1));
  }

  @Test
  public void set_update_updatesExistingValue() {
    hashTable.set(1, "Hello");
    hashTable.set(1, "World");
    assertEquals("World", hashTable.get(1));
  }

  @Test
  public void remove_noMatchingKey_throwsNoSuchElementException() {
    expectedException.expect(NoSuchElementException.class);
    hashTable.remove(1);
  }

  @Test
  public void remove_existingKey() {
    hashTable.set(1, "Hello");
    hashTable.remove(1);
    expectedException.expect(NoSuchElementException.class);
    hashTable.get(1);
  }

}
