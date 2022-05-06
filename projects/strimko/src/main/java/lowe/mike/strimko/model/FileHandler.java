package lowe.mike.strimko.model;

import static java.lang.Integer.parseInt;
import static java.nio.file.Files.copy;
import static java.nio.file.Paths.get;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static lowe.mike.strimko.model.Constants.PUZZLES_RESOURCE_NAME;
import static lowe.mike.strimko.model.Constants.SUDOKU_SIZE;
import static lowe.mike.strimko.model.Constants.USER_PUZZLE_DIRECTORY;
import static lowe.mike.strimko.model.Constants.getSudokuStreams;
import static lowe.mike.strimko.model.Type.STRIMKO;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import lowe.mike.strimko.model.Grid.GridBuilder;

/**
 * {@code FileHandler} provides useful methods for interacting with puzzle files.
 *
 * <p>Instances of {@code FileHandler} cannot be created.
 *
 * @author Mike Lowe
 */
public final class FileHandler {

  private static final String EXTENSION = ".txt";

  // don't want instances
  private FileHandler() {
  }

  /**
   * Reads the resource file with the given name and returns the paths to the puzzle files.
   *
   * @param resourceName the name of the resource file
   * @return the {@link Collection} of paths to puzzle files
   */
  static Collection<String> readPathsToPuzzles(String resourceName) {
    Collection<String> pathsToPuzzles = new HashSet<>();

    InputStream inputStream = FileHandler.class.getResourceAsStream(resourceName);
    Scanner scanner = new Scanner(inputStream);
    while (scanner.hasNextLine()) {
      String pathToPuzzle = scanner.nextLine();
      pathsToPuzzles.add(pathToPuzzle);
    }
    scanner.close();

    return pathsToPuzzles;
  }

  /**
   * Copies puzzles to a given output directory.
   *
   * @param outputDirectory directory to output the puzzle files to
   * @param pathsToPuzzles the {@link Collection} of paths to puzzle files
   * @throws FileHandlingException if the puzzles could not be copied to the output directory
   */
  static void copyPuzzlesToDirectory(String outputDirectory, Collection<String> pathsToPuzzles)
      throws FileHandlingException {
    for (String pathToPuzzle : pathsToPuzzles) {
      Path outputPath = get(outputDirectory, pathToPuzzle);
      makeParentDirectory(outputPath);
      copyPuzzle(pathToPuzzle, outputPath);
    }
  }

  private static void makeParentDirectory(Path path) throws FileHandlingException {
    makeDirectory(path.getParent());
  }

  private static void makeDirectory(Path path) throws FileHandlingException {
    try {
      path.toFile().mkdirs();
    } catch (UnsupportedOperationException | SecurityException e) {
      throw new FileHandlingException("Could not make directory '" + path + "'");
    }
  }

  private static void copyPuzzle(String pathToPuzzle, Path outputPath)
      throws FileHandlingException {
    try (InputStream inputStream = FileHandler.class
        .getResourceAsStream("/puzzles/" + pathToPuzzle)) {
      copy(inputStream, outputPath, REPLACE_EXISTING);
    } catch (UnsupportedOperationException | SecurityException | IOException e) {
      throw new FileHandlingException("Could not copy puzzles to '" + outputPath + "'");
    }
  }

  /**
   * Copies puzzles to the user directory.
   *
   * @throws FileHandlingException if the puzzles could not be copied to the user directory
   */
  public static void copyPuzzlesToUserDirectory() throws FileHandlingException {
    Collection<String> pathsToPuzzles = readPathsToPuzzles(PUZZLES_RESOURCE_NAME);
    copyPuzzlesToDirectory(USER_PUZZLE_DIRECTORY, pathsToPuzzles);
  }

  /**
   * Returns a {@link Collection} of puzzle file names given the {@link Type} and {@link
   * Difficulty}.
   *
   * @param type the {@link Type}
   * @param difficulty the {@link Difficulty}
   * @return the {@link Collection} of puzzle file names
   * @throws FileHandlingException if the puzzle file names could not be listed
   */
  public static Collection<String> listPuzzleFileNames(Type type, Difficulty difficulty)
      throws FileHandlingException {
    return listPuzzleFileNames(USER_PUZZLE_DIRECTORY, type, difficulty);
  }

