package interactive_coding_challenges.linked_lists;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import static java.util.Objects.requireNonNull;

/**
 * PROBLEM:
 * Remove duplicates from a linked list.
 *
 * @author Mike Lowe
 */
public final class RemoveDuplicates {

  // don't want instances
  private RemoveDuplicates() {
  }

  public static <E> void remove(final LinkedList<E> list) {
    requireNonNull(list, "list cannot be null");

    final Set<E> seen = new HashSet<>();

    final Iterator<E> iterator = list.iterator();
    while (iterator.hasNext()) {
      final E element = iterator.next();
      if (seen.contains(element)) {
        iterator.remove();
      } else {
        seen.add(element);
      }
    }
  }

}
