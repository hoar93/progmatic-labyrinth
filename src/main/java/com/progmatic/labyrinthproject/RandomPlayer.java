package com.progmatic.labyrinthproject;

import com.progmatic.labyrinthproject.enums.CellType;
import com.progmatic.labyrinthproject.enums.Direction;
import com.progmatic.labyrinthproject.interfaces.Labyrinth;
import com.progmatic.labyrinthproject.interfaces.Player;

import java.util.Random;

public class RandomPlayer implements Player {
    static LabyrinthImpl mylab = new LabyrinthImpl();



    Random r = new Random();
    int whereTo = r.nextInt(4);
    @Override

    public Direction nextMove(Labyrinth l) {

        Direction newDirection = Direction.EAST;
        switch (whereTo) {
            case 0:
                newDirection = Direction.NORTH;
                break;
            case 1:
                newDirection = Direction.EAST;
                break;
            case 2:
                newDirection = Direction.SOUTH;
                break;
            case 3:
                newDirection = Direction.WEST;
                break;
        }

        return newDirection;
    }
}
