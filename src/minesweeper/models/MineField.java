package minesweeper.models;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MineField {
    private int rows;
    private int columns;
    private int mines;
    private Cell[][] cells;
    private GameState state;
    private Random random;
    private List<IMineFieldUpdater> fieldUpdaters;

    public MineField(int rows, int columns, int mines, Random random) {
        // TODO: Check arguments, throw exceptions where appropriate
        this.rows = rows;
        this.columns = columns;
        this.mines = mines;
        this.random = random;
        this.cells = new Cell[rows][columns];
        this.fieldUpdaters = new ArrayList<>();

        initialize(-1, -1);
    }

    public MineField(int rows, int columns, int mines) {
        this(rows, columns, mines, new Random());
    }

    public Cell getCellAt(int row, int column) {
        throw new NotImplementedException();
    }

    public void setCellAt(int row, int column, Cell cell) {
        throw new NotImplementedException();
    }

    // TODO: Logic for cell interaction; logic for game management (change of status)

    private void initialize(int noMineRow, int noMineCol) {
        // TODO: Generate mines, skip a cell if necessary
        for (int row = 0; row < this.rows; row++) {
            for (int col = 0; col < this.columns; col++) {
                this.cells[row][col] = new Cell(this, row, col, CellType.UNOPENED);
            }
        }

        for (int i = 0; i < this.mines; i++) {

            int currentRow = random.nextInt(rows);
            int currentCol = random.nextInt(columns);

            if ((currentRow == noMineRow && currentCol == noMineCol) || this.cells[currentRow][currentCol].isMine()) {
                i--;
            } else {
                this.cells[currentRow][currentCol].makeMine();
            }
        }

        updateBoard();
    }

    private void updateBoard() {
        for (IMineFieldUpdater updater : fieldUpdaters) {
            updater.updateBoard();
        }
    }
}
