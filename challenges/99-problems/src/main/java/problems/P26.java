package problems;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

class P26 {

  static <T> List<List<T>> combinations(final List<T> list, final int size) {
    if (size <= 0 || list.isEmpty()) {
      return emptyList();
    }
    if (size == 1) {
      return list.stream().map(e -> Stream.of(e).collect(toList())).collect(toList());
    }

    final List<List<T>> combinations = new ArrayList<>();

    for (int i = 0; i < list.size(); i++) {
      final List<List<T>> subCombinations = combinations(list.subList(i + 1, list.size()), size - 1);
      for (final List<T> subCombination : subCombinations) {
        subCombination.add(0, list.get(i));
      }
      combinations.addAll(subCombinations);
    }

    return combinations;
  }

}
