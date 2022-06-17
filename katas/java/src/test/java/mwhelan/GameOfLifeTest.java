package mwhelan;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Mike Lowe
 */
public final class GameOfLifeTest {

  private GameOfLife gameOfLife;
  private boolean[][] state;
  private boolean[][] expectedState;

  @Test(expected = IllegalArgumentException.class)
  public void constructor_negativeRows_throwsIllegalArgumentException() {
    new GameOfLife(-1, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructor_negativeColumns_throwsIllegalArgumentException() {
    new GameOfLife(3, -1);
  }

  @Test
  public void cellWithFewerThanTwoLiveNeighboursDies() {
    gameOfLife = new GameOfLife(3, 3);
    state = new boolean[3][3];
    state[0][0] = true;
    setState(state);
    gameOfLife.nextGeneration();
    expectedState = new boolean[3][3];
    stateAssertions(expectedState);
  }

  @Test
  public void cellWithTwoLiveNeighboursLives() {
    gameOfLife = new GameOfLife(3, 3);
    state = new boolean[3][3];
    state[0][0] = true;
    state[0][1] = true;
    state[1][0] = true;
    setState(state);
    gameOfLife.nextGeneration();
    expectedState = state;
    expectedState[1][1] = true;
    stateAssertions(expectedState);
  }

  @Test
  public void cellWithThreeLiveNeighboursLives() {
    gameOfLife = new GameOfLife(3, 3);
    state = new boolean[3][3];
    state[0][0] = true;
    state[0][1] = true;
    state[1][0] = true;
    state[1][1] = true;
    setState(state);
    gameOfLife.nextGeneration();
    expectedState = state;
    stateAssertions(expectedState);
  }

  @Test
  public void cellWithMoreThanThreeLiveNeighboursDies() {
    gameOfLife = new GameOfLife(3, 3);
    state = new boolean[3][3];
    state[0][0] = true;
    state[0][1] = true;
    state[0][2] = true;
    state[1][0] = true;
    state[1][1] = true;
    setState(state);
    gameOfLife.nextGeneration();
    expectedState = state;
    expectedState[0][1] = false;
    expectedState[1][1] = false;
    expectedState[1][2] = true;
    stateAssertions(expectedState);
  }

  @Test
  public void deadCellWithExactlyThreeLiveNeighboursBecomesLive() {
    gameOfLife = new GameOfLife(3, 3);
    state = new boolean[3][3];
    state[0][0] = true;
    state[0][1] = true;
    state[0][2] = true;
    setState(state);
    gameOfLife.nextGeneration();
    expectedState = state;
    expectedState[0][0] = false;
    expectedState[0][2] = false;
    expectedState[1][1] = true;
    stateAssertions(expectedState);
  }

  private void setState(final boolean[][] state) {
    for (int row = 0; row < gameOfLife.getRows(); row++) {
      for (int column = 0; column < gameOfLife.getColumns(); column++) {
        final GameOfLife.Cell cell = gameOfLife.getCell(row, column);
        final boolean isAlive = state[row][column];
        cell.setAlive(isAlive);
      }
    }
  }

  private void stateAssertions(final boolean[][] expectedState) {
    for (int row = 0; row < gameOfLife.getRows(); row++) {
      for (int column = 0; column < gameOfLife.getColumns(); column++) {
        final GameOfLife.Cell cell = gameOfLife.getCell(row, column);
        final boolean expectedAlive = expectedState[row][column];
        assertEquals(expectedAlive, cell.isAlive());
      }
    }
  }

}
