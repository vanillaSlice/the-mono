package lowe.mike.strimko.controller;

import static java.lang.Double.MAX_VALUE;
import static java.lang.Integer.parseInt;
import static javafx.scene.layout.GridPane.setFillWidth;
import static javafx.scene.layout.GridPane.setHgrow;
import static javafx.scene.layout.Priority.SOMETIMES;
import static lowe.mike.strimko.controller.Alerts.showErrorAndWait;

import java.util.Iterator;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableIntegerValue;
import javafx.beans.value.ObservableStringValue;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import lowe.mike.strimko.model.GameState;
import lowe.mike.strimko.model.Type;

/**
 * Abstract Controller class for Mode Views.
 *
 * @author Mike Lowe
 */
public abstract class ModeViewController {

  protected GameState gameState;
  @FXML
  protected ComboBox<Type> typeComboBox;
  @FXML
  protected GridPane gridPane;
  @FXML
  protected GridPane controlsPane;
  @FXML
  protected ToggleGroup controlsToggleGroup;
  @FXML
  protected GridPane numbersPane;
  @FXML
  protected ToggleButton clearNumberButton;
  @FXML
  protected ToggleButton solutionButton;
  @FXML
  protected Button restartButton;

  private static final String CELL_PANE_STYLE_CLASS = "cell-pane";
  private static final String STREAM_STYLE_CLASS_PREFIX = "stream-";
  private static final String NUMBER_LABEL_STYLE_CLASS = "number-label";
  private static final String SOLUTION_LABEL_STYLE_CLASS = "solution-label";

  /**
   * Set the state of the controller.
   *
   * @param gameState the {@link GameState}
   */
  protected final void setGameState(GameState gameState) {
    this.gameState = gameState;
    addTypeComboBoxValues();
    initialize();
  }

  private void addTypeComboBoxValues() {
    typeComboBox.getItems().addAll(Type.values());
  }

  /**
   * Tasks to carry out to initialize the controller.
   */
  protected abstract void initialize();

  /**
   * Runs a given {@link Task} in a thread.
   *
   * @param task the {@link Task} to run
   */
  protected static void runTaskInThread(Task<?> task) {
    new Thread(task).run();
  }

  /**
   * Returns the selected {@link Type}.
   *
   * @return the selected {@link Type}
   */
  protected final Type getTypeComboBoxValue() {
    return typeComboBox.getValue();
  }

  /**
   * Shows an error and waits if the given {@link Task} fails.
   *
   * @param task the {@link Task} to add the handler to
   */
  protected static void showErrorAndWaitIfTaskUnsuccessful(Task<?> task) {
    task.setOnFailed((event) -> showErrorAndWait(task.getException()));
  }

  /**
   * Removes all children from the grid pane.
   */
  protected final void resetGridPane() {
    clearChildren(gridPane);
  }

  /**
   * Creates a new cell pane given the size of the grid and the stream index of the cell.
   *
   * @param size the size of the grid
   * @param streamIndex the stream index of the cell
   * @return the new cell pane
   */
  protected final StackPane newCellPane(int size, int streamIndex) {
    StackPane cellPane = new StackPane();
    double width = gridPane.getPrefWidth() / size;
    double height = gridPane.getPrefHeight() / size;
    cellPane.setPrefSize(width, height);
    cellPane.getStyleClass().add(CELL_PANE_STYLE_CLASS);
    addStreamStyleClass(cellPane, streamIndex);
    return cellPane;
  }

  /**
   * Adds the stream style class to the given {@link Node}.
   *
   * @param node the {@link Node} to add the style to
   * @param streamIndex the stream index
   */
  protected static void addStreamStyleClass(Node node, int streamIndex) {
    String streamStyleClass = getStreamStyleClass(streamIndex);
    node.getStyleClass().add(streamStyleClass);
  }

