package lowe.mike.strimko.controller;

import static javafx.application.Platform.runLater;
import static javafx.beans.binding.Bindings.when;
import static lowe.mike.strimko.controller.Alerts.showMessageAndWait;
import static lowe.mike.strimko.model.FileHandler.listPuzzleFileNames;
import static lowe.mike.strimko.model.FileHandler.read;

import java.util.Collection;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableIntegerValue;
import javafx.beans.value.ObservableStringValue;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener.Change;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import lowe.mike.strimko.model.Cell;
import lowe.mike.strimko.model.Difficulty;
import lowe.mike.strimko.model.FileHandlingException;
import lowe.mike.strimko.model.Grid;
import lowe.mike.strimko.model.Puzzle;

/**
 * Controller class for Play Mode View.
 *
 * @author Mike Lowe
 */
public final class PlayModeViewController extends ModeViewController {

  @FXML
  private ComboBox<Difficulty> difficultyComboBox;
  @FXML
  private ComboBox<String> nameComboBox;
  @FXML
  private Button loadButton;
  @FXML
  private ToggleButton solveCellButton;
  @FXML
  private ToggleButton pencilMarksButton;
  @FXML
  private ToggleButton hintButton;

  private static final String PENCIL_MARK_LABEL_STYLE_CLASS = "pencil-mark-label";
  private static final String PENCIL_MARKS_PANE_STYLE_CLASS = "pencil-marks-pane";
  private static final int NUMBER_OF_PENCIL_MARK_ROWS = 3;
  private static final int NUMBER_OF_PENCIL_MARK_COLUMNS = 3;
  private static final Effect GLOW = new Glow(0.7);
  private static final Effect NO_GLOW = null;

  @Override
  protected void initialize() {
    initializeDifficultyComboBox();
    initializeLoadButton();
    initializeGridPane();
    initializeControlsPane();
  }

  private void initializeDifficultyComboBox() {
    difficultyComboBox.getItems().addAll(Difficulty.values());
  }

  private void initializeLoadButton() {
    disableWhenNameNotSelected(loadButton);
  }

  private void disableWhenNameNotSelected(Node node) {
    node.disableProperty().bind(nameNotSelected());
  }

  private ObservableBooleanValue nameNotSelected() {
    return nameComboBox.valueProperty().isNull();
  }

  private void initializeGridPane() {
    hideWhenNoPuzzleIsSelected(gridPane);
  }

  private void hideWhenNoPuzzleIsSelected(Node node) {
    node.visibleProperty().bind(puzzleNotNull());
  }

  private ObservableBooleanValue puzzleNotNull() {
    return gameState.puzzleProperty().isNotNull();
  }

  private void initializeControlsPane() {
    hideWhenNoPuzzleIsSelected(controlsPane);
  }

  @FXML
  private void typeComboBoxAction() {
    refreshPuzzleNames();
  }

  @FXML
  private void difficultyComboBoxAction() {
    refreshPuzzleNames();
  }

  private void refreshPuzzleNames() {
    clearNameComboBoxSelection();
    Task<Collection<String>> task = getListPuzzleFileNamesTask();
    runTaskInThread(task);
  }

  private void clearNameComboBoxSelection() {
    nameComboBox.getSelectionModel().clearSelection();
  }

  private Task<Collection<String>> getListPuzzleFileNamesTask() {
    Task<Collection<String>> task = new Task<Collection<String>>() {

      @Override
      protected Collection<String> call() throws FileHandlingException {
        return listPuzzleFileNames(getTypeComboBoxValue(), getDifficultyComboBoxValue());
      }

    };

    setNameComboBoxValuesIfSuccessful(task);
    showErrorAndWaitIfTaskUnsuccessful(task);

    return task;
  }

  private Difficulty getDifficultyComboBoxValue() {
    return difficultyComboBox.getValue();
  }

  private void setNameComboBoxValuesIfSuccessful(Task<Collection<String>> task) {
    task.setOnSucceeded((event) -> nameComboBox.getItems().setAll(task.getValue()));
  }

  @FXML
  private void refreshButtonAction() {
    refreshPuzzleNames();
  }

  @FXML
  private void loadButtonAction() {
    Task<Puzzle> task = getReadPuzzleTask();
    runTaskInThread(task);
  }

  private Task<Puzzle> getReadPuzzleTask() {
    Task<Puzzle> task = new Task<Puzzle>() {

      @Override
      protected Puzzle call() throws FileHandlingException {
        return read(getTypeComboBoxValue(), getDifficultyComboBoxValue(), getNameComboBoxValue());
      }

    };

    updateStateAndViewIfSuccessful(task);
    showErrorAndWaitIfTaskUnsuccessful(task);

    return task;
  }

  private String getNameComboBoxValue() {
    return nameComboBox.getValue();
  }

