package interactive_coding_challenges.arrays_and_strings;

import java.util.NoSuchElementException;

/**
 * PROBLEM:
 * Implement a simple hash table with set, get, and remove methods.
 *
 * @author Mike Lowe
 */
public final class HashTable {

  private final int size;
  private final String[] array;

  public HashTable(final int size) {
    this.size = size;
    if (this.size < 0) {
      throw new IllegalArgumentException("size cannot be less than 0 but was " + this.size);
    }
    this.array = new String[this.size];
  }

  private int getHashCode(final int key) {
    return key % size;
  }

  public void set(final int key, final String value) {
    array[getHashCode(key)] = value;
  }

  public String get(final int key) {
    final String value = array[getHashCode(key)];
    if (value == null) {
      throw new NoSuchElementException("No value at " + key);
    }
    return value;
  }

  public void remove(final int key) {
    final int hashCode = getHashCode(key);
    final String value = array[hashCode];
    if (value == null) {
      throw new NoSuchElementException("No value at " + key);
    }
    array[hashCode] = null;
  }

}