  /**
   * Removes the stream style class from the given {@link Node}.
   *
   * @param node the {@link Node} to remove the style class from
   * @param streamIndex the stream index
   */
  protected static void removeStreamStyleClass(Node node, int streamIndex) {
    String streamStyleClass = getStreamStyleClass(streamIndex);
    node.getStyleClass().remove(streamStyleClass);
  }

  private static String getStreamStyleClass(int streamIndex) {
    return STREAM_STYLE_CLASS_PREFIX + streamIndex;
  }

  /**
   * Adds a number {@link Label} to the given cell pane.
   *
   * @param cellPane the cell pane to add the number {@link Label} to
   * @param textProperty the {@link ObservableStringValue} text property
   * @param visibilityProperty the {@link ObservableBooleanValue} visibility property
   */
  protected static void addNumberLabelToCellPane(StackPane cellPane,
      ObservableStringValue textProperty,
      ObservableBooleanValue visibilityProperty) {
    Label numberLabel = new Label();

    numberLabel.textProperty().bind(textProperty);

    addLabelToCellPane(cellPane, numberLabel, visibilityProperty, NUMBER_LABEL_STYLE_CLASS);
  }

  private static void addLabelToCellPane(StackPane cellPane, Label label,
      ObservableBooleanValue visibilityProperty,
      String styleClass) {
    label.visibleProperty().bind(visibilityProperty);
    label.setPrefSize(cellPane.getPrefWidth(), cellPane.getPrefHeight());
    label.getStyleClass().add(styleClass);

    cellPane.getChildren().add(label);
  }

  /**
   * Adds a solution {@link Label} to the given cell pane.
   *
   * @param cellPane the cell pane to add the solution {@link Label} to
   * @param number the solution number
   * @param visibilityProperty the {@link ObservableBooleanValue} visibility property
   */
  protected static void addSolutionLabelToCellPane(StackPane cellPane, int number,
      ObservableBooleanValue visibilityProperty) {
    Label solutionLabel = new Label(Integer.toString(number));

    addLabelToCellPane(cellPane, solutionLabel, visibilityProperty, SOLUTION_LABEL_STYLE_CLASS);
  }

  /**
   * Removes a solution {@link Label} from the given cell pane.
   *
   * @param cellPane the cell pane to remove the solution {@link Label} from
   */
  protected static void removeSolutionLabelFromCellPane(StackPane cellPane) {
    Iterator<Node> iterator = cellPane.getChildren().iterator();
    while (iterator.hasNext()) {
      Label label = (Label) iterator.next();
      if (label.getStyleClass().contains(SOLUTION_LABEL_STYLE_CLASS)) {
        iterator.remove();
      }
    }
  }

  /**
   * Adds the cell pane to the grid pane at the given column index and row index.
   *
   * @param cellPane the cell pane to add
   * @param columnIndex the column index
   * @param rowIndex the row index
   */
  protected final void addCellPaneToGridPane(StackPane cellPane, int columnIndex, int rowIndex) {
    gridPane.add(cellPane, columnIndex, rowIndex);
  }

  /**
   * Returns the selected {@link ToggleButton} in a given {@link Pane}, {@code null} if one is not
   * selected.
   *
   * @param pane the {@link Pane}
   * @return the selected {@link ToggleButton}
   */
  protected static ToggleButton getSelectedToggleButtonFromPane(Pane pane) {
    for (Node node : pane.getChildren()) {
      ToggleButton button = (ToggleButton) node;
      if (button.isSelected()) {
        return button;
      }
    }
    return null;
  }

  /**
   * Returns the number on a given {@link ToggleButton}.
   *
   * @param button the {@link ToggleButton} to get the number from
   * @return the number on a given {@link ToggleButton}
   */
  protected static int getNumberFromToggleButton(ToggleButton button) {
    return parseInt(button.getText());
  }

  /**
   * Returns{@code true} if the clear number {@link ToggleButton} is selected; {@code false}
   * otherwise.
   *
   * @return {@code true} if the clear number {@link ToggleButton} is selected; {@code false}
   *     otherwise
   */
  protected final boolean clearNumber() {
    return clearNumberButton.isSelected();
  }

