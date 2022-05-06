package lowe.mike.strimko.controller;

import static javafx.beans.binding.Bindings.when;
import static javafx.scene.layout.GridPane.getColumnIndex;
import static javafx.scene.layout.GridPane.getRowIndex;
import static lowe.mike.strimko.controller.Alerts.showMessageAndWait;
import static lowe.mike.strimko.controller.Alerts.showWarningAndWait;
import static lowe.mike.strimko.model.Constants.MIN_STRIMKO_SIZE;
import static lowe.mike.strimko.model.Constants.NO_NUMBER;
import static lowe.mike.strimko.model.Constants.SUDOKU_SIZE;
import static lowe.mike.strimko.model.Constants.getStrimkoPuzzleSizes;
import static lowe.mike.strimko.model.Constants.getSudokuStreams;
import static lowe.mike.strimko.model.FileHandler.write;
import static lowe.mike.strimko.model.Type.STRIMKO;
import static lowe.mike.strimko.model.Type.SUDOKU;

import java.util.concurrent.ExecutionException;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableIntegerValue;
import javafx.beans.value.ObservableStringValue;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import lowe.mike.strimko.model.Cell;
import lowe.mike.strimko.model.FileHandlingException;
import lowe.mike.strimko.model.Grid;
import lowe.mike.strimko.model.Grid.GridBuilder;
import lowe.mike.strimko.model.Puzzle;

/**
 * Controller class for Solve Mode View.
 *
 * @author Mike Lowe
 */
public final class SolveModeViewController extends ModeViewController {

  @FXML
  private ComboBox<Integer> sizeComboBox;
  @FXML
  private RadioButton streamsRadioButton;
  @FXML
  private RadioButton numbersRadioButton;
  @FXML
  private GridPane streamsPane;
  @FXML
  private ToggleButton clearStreamButton;
  @FXML
  private Button saveButton;

  private static final String STREAM_BUTTON_STYLE_CLASS = "stream-button";

  @Override
  protected void initialize() {
    initializeSizeComboBox();
    initializeGridPane();
    initializeControlsPane();
  }

  private void initializeSizeComboBox() {
    disableWhenStrimkoNotSelected(sizeComboBox);
  }

  private void disableWhenStrimkoNotSelected(Node node) {
    node.disableProperty().bind(strimkoNotSelected());
  }

  private ObservableBooleanValue strimkoNotSelected() {
    return typeComboBox.valueProperty().isNotEqualTo(STRIMKO);
  }

  private void initializeGridPane() {
    hideWhenNoGridBuilderIsSelected(gridPane);
  }

  private void hideWhenNoGridBuilderIsSelected(Node node) {
    node.visibleProperty().bind(gridBuilderNotNull());
  }

  private ObservableBooleanValue gridBuilderNotNull() {
    return gameState.gridBuilderProperty().isNotNull();
  }

  private void initializeControlsPane() {
    hideWhenNoGridBuilderIsSelected(controlsPane);
    initializeStreamsPane();
    initializeNumbersPane();
  }

  private void initializeStreamsPane() {
    streamsPane.visibleProperty().bind(streamsRadioButtonSelected());
  }

  private ObservableBooleanValue streamsRadioButtonSelected() {
    return streamsRadioButton.selectedProperty();
  }

  private void initializeNumbersPane() {
    numbersPane.visibleProperty().bind(numbersRadioButtonSelected());
  }

  private ObservableBooleanValue numbersRadioButtonSelected() {
    return numbersRadioButton.selectedProperty();
  }

  @FXML
  private void typeComboBoxAction() {
    refreshSizes();
  }

  private void refreshSizes() {
    clearSizeComboBoxSelection();
    clearSizeComboBox();
    setSizeComboBox();
  }

  private void clearSizeComboBoxSelection() {
    sizeComboBox.getSelectionModel().clearSelection();
  }

  private void clearSizeComboBox() {
    sizeComboBox.getItems().clear();
  }

  private void setSizeComboBox() {
    if (getTypeComboBoxValue() == STRIMKO) {
      sizeComboBox.getItems().setAll(getStrimkoPuzzleSizes());
      sizeComboBox.setValue(MIN_STRIMKO_SIZE);
    } else {
      sizeComboBox.getItems().setAll(SUDOKU_SIZE);
      sizeComboBox.setValue(SUDOKU_SIZE);
    }
  }

  @FXML
  private void sizeComboBoxAction() {
    if (getSizeComboBoxValue() != null) {
      updateStateAndView();
    }
  }

  private Integer getSizeComboBoxValue() {
    return sizeComboBox.getValue();
  }

  private void updateStateAndView() {
    updateGridBuilder();
    updateGridPane();
    updateControls();
  }

