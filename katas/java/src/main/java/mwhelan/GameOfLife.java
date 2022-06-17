package mwhelan;

import java.util.ArrayList;
import java.util.List;

/**
 * PROBLEM:
 * Create a simple implementation for John Conway's Game of Life.
 *
 * @author Mike Lowe
 */
public final class GameOfLife {

  private final int rows;
  private final int columns;
  private final Cell[][] cells;

  public GameOfLife(final int rows, final int columns) {
    this.rows = rows;
    this.columns = columns;
    validate(this.rows, this.columns);
    this.cells = new Cell[rows][columns];
    initCells();
  }

  private void validate(final int rows, final int columns) {
    if (rows < 0) {
      throw new IllegalArgumentException("rows cannot be less than 0");
    } else if (columns < 0) {
      throw new IllegalArgumentException("columns cannot be less than 0");
    }
  }

  private void initCells() {
    createCells();
    addCellNeighbours();
  }

  private void createCells() {
    for (int row = 0; row < rows; row++) {
      for (int column = 0; column < columns; column++) {
        cells[row][column] = new Cell();
      }
    }
  }

  private void addCellNeighbours() {
    for (int row = 0; row < rows; row++) {
      for (int column = 0; column < columns; column++) {
        addCellNeighbour(row, column);
      }
    }
  }

  private void addCellNeighbour(final int row, final int column) {
    final Cell cell = getCell(row, column);
    final int north = row - 1;
    final int east = column + 1;
    final int south = row + 1;
    final int west = column - 1;
    cell.addNeighbour(getCell(north, column));
    cell.addNeighbour(getCell(north, east));
    cell.addNeighbour(getCell(row, east));
    cell.addNeighbour(getCell(south, east));
    cell.addNeighbour(getCell(south, column));
    cell.addNeighbour(getCell(south, west));
    cell.addNeighbour(getCell(row, west));
    cell.addNeighbour(getCell(north, west));
  }

  public int getRows() {
    return rows;
  }

  public int getColumns() {
    return columns;
  }

  public Cell getCell(final int row, final int column) {
    if (row < 0 || row >= rows || column < 0 || column >= columns) {
      return null;
    }
    return cells[row][column];
  }

  public void nextGeneration() {
    calculateNextGeneration();
    switchToNextGeneration();
  }

  private void calculateNextGeneration() {
    for (final Cell[] row : cells) {
      for (final Cell cell : row) {
        applyRules(cell);
      }
    }
  }

  private void applyRules(final Cell cell) {
    final int aliveNeighbours = cell.getAliveNeighbours();
    cell.isAliveInNextState = (cell.isAlive && aliveNeighbours == 2) || aliveNeighbours == 3;
  }

  private void switchToNextGeneration() {
    for (final Cell[] row : cells) {
      for (final Cell cell : row) {
        cell.isAlive = cell.isAliveInNextState;
      }
    }
  }

  public static final class Cell {

    private boolean isAlive;
    private boolean isAliveInNextState;
    private final List<Cell> neighbours = new ArrayList<>();

    public boolean isAlive() {
      return isAlive;
    }

    public void setAlive(final boolean isAlive) {
      this.isAlive = isAlive;
    }

    private void addNeighbour(final Cell neighbour) {
      if (neighbour != null) {
        neighbours.add(neighbour);
      }
    }

    private int getAliveNeighbours() {
      int aliveNeighbours = 0;
      for (final Cell neighbour : neighbours) {
        if (neighbour.isAlive) {
          aliveNeighbours++;
        }
      }
      return aliveNeighbours;
    }

  }

}
