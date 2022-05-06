package lowe.mike.strimko.model;

import static java.util.Arrays.asList;
import static lowe.mike.strimko.model.Constants.SUDOKU_SIZE;
import static lowe.mike.strimko.model.Constants.getSudokuStreams;
import static lowe.mike.strimko.model.Difficulty.EASY;
import static lowe.mike.strimko.model.Difficulty.MEDIUM;
import static lowe.mike.strimko.model.FileHandler.copyPuzzlesToDirectory;
import static lowe.mike.strimko.model.FileHandler.listPuzzleFileNames;
import static lowe.mike.strimko.model.FileHandler.read;
import static lowe.mike.strimko.model.FileHandler.readPathsToPuzzles;
import static lowe.mike.strimko.model.FileHandler.write;
import static lowe.mike.strimko.model.Type.STRIMKO;
import static lowe.mike.strimko.model.Type.SUDOKU;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collection;
import lowe.mike.strimko.model.Grid.GridBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * {@link FileHandler} tests.
 *
 * @author Mike Lowe
 */
public final class FileHandlerTests {

  private static String TEST_PUZZLE_DIRECTORY;

  // for loading puzzle resources
  private static final String TEST_PUZZLES_RESOURCE_NAME = "/test-puzzles.txt";
  private static final Collection<String> PATHS_TO_PUZZLES = asList(
      "Strimko/Easy/Test-Strimko-1.txt",
      "Strimko/Easy/Test-Strimko-2.txt", "Strimko/Medium/Test-Invalid-Strimko-1.txt",
      "Strimko/Hard/Test-Strimko-1.txt", "Sudoku/Easy/Test-Sudoku-1.txt",
      "Sudoku/Medium/Test-Sudoku-1.txt",
      "Sudoku/Hard/Test-Sudoku-1.txt");

  // test strimko puzzle
  private static final int STRIMKO_SIZE = 4;
  private static final int[][] STRIMKO_STREAMS = {{1, 2, 2, 3}, {2, 1, 3, 2}, {4, 3, 1, 4},
      {3, 4, 4, 1}};
  private static final int[][] STRIMKO_NUMBERS = {{0, 0, 0, 2}, {0, 0, 0, 0}, {0, 0, 0, 0},
      {4, 0, 0, 1}};
  private static final Grid STRIMKO_GRID = new GridBuilder(STRIMKO_SIZE).setStreams(STRIMKO_STREAMS)
      .setNumbers(STRIMKO_NUMBERS).build();
  private static final Puzzle STRIMKO_PUZZLE = new Puzzle(STRIMKO, STRIMKO_GRID);

  // test sudoku puzzle
  private static final int[][] SUDOKU_NUMBERS = {{8, 0, 0, 0, 0, 0, 4, 6, 0},
      {0, 9, 7, 6, 0, 1, 2, 0, 8},
      {0, 4, 0, 8, 0, 5, 0, 1, 0}, {0, 0, 0, 2, 7, 9, 0, 0, 4}, {0, 5, 0, 0, 0, 0, 0, 2, 0},
      {1, 0, 0, 4, 5, 3, 0, 0, 0}, {0, 7, 0, 3, 0, 6, 0, 9, 0}, {3, 0, 2, 9, 0, 7, 5, 4, 0},
      {0, 6, 1, 0, 0, 0, 0, 0, 2}};
  private static final Grid SUDOKU_GRID = new GridBuilder(SUDOKU_SIZE)
      .setStreams(getSudokuStreams())
      .setNumbers(SUDOKU_NUMBERS).build();
  private static final Puzzle SUDOKU_PUZZLE = new Puzzle(SUDOKU, SUDOKU_GRID);

  @BeforeAll
  public static void setup() throws IOException, FileHandlingException {
    TEST_PUZZLE_DIRECTORY = Files.createTempDirectory("strimko").toAbsolutePath().toString();
    copyPuzzlesToDirectory(TEST_PUZZLE_DIRECTORY, PATHS_TO_PUZZLES);
  }

  @Test
  public void test_readPathsToPuzzles() {
    // execution
    Collection<String> result = readPathsToPuzzles(TEST_PUZZLES_RESOURCE_NAME);

    // verification
    assertTrue(result.containsAll(PATHS_TO_PUZZLES));
  }

  @Test
  public void test_copyPuzzlesToDirectory() {
    for (String pathToPuzzle : PATHS_TO_PUZZLES) {
      File file = new File(TEST_PUZZLE_DIRECTORY, pathToPuzzle);
      assertTrue(file.exists());
    }
  }

  @Test
  public void test_listPuzzleFileNames_nullType() throws FileHandlingException {
    test_listPuzzleFileNames_noPuzzles(null, EASY);
  }

  @Test
  public void test_listPuzzleFileNames_nullDifficulty() throws FileHandlingException {
    test_listPuzzleFileNames_noPuzzles(STRIMKO, null);
  }

  private void test_listPuzzleFileNames_noPuzzles(Type type, Difficulty difficulty)
      throws FileHandlingException {
    // execution
    Collection<String> result = listPuzzleFileNames(TEST_PUZZLE_DIRECTORY, type, difficulty);

    // verification
    assertTrue(result.isEmpty());
  }

  @Test
  public void test_listPuzzleFileNames() throws FileHandlingException {
    // setup
    Collection<String> expected = asList("Test-Strimko-1", "Test-Strimko-2");

    // execution
    Collection<String> result = listPuzzleFileNames(TEST_PUZZLE_DIRECTORY, STRIMKO, EASY);

    // verification
    assertTrue(result.containsAll(expected));
  }

  @Test
  public void test_read_invalid() {
    assertThrows(FileHandlingException.class,
        () -> read(TEST_PUZZLE_DIRECTORY, STRIMKO, MEDIUM, "Test-Invalid-Strimko-1"));
  }

  @Test
  public void test_read_strimko() throws FileHandlingException {
    test_read(STRIMKO_PUZZLE, "Test-Strimko-1");
  }

  @Test
  public void test_read_sudoku() throws FileHandlingException {
    test_read(SUDOKU_PUZZLE, "Test-Sudoku-1");
  }

  private void test_read(Puzzle expectedPuzzle, String fileName) throws FileHandlingException {
    // setup
    Type type = expectedPuzzle.getType();
    Difficulty difficulty = expectedPuzzle.getDifficulty();

    // execution
    Puzzle result = read(TEST_PUZZLE_DIRECTORY, type, difficulty, fileName);

    // verification
    assertEquals(expectedPuzzle, result);
  }

  @Test
  public void test_write_strimko() throws FileHandlingException {
    test_write(STRIMKO_PUZZLE, "Strimko-1");
  }

  @Test
  public void test_write_sudoku() throws FileHandlingException {
    test_write(SUDOKU_PUZZLE, "Sudoku-1");
  }

  private void test_write(Puzzle expectedPuzzle, String expectedFileName)
      throws FileHandlingException {
    // setup
    Type type = expectedPuzzle.getType();
    Difficulty difficulty = expectedPuzzle.getDifficulty();

    // execution
    String actualFileName = write(TEST_PUZZLE_DIRECTORY, expectedPuzzle);
    Puzzle actualPuzzle = read(TEST_PUZZLE_DIRECTORY, type, difficulty, actualFileName);

    // verification
    assertEquals(expectedFileName, actualFileName);
    assertEquals(expectedPuzzle, actualPuzzle);
  }

}