  private void updateGridBuilder() {
    GridBuilder gridBuilder = new GridBuilder(getSizeComboBoxValue());
    if (getTypeComboBoxValue() == SUDOKU) {
      gridBuilder.setStreams(getSudokuStreams());
    }
    gameState.setGridBuilder(gridBuilder);
  }

  private void updateGridPane() {
    resetGridPane();

    GridBuilder gridBuilder = gameState.getGridBuilder();
    int size = gridBuilder.getSize();

    for (int rowIndex = 0; rowIndex < size; rowIndex++) {
      for (int columnIndex = 0; columnIndex < size; columnIndex++) {
        int streamIndex = gridBuilder.getStreamIndex(rowIndex, columnIndex);
        StackPane cellPane = newCellPane(size, streamIndex);
        addCellPaneChildren(cellPane, columnIndex, rowIndex);
        addCellPaneToGridPane(cellPane, columnIndex, rowIndex);
      }
    }
  }

  private void addCellPaneChildren(StackPane cellPane, int columnIndex, int rowIndex) {
    ObservableStringValue textProperty = getNumberLabelTextProperty(columnIndex, rowIndex);
    ObservableBooleanValue visibilityProperty = new SimpleBooleanProperty(true);
    addNumberLabelToCellPane(cellPane, textProperty, visibilityProperty);
    addStreamIndexPropertyChangeListener(cellPane, columnIndex, rowIndex);
    addCellPaneClickEvent(cellPane, columnIndex, rowIndex);
  }

  private ObservableStringValue getNumberLabelTextProperty(int columnIndex, int rowIndex) {
    ReadOnlyIntegerProperty numberProperty = gameState.getGridBuilder()
        .numberProperty(rowIndex, columnIndex);
    ObservableBooleanValue cellIsSet = numberProperty.greaterThan(NO_NUMBER);
    ObservableStringValue number = numberProperty.asString();
    ObservableStringValue noNumber = new SimpleStringProperty();
    return when(cellIsSet).then(number).otherwise(noNumber);
  }

  private void addStreamIndexPropertyChangeListener(StackPane cellPane, int columnIndex,
      int rowIndex) {
    ObservableIntegerValue streamIndexProperty = gameState.getGridBuilder()
        .streamIndexProperty(rowIndex,
            columnIndex);
    streamIndexProperty.addListener((observable, oldValue, newValue) -> {
      removeStreamStyleClass(cellPane, (int) oldValue);
      addStreamStyleClass(cellPane, (int) newValue);
    });
  }

  private void addCellPaneClickEvent(StackPane cellPane, int columnIndex, int rowIndex) {
    cellPane.setOnMouseClicked(event -> {
      GridBuilder gridBuilder = gameState.getGridBuilder();
      ToggleButton selectedStreamButton = getSelectedToggleButtonFromPane(streamsPane);
      ToggleButton selectedNumberButton = getSelectedToggleButtonFromPane(numbersPane);
      if (selectedStreamButton != null) {
        int streamIndex = getNumberFromToggleButton(selectedStreamButton);
        gridBuilder.setStreamIndex(rowIndex, columnIndex, streamIndex);
      } else if (selectedNumberButton != null) {
        int number = getNumberFromToggleButton(selectedNumberButton);
        gridBuilder.setNumber(rowIndex, columnIndex, number);
      } else if (clearStream()) {
        gridBuilder.clearStreamIndex(rowIndex, columnIndex);
      } else if (clearNumber()) {
        gridBuilder.clearNumber(rowIndex, columnIndex);
      }
    });
  }

  private boolean clearStream() {
    return clearStreamButton.isSelected();
  }

  private void updateControls() {
    deselectToggleInToggleGroup(controlsToggleGroup);
    enableNodes(numbersRadioButton, numbersPane, clearNumberButton);
    removeChildrenFromToggleGroup(controlsToggleGroup, streamsPane, numbersPane);
    clearChildren(streamsPane, numbersPane);
    addStreamButtons();
    addNumberButtons();
    updateStreamControls();
  }

  private void addStreamButtons() {
    GridBuilder gridBuilder = gameState.getGridBuilder();
    int size = gridBuilder.getSize();

    for (int streamIndex = 1; streamIndex <= size; streamIndex++) {
      addStreamButton(gridBuilder, size, streamIndex);
    }
  }

  private void addStreamButton(GridBuilder gridBuilder, int size, int streamIndex) {
    ObservableIntegerValue occurrenceProperty = gridBuilder
        .streamIndexOccurrenceProperty(streamIndex);
    ToggleButton streamButton = newNumberedToggleButton(size, streamIndex, controlsToggleGroup,
        occurrenceProperty);
    addStreamButtonStyle(streamButton, streamIndex);
    streamsPane.addColumn(streamIndex - 1, streamButton);
  }