  /**
   * Returns a {@link Collection} of puzzle file names given the puzzle directory, {@link Type} and
   * {@link Difficulty}.
   *
   * @param puzzleDirectory the directory where the puzzles are stored
   * @param type the {@link Type}
   * @param difficulty the {@link Difficulty}
   * @return the {@link Collection} of puzzle file names
   * @throws FileHandlingException if the puzzle file names could not be listed
   */
  static Collection<String> listPuzzleFileNames(String puzzleDirectory, Type type,
      Difficulty difficulty)
      throws FileHandlingException {
    if (type == null || difficulty == null) {
      return new ArrayList<>();
    }

    Path puzzleSubDirectoryPath = getPathToPuzzleSubDirectory(puzzleDirectory, type, difficulty);
    List<String> validPuzzleFileNames = getValidPuzzleFileNames(puzzleSubDirectoryPath);
    sortPuzzleFileNames(validPuzzleFileNames);

    return validPuzzleFileNames;
  }

  private static Path getPathToPuzzleSubDirectory(String puzzleDirectory, Type type,
      Difficulty difficulty) {
    return get(puzzleDirectory, type.toString(), difficulty.toString());
  }

  private static List<String> getValidPuzzleFileNames(Path puzzleSubDirectoryPath)
      throws FileHandlingException {
    File[] files = listFiles(puzzleSubDirectoryPath);

    List<String> puzzleFileNames = new ArrayList<>();

    if (files == null) {
      return puzzleFileNames;
    }

    for (File file : files) {
      String fileName = getFileNameWithoutExtension(file);
      if (isValidFileName(fileName)) {
        puzzleFileNames.add(fileName);
      }
    }

    return puzzleFileNames;
  }

  private static File[] listFiles(Path puzzleSubDirectoryPath) throws FileHandlingException {
    try {
      return puzzleSubDirectoryPath.toFile().listFiles();
    } catch (UnsupportedOperationException | SecurityException e) {
      throw new FileHandlingException("Could not list puzzles in '" + puzzleSubDirectoryPath + "'");
    }
  }

  private static String getFileNameWithoutExtension(File file) {
    return getFileNameWithoutExtension(file.getName());
  }

  private static String getFileNameWithoutExtension(String fileName) {
    return fileName.replace(EXTENSION, "");
  }