  /**
   * Deselects the selected {@link Toggle} in a {@link ToggleGroup}.
   *
   * @param toggleGroup the {@link ToggleGroup}
   */
  protected static void deselectToggleInToggleGroup(ToggleGroup toggleGroup) {
    toggleGroup.selectToggle(null);
  }

  /**
   * Deselects an array of {@link Toggle}s.
   *
   * @param toggles {@link Toggle}s to deselect
   */
  protected static void deselectToggles(Toggle... toggles) {
    for (Toggle toggle : toggles) {
      toggle.setSelected(false);
    }
  }

  /**
   * Enables an array of {@link Node}s.
   *
   * @param nodes {@link Node}s to enable
   */
  protected static void enableNodes(Node... nodes) {
    disableNodes(false, nodes);
  }

  /**
   * Disables an array of {@link Node}s.
   *
   * @param nodes {@link Node}s to disable
   */
  protected static void disableNodes(Node... nodes) {
    disableNodes(true, nodes);
  }

  private static void disableNodes(boolean disable, Node... nodes) {
    for (Node node : nodes) {
      node.setDisable(disable);
    }
  }

  /**
   * Removes each {@link Pane}'s children from a {@link ToggleGroup}.
   *
   * @param panes an array of {@link Pane}s
   * @param toggleGroup the {@link ToggleGroup}
   */
  protected static void removeChildrenFromToggleGroup(ToggleGroup toggleGroup, Pane... panes) {
    for (Pane pane : panes) {
      toggleGroup.getToggles().removeAll(pane.getChildren());
    }
  }

  /**
   * Clears all children from each {@link Pane} in an array.
   *
   * @param panes an array of {@link Pane}s
   */
  protected static void clearChildren(Pane... panes) {
    for (Pane pane : panes) {
      pane.getChildren().clear();
    }
  }

  /**
   * Returns a new numbered {@link ToggleButton}.
   *
   * @param size the size of the grid
   * @param number the number of the {@link ToggleButton}
   * @param toggleGroup the {@link ToggleGroup}
   * @param occurrenceProperty the number or stream occurrence property
   * @return a new numbered {@link ToggleButton}
   */
  protected static ToggleButton newNumberedToggleButton(int size, int number,
      ToggleGroup toggleGroup,
      ObservableIntegerValue occurrenceProperty) {
    ToggleButton numberedButton = new ToggleButton(Integer.toString(number));

    numberedButton.setToggleGroup(toggleGroup);

    setNumberedToggleButtonDisableProperty(numberedButton, size, occurrenceProperty);
    addOccurrencePropertyChangeListener(numberedButton, size, occurrenceProperty);
    addNumberedButtonStyle(numberedButton);

    return numberedButton;
  }

  private static void setNumberedToggleButtonDisableProperty(ToggleButton numberedButton, int size,
      ObservableIntegerValue occurrenceProperty) {
    if (occurrenceProperty.get() >= size) {
      numberedButton.setDisable(true);
      numberedButton.setSelected(false);
    } else {
      numberedButton.setDisable(false);
    }
  }

  private static void addOccurrencePropertyChangeListener(ToggleButton numberedButton, int size,
      ObservableIntegerValue occurrenceProperty) {
    occurrenceProperty.addListener(
        (observable) -> setNumberedToggleButtonDisableProperty(numberedButton, size,
            occurrenceProperty));
  }

  private static void addNumberedButtonStyle(ToggleButton numberedButton) {
    numberedButton.setMaxWidth(MAX_VALUE);
    numberedButton.setMaxHeight(MAX_VALUE);
    setHgrow(numberedButton, SOMETIMES);
    setFillWidth(numberedButton, true);
  }

  /**
   * Returns {@code true} if the solution {@link ToggleButton} is selected; {@code false}
   * otherwise.
   *
   * @return {@code true} if the solution {@link ToggleButton} is selected; {@code false} otherwise
   */
  protected final boolean showSolution() {
    return solutionButton.isSelected();
  }

}