  private static void addStreamButtonStyle(ToggleButton streamButton, int streamIndex) {
    addStreamStyleClass(streamButton, streamIndex);
    streamButton.getStyleClass().add(STREAM_BUTTON_STYLE_CLASS);
  }

  private void addNumberButtons() {
    GridBuilder gridBuilder = gameState.getGridBuilder();
    int size = gridBuilder.getSize();

    for (int number = 1; number <= size; number++) {
      addNumberButton(gridBuilder, size, number);
    }
  }

  private void addNumberButton(GridBuilder gridBuilder, int size, int number) {
    ObservableIntegerValue occurrenceProperty = gridBuilder.numberOccurrenceProperty(number);
    ToggleButton numberButton = newNumberedToggleButton(size, number, controlsToggleGroup,
        occurrenceProperty);
    numbersPane.addColumn(number - 1, numberButton);
  }

  private void updateStreamControls() {
    Node[] nodes = {streamsRadioButton, streamsPane, clearStreamButton};

    if (getTypeComboBoxValue() == STRIMKO) {
      enableNodes(nodes);
      streamsRadioButton.setSelected(true);
    } else {
      disableNodes(nodes);
      numbersRadioButton.setSelected(true);
    }
  }

  @FXML
  private void solutionButtonAction() {
    Node[] nodes = {streamsPane, numbersPane, streamsRadioButton, numbersRadioButton,
        clearStreamButton,
        clearNumberButton};

    if (showSolution()) {
      Puzzle puzzle = createPuzzle();
      if (puzzle == null) {
        deselectToggles(solutionButton);
      } else {
        addSolutionLabels(puzzle);
        disableNodes(nodes);
      }
    } else {
      removeSolutionLabels();
      enableNodes(nodes);
    }
  }

  private Puzzle createPuzzle() {
    Task<Puzzle> task = getCreatePuzzleTask();
    runTaskInThread(task);

    try {
      return task.get();
    } catch (InterruptedException | ExecutionException e) {
      showWarningAndWait(e.getCause());
      return null;
    }
  }

  private Task<Puzzle> getCreatePuzzleTask() {
    return new Task<Puzzle>() {

      @Override
      protected Puzzle call() {
        Grid grid = gameState.getGridBuilder().build();
        return new Puzzle(getTypeComboBoxValue(), grid);
      }

    };
  }

  private void addSolutionLabels(Puzzle puzzle) {
    for (Node node : gridPane.getChildren()) {
      StackPane cellPane = (StackPane) node;
      int number = getSolutionNumber(puzzle, cellPane);
      ObservableBooleanValue visibilityProperty = new SimpleBooleanProperty(true);
      addSolutionLabelToCellPane(cellPane, number, visibilityProperty);
    }
  }

  private static int getSolutionNumber(Puzzle puzzle, StackPane cellPane) {
    int rowIndex = getRowIndex(cellPane);
    int columnIndex = getColumnIndex(cellPane);
    Cell cell = puzzle.getGrid().getCell(rowIndex, columnIndex);
    return puzzle.getSolutionForCell(cell);
  }

  private void removeSolutionLabels() {
    for (Node node : gridPane.getChildren()) {
      StackPane cellPane = (StackPane) node;
      removeSolutionLabelFromCellPane(cellPane);
    }
  }

  @FXML
  private void saveButtonAction() {
    Puzzle puzzle = createPuzzle();

    if (puzzle != null) {
      Task<String> task = getWritePuzzleTask(puzzle);
      runTaskInThread(task);
    }
  }

  private Task<String> getWritePuzzleTask(Puzzle puzzle) {
    Task<String> task = new Task<String>() {

      @Override
      protected String call() throws FileHandlingException {
        return write(puzzle);
      }

    };

    showMessageAndUpdateViewOnSuccess(task, puzzle);
    showErrorAndWaitIfTaskUnsuccessful(task);

    return task;
  }

  private void showMessageAndUpdateViewOnSuccess(Task<String> task, Puzzle puzzle) {
    task.setOnSucceeded((event) -> {
      resetViewAndState();
      String message = "Saved puzzle successfully!\nType: " + puzzle.getType() + "\nDifficulty: "
          + puzzle.getDifficulty() + "\nName: " + task.getValue();
      showMessageAndWait(message);
    });
  }

  @FXML
  private void restartButtonAction() {
    resetViewAndState();
  }

  private void resetViewAndState() {
    resetGridBuilder();
    removeSolutionLabels();
    deselectToggleInToggleGroup(controlsToggleGroup);
    enableNodes(numbersRadioButton, numbersPane, clearNumberButton);
    updateStreamControls();
  }

  private void resetGridBuilder() {
    if (getTypeComboBoxValue() == STRIMKO) {
      gameState.getGridBuilder().clearStreamIndexes();
    }
    gameState.getGridBuilder().clearNumbers();
  }

}