  private static boolean isValidFileName(String fileName) {
    try {
      getIntegerFromFileName(fileName);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  private static int getIntegerFromFileName(String fileName) {
    return parseInt(fileName.replaceAll("[^\\d]", ""));
  }

  private static void sortPuzzleFileNames(List<String> puzzleFileNames) {
    puzzleFileNames.sort((s1, s2) -> {
      int number1 = getIntegerFromFileName(s1);
      int number2 = getIntegerFromFileName(s2);
      return number1 - number2;
    });
  }

  /**
   * Reads a {@link Puzzle} from a file given the {@link Puzzle}'s {@link Type}, {@link Difficulty}
   * and name.
   *
   * @param type the {@link Type}
   * @param difficulty the {@link Difficulty}
   * @param name the name of the {@link Puzzle}
   * @return the {@link Puzzle}
   * @throws FileHandlingException if a {@link Puzzle} could not be read from the file
   */
  public static Puzzle read(Type type, Difficulty difficulty, String name)
      throws FileHandlingException {
    return read(USER_PUZZLE_DIRECTORY, type, difficulty, name);
  }

  /**
   * Reads a {@link Puzzle} from a file given the puzzle directory, the {@link Puzzle}'s {@link
   * Type}, {@link Difficulty} and name.
   *
   * @param puzzleDirectory the directory where the puzzles are stored
   * @param type the {@link Type}
   * @param difficulty the {@link Difficulty}
   * @param name the name of the {@link Puzzle}
   * @return the {@link Puzzle}
   * @throws FileHandlingException if a {@link Puzzle} could not be read from the file
   */
  static Puzzle read(String puzzleDirectory, Type type, Difficulty difficulty, String name)
      throws FileHandlingException {
    Path pathToFileToReadFrom = getPathToFileToReadPuzzleFrom(puzzleDirectory, type, difficulty,
        name);
    try (Scanner scanner = new Scanner(pathToFileToReadFrom)) {
      Grid grid = readGrid(scanner, type);
      return createPuzzle(type, grid);
    } catch (IOException e) {
      throw new FileHandlingException("Could not read '" + pathToFileToReadFrom + "'");
    }
  }

  private static Path getPathToFileToReadPuzzleFrom(String puzzleDirectory, Type type,
      Difficulty difficulty,
      String name) {
    return get(getPathToPuzzleSubDirectory(puzzleDirectory, type, difficulty).toString(),
        name + EXTENSION);
  }

  private static Grid readGrid(Scanner scanner, Type type) throws FileHandlingException {
    boolean isStrimko = type == STRIMKO;
    int size = (isStrimko) ? readNextInt(scanner) : SUDOKU_SIZE;
    int[][] streams = (isStrimko) ? readArray(scanner, size) : getSudokuStreams();
    int[][] numbers = readArray(scanner, size);
    return buildGrid(size, streams, numbers);
  }

  private static int readNextInt(Scanner scanner) throws FileHandlingException {
    try {
      return scanner.nextInt();
    } catch (InputMismatchException e) {
      throw new FileHandlingException("File contains an invalid character");
    } catch (NoSuchElementException e) {
      throw new FileHandlingException("File does not contain enough data to describe a puzzle");
    }
  }

  private static int[][] readArray(Scanner scanner, int size) throws FileHandlingException {
    int[][] array = new int[size][size];

    for (int rowIndex = 0; rowIndex < size; rowIndex++) {
      for (int columnIndex = 0; columnIndex < size; columnIndex++) {
        array[rowIndex][columnIndex] = readNextInt(scanner);
      }
    }

    return array;
  }

  private static Grid buildGrid(int size, int[][] streams, int[][] numbers)
      throws FileHandlingException {
    try {
      return new GridBuilder(size).setStreams(streams).setNumbers(numbers).build();
    } catch (IllegalArgumentException e) {
      throw new FileHandlingException(
          "File contains invalid puzzle information: " + e.getMessage());
    }
  }

  private static Puzzle createPuzzle(Type type, Grid grid) throws FileHandlingException {
    try {
      return new Puzzle(type, grid);
    } catch (IllegalArgumentException e) {
      throw new FileHandlingException("Puzzle could not be read from file: " + e.getMessage());
    }
  }

  /**
   * Writes a {@link Puzzle} to file.
   *
   * @param puzzle the {@link Puzzle} to write
   * @return the name of the {@link Puzzle} written to file
   * @throws FileHandlingException if the {@link Puzzle} could not be written to file
   */
  public static String write(Puzzle puzzle) throws FileHandlingException {
    return write(USER_PUZZLE_DIRECTORY, puzzle);
  }

  /**
   * Writes a {@link Puzzle} to file given the puzzle directory.
   *
   * @param puzzleDirectory the directory where the puzzles are stored
   * @param puzzle the {@link Puzzle} to write
   * @return the name of the {@link Puzzle} written to file
   * @throws FileHandlingException if the {@link Puzzle} could not be written to file
   */
  static String write(String puzzleDirectory, Puzzle puzzle) throws FileHandlingException {
    Type type = puzzle.getType();
    Difficulty difficulty = puzzle.getDifficulty();

    Path pathToPuzzleSubDirectory = getPathToPuzzleSubDirectory(puzzleDirectory, type, difficulty);
    makeDirectory(pathToPuzzleSubDirectory);

    String fileName = getNextAvailableFileName(pathToPuzzleSubDirectory, type);
    Path pathToFileToWriteTo = getPathToFileToWriteTo(pathToPuzzleSubDirectory, fileName);
    writePuzzle(puzzle, pathToFileToWriteTo);

    return fileName;
  }

  private static String getNextAvailableFileName(Path pathToPuzzleSubDirectory, Type type)
      throws FileHandlingException {
    Collection<String> puzzleFileNames = getValidPuzzleFileNames(pathToPuzzleSubDirectory);

    int fileNumber = 1;
    while (true) {
      String fileName = type + "-" + fileNumber;
      if (!puzzleFileNames.contains(fileName)) {
        return fileName;
      }
      fileNumber++;
    }
  }

  private static Path getPathToFileToWriteTo(Path pathToPuzzleSubDirectory, String fileName) {
    return get(pathToPuzzleSubDirectory.toString(), fileName + EXTENSION);
  }

  private static void writePuzzle(Puzzle puzzle, Path pathToFileToWriteTo)
      throws FileHandlingException {
    String puzzleString = getPuzzleString(puzzle);
    try {
      Files.write(pathToFileToWriteTo, puzzleString.trim().concat("\n").getBytes());
    } catch (IOException | UnsupportedOperationException | SecurityException e) {
      throw new FileHandlingException("Could not write puzzle to '" + pathToFileToWriteTo + "'");
    }
  }

  private static String getPuzzleString(Puzzle puzzle) {
    StringBuilder builder = new StringBuilder();
    Type type = puzzle.getType();
    Grid grid = puzzle.getGrid();
    if (type == Type.STRIMKO) {
      builder.append(puzzle.getGrid().getSize()).append(" ");
      appendStreams(builder, grid);
    }
    appendNumbers(builder, grid);
    return builder.toString();
  }

  private static void appendStreams(StringBuilder builder, Grid grid) {
    for (Cell cell : grid.getCells()) {
      builder.append(cell.getStreamIndex()).append(" ");
    }
  }

  private static void appendNumbers(StringBuilder builder, Grid grid) {
    for (Cell cell : grid.getCells()) {
      builder.append(cell.getNumber()).append(" ");
    }
  }

}
