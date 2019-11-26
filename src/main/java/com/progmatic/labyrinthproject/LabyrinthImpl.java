package com.progmatic.labyrinthproject;

import com.progmatic.labyrinthproject.enums.CellType;
import com.progmatic.labyrinthproject.enums.Direction;
import com.progmatic.labyrinthproject.exceptions.CellException;
import com.progmatic.labyrinthproject.exceptions.InvalidMoveException;
import com.progmatic.labyrinthproject.interfaces.Labyrinth;
import com.progmatic.labyrinthproject.interfaces.Player;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author pappgergely
 */
public class LabyrinthImpl implements Labyrinth {

    public static CellType[][] myLabyrinth;
    public static int lWidth;
    public static int lHeight;
    public RandomPlayer player;
    Coordinate playerCord;


    public LabyrinthImpl() {
        loadLabyrinthFile("labyrinth1.txt");
        setSize(lWidth, lHeight);


    }

    public void movePlayer(Direction direction) throws InvalidMoveException {
        int x = playerCord.getRow();
        int y = playerCord.getCol();

        switch (direction) {
            case EAST:
                if (!myLabyrinth[x][y + 1].equals(CellType.EMPTY) || myLabyrinth[0].length <= y + 1) {
                    throw new InvalidMoveException();
                } else {
                    y++;
                }
                break;
            case NORTH:
                if (!(x < 1)) {
                    if (!myLabyrinth[x - 1][y].equals(CellType.EMPTY) || myLabyrinth.length - 1 < 0) {
                        throw new InvalidMoveException();
                    } else {
                        x--;
                    }

                } else throw new InvalidMoveException();
                break;
            case WEST: // (1,0)
                if (!(y < 1)) {
                    if (!myLabyrinth[x][y - 1].equals(CellType.EMPTY) || myLabyrinth[0].length - 1 <= 0) {
                        throw new InvalidMoveException();
                    } else {
                        y--;
                    }
                }
                else throw new InvalidMoveException();
                break;
            case SOUTH:
                if (!myLabyrinth[x + 1][y].equals(CellType.EMPTY) || myLabyrinth[0].length <= x + 1) {
                    throw new InvalidMoveException();
                } else {
                    x++;
                }
                break;
        }
        playerCord = new Coordinate(x, y);
    }

    public List<Direction> possibleMoves() {
        List<Direction> moves = new ArrayList<>();
        if (playerCord.getCol() > 0) {
            if ((myLabyrinth[playerCord.getRow()][playerCord.getCol() - 1].equals(CellType.EMPTY)) ||
                    (myLabyrinth[playerCord.getRow()][playerCord.getCol() - 1].equals(CellType.END))) {
                moves.add(Direction.WEST);
            }
        }
        //if (playerCord.getRow() < myLabyrinth.length)
        if ((myLabyrinth[playerCord.getRow() + 1][playerCord.getCol()].equals(CellType.EMPTY)) ||
                (myLabyrinth[playerCord.getRow() + 1][playerCord.getCol()].equals(CellType.END)))
        {
            moves.add(Direction.SOUTH);
        }
        if ((myLabyrinth[playerCord.getRow() - 1][playerCord.getCol()].equals(CellType.EMPTY)) ||
                (myLabyrinth[playerCord.getRow() - 1][playerCord.getCol()].equals(CellType.END)))
        {
            moves.add(Direction.NORTH);
        }
        if ((myLabyrinth[playerCord.getRow()][playerCord.getCol() + 1].equals(CellType.EMPTY)) ||
                (myLabyrinth[playerCord.getRow()][playerCord.getCol() + 1].equals(CellType.END)))

        {
            moves.add(Direction.EAST);
        }


        return moves;
    }

    public boolean hasPlayerFinished() {
        if (myLabyrinth[playerCord.getRow()][playerCord.getCol()] == CellType.END) {
            return true;
        }
        return false;
    }

    public Coordinate getPlayerPosition() {
        return playerCord;
    }

    public void setCellType(Coordinate c, CellType type) throws CellException {
        if (myLabyrinth.length < c.getRow() || myLabyrinth[0].length < c.getCol()) {
            throw new CellException(c.getRow(), c.getCol(), "Nincs ilyen mező");
        } else {
            myLabyrinth[c.getRow()][c.getCol()] = type;
        }
    }

    public CellType getCellType(Coordinate c) throws CellException {
        if (c.getCol() < 0 || c.getRow() < 0) throw new CellException(c.getRow(), c.getCol(), "Nem jó koordináta");
        if (c.getRow() >= myLabyrinth.length) throw new CellException(c.getRow(), c.getCol(), "Nincs ilyen mező");
        if (c.getCol() >= myLabyrinth[0].length) throw new CellException(c.getRow(), c.getCol(), "Nincs ilyen mező");
        return myLabyrinth[c.getRow()][c.getCol()];

    }

    public void setSize(int width, int height) {

        myLabyrinth = new CellType[width][height];
    }

    public int getWidth() {
        if (myLabyrinth.length < 1) {
            return -1;
        }
        return myLabyrinth.length;
    }

    public int getHeight() {
        if (myLabyrinth[0].length < 1) {
            return -1;
        }
        return myLabyrinth[0].length;
    }

    @Override
    public void loadLabyrinthFile(String fileName) {
        try {
            Scanner sc = new Scanner(new File(fileName));
            int width = Integer.parseInt(sc.nextLine());
            lWidth = width;
            int height = Integer.parseInt(sc.nextLine());
            lHeight = height;
            setSize(width, height);
            for (int hh = 0; hh < height; hh++) {
                String line = sc.nextLine();
                for (int ww = 0; ww < width; ww++) {
                    Coordinate cordi = new Coordinate(ww, hh);
                    switch (line.charAt(ww)) {
                        case 'W':
                            setCellType(cordi, CellType.WALL);
                            break;
                        case 'E':
                            setCellType(cordi, CellType.END);
                            break;
                        case 'S':
                            setCellType(cordi, CellType.START);
                            playerCord = new Coordinate(ww, hh);
                            //TODO player ideállítása
                            break;
                        case ' ':
                            setCellType(cordi,CellType.EMPTY);
                            break;
                    }
                }
            }
        } catch (FileNotFoundException | NumberFormatException | CellException ex) {
            System.out.println(ex.toString());
        }
    }

}