  private void updateStateAndViewIfSuccessful(Task<Puzzle> task) {
    task.setOnSucceeded((event) -> {
      gameState.setPuzzle(task.getValue());
      updateGridPane();
      updateControls();
      checkIsSolved();
      addSolvedPropertyChangeListener();
    });
  }

  private void updateGridPane() {
    resetGridPane();

    Grid grid = gameState.getPuzzle().getGrid();
    int size = grid.getSize();

    for (Cell cell : grid.getCells()) {
      StackPane cellPane = newCellPane(size, cell.getStreamIndex());
      addCellPaneChildren(cellPane, cell);
      addCellPaneToGridPane(cellPane, cell.getColumnIndex(), cell.getRowIndex());
    }
  }

  private void addCellPaneChildren(StackPane cellPane, Cell cell) {
    ObservableStringValue textProperty = getNumberLabelTextProperty(cell);
    ObservableBooleanValue visibilityProperty = getNumberLabelVisibilityProperty();
    addNumberLabelToCellPane(cellPane, textProperty, visibilityProperty);
    addSolutionLabel(cellPane, cell);
    addPencilMarksPane(cellPane, cell);
    addHintHighlighting(cellPane, cell);
    addCellPaneClickEvent(cellPane, cell);
  }

  private static ObservableStringValue getNumberLabelTextProperty(Cell cell) {
    ObservableBooleanValue cellIsSet = cell.setProperty();
    ObservableStringValue number = cell.numberProperty().asString();
    ObservableStringValue noNumber = new SimpleStringProperty();
    return when(cellIsSet).then(number).otherwise(noNumber);
  }

  private ObservableBooleanValue getNumberLabelVisibilityProperty() {
    return solutionButton.selectedProperty().not();
  }

  private void addSolutionLabel(StackPane cellPane, Cell cell) {
    runLater(() -> {
      int number = gameState.getPuzzle().getSolutionForCell(cell);
      ObservableBooleanValue visibilityProperty = getSolutionLabelVisibilityProperty();
      addSolutionLabelToCellPane(cellPane, number, visibilityProperty);
    });
  }

  private ObservableBooleanValue getSolutionLabelVisibilityProperty() {
    return solutionButton.selectedProperty();
  }

  private void addPencilMarksPane(StackPane cellPane, Cell cell) {
    runLater(() -> {
      GridPane pencilMarksPane = new GridPane();

      int size = gameState.getPuzzle().getGrid().getSize();
      double width = getPencilMarkLabelWidth(cellPane.getPrefWidth());
      double height = getPencilMarkLabelHeight(cellPane.getPrefHeight());

      for (int number = 1; number <= size; number++) {
        addPencilMarkLabel(pencilMarksPane, cell, number, width, height);
      }

      setPencilMarksPaneVisibility(pencilMarksPane);
      addPencilMarksPaneStyle(pencilMarksPane, width, height);

      cellPane.getChildren().add(pencilMarksPane);
    });
  }

  private static double getPencilMarkLabelWidth(double width) {
    return width / NUMBER_OF_PENCIL_MARK_ROWS;
  }

  private static double getPencilMarkLabelHeight(double height) {
    return height / NUMBER_OF_PENCIL_MARK_COLUMNS;
  }

  private static void addPencilMarkLabel(GridPane pencilMarksPane, Cell cell, int number,
      double width,
      double height) {
    Label pencilMarkLabel = new Label(Integer.toString(number));

    ObservableSet<Integer> possibleNumbers = cell.getPossibleNumbers();

    setPencilMarkLabelVisibility(pencilMarkLabel, possibleNumbers, number);
    addPossibleNumbersChangeListener(pencilMarkLabel, possibleNumbers, number);
    addPencilMarkLabelStyle(pencilMarkLabel, width, height);

    int columnIndex = getPencilMarkLabelColumnIndex(number);
    int rowIndex = getPencilMarkLabelRowIndex(number);
    pencilMarksPane.add(pencilMarkLabel, columnIndex, rowIndex);
  }

  private static void setPencilMarkLabelVisibility(Label pencilMarkLabel,
      ObservableSet<Integer> possibleNumbers,
      int number) {
    boolean containsNumber = possibleNumbers.contains(number);
    pencilMarkLabel.setVisible(containsNumber);
  }

  private static void addPossibleNumbersChangeListener(Label pencilMarkLabel,
      ObservableSet<Integer> possibleNumbers,
      int number) {
    possibleNumbers.addListener(
        (Change<? extends Integer> change) -> setPencilMarkLabelVisibility(pencilMarkLabel,
            possibleNumbers, number));
  }

  private static void addPencilMarkLabelStyle(Label pencilMarkLabel, double width, double height) {
    pencilMarkLabel.setPrefSize(width, height);
    pencilMarkLabel.getStyleClass().add(PENCIL_MARK_LABEL_STYLE_CLASS);
  }

  private static int getPencilMarkLabelColumnIndex(int number) {
    return (number - 1) % NUMBER_OF_PENCIL_MARK_COLUMNS;
  }

  private static int getPencilMarkLabelRowIndex(int number) {
    return (number - 1) / NUMBER_OF_PENCIL_MARK_COLUMNS;
  }

  private void setPencilMarksPaneVisibility(GridPane pencilMarksPane) {
    pencilMarksPane.visibleProperty().bind(showingPencilMarks());
  }

  private ObservableBooleanValue showingPencilMarks() {
    return pencilMarksButton.selectedProperty();
  }

  private static void addPencilMarksPaneStyle(GridPane pencilMarksPane, double width,
      double height) {
    pencilMarksPane.setPrefSize(width, height);
    pencilMarksPane.getStyleClass().add(PENCIL_MARKS_PANE_STYLE_CLASS);
  }

  private void addHintHighlighting(StackPane cellPane, Cell cell) {
    runLater(() -> cellPane.effectProperty()
        .bind(when(nextHint().isEqualTo(cell).and(showingHint())).then(GLOW).otherwise(NO_GLOW)));
  }

  private ReadOnlyObjectProperty<Cell> nextHint() {
    return gameState.getPuzzle().nextHintProperty();
  }

  private ObservableBooleanValue showingHint() {
    return hintButton.selectedProperty();
  }

  private void addCellPaneClickEvent(StackPane cellPane, Cell cell) {
    cellPane.setOnMouseClicked(event -> {
      ToggleButton selectedNumberButton = getSelectedToggleButtonFromPane(numbersPane);
      if (selectedNumberButton != null) {
        int number = getNumberFromToggleButton(selectedNumberButton);
        cell.setNumber(number);
      } else if (solveCell()) {
        int number = gameState.getPuzzle().getSolutionForCell(cell);
        cell.setNumber(number);
      } else if (clearNumber()) {
        cell.clearNumber();
      }
    });
  }

  private boolean solveCell() {
    return solveCellButton.isSelected();
  }

  private void updateControls() {
    deselectToggleInToggleGroup(controlsToggleGroup);
    deselectToggles(pencilMarksButton, hintButton);
    enableNodes(numbersPane, clearNumberButton, solveCellButton, pencilMarksButton, hintButton,
        solutionButton);
    removeChildrenFromToggleGroup(controlsToggleGroup, numbersPane);
    clearChildren(numbersPane);
    addNumberButtons();
    addNextHintPropertyChangeListener();
  }

  private void addNumberButtons() {
    Grid grid = gameState.getPuzzle().getGrid();
    int size = grid.getSize();

    for (int number = 1; number <= size; number++) {
      addNumberButton(grid, size, number);
    }
  }

  private void addNumberButton(Grid grid, int size, int number) {
    ObservableIntegerValue occurrenceProperty = grid.numberOccurrenceProperty(number);
    ToggleButton numberButton = newNumberedToggleButton(size, number, controlsToggleGroup,
        occurrenceProperty);
    numbersPane.addColumn(number - 1, numberButton);
  }

  private void addNextHintPropertyChangeListener() {
    nextHint().addListener((observable) -> deselectToggles(hintButton));
  }

  private void addSolvedPropertyChangeListener() {
    isSolved().addListener((observable) -> checkIsSolved());
  }

  private ObservableBooleanValue isSolved() {
    return gameState.getPuzzle().getGrid().isSolvedProperty();
  }

  private void checkIsSolved() {
    runLater(() -> {

      Node[] nodes = {numbersPane, solveCellButton, pencilMarksButton, hintButton, solutionButton};

      if (gameState.getPuzzle().getGrid().isSolved()) {
        deselectToggleInToggleGroup(controlsToggleGroup);
        deselectToggles(pencilMarksButton, hintButton);
        disableNodes(nodes);
        showMessageAndWait("Congratulations! You have solved the puzzle!");
      } else {
        enableNodes(nodes);
      }

    });
  }

  @FXML
  private void hintButtonAction() {
    if (showHint() && noMoreHints()) {
      deselectToggles(hintButton);
      showMessageAndWait("There are no more hints available");
    }
  }

  private boolean showHint() {
    return hintButton.isSelected();
  }

  private boolean noMoreHints() {
    return gameState.getPuzzle().getNextHint() == null;
  }

  @FXML
  private void solutionButtonAction() {
    Node[] nodes = {numbersPane, clearNumberButton, solveCellButton, pencilMarksButton, hintButton};

    if (showSolution()) {
      deselectToggles(pencilMarksButton, hintButton);
      disableNodes(nodes);
    } else {
      enableNodes(nodes);
    }
  }

  @FXML
  private void restartButtonAction() {
    gameState.getPuzzle().getGrid().reset();
    deselectToggleInToggleGroup(controlsToggleGroup);
    deselectToggles(pencilMarksButton, hintButton);
    enableNodes(numbersPane, clearNumberButton, solveCellButton, pencilMarksButton, hintButton,
        solutionButton);
    checkIsSolved();
  }

}
