package rybas.models;

import rybas.linedrawers.LineDrawer;
import rybas.points.ScreenPoint;
import java.util.ArrayList;

public class SquareSpiral {
    private ScreenPoint centre;
    private int turnAmount = 10;
    private double size = 1;
    private boolean clockwiseMovement = true;
    private ArrayList<ScreenPoint> points = new ArrayList<>();

    public SquareSpiral(ScreenPoint centre) {
        this.centre = centre;
        points = findPoints();
    }

    public SquareSpiral(ScreenPoint centre, int turnAmount) {
        this.centre = centre;
        this.turnAmount = turnAmount;
        this.size = 1;
        points = findPoints();
    }

    public SquareSpiral(ScreenPoint centre, double size) {
        this.centre = centre;
        this.turnAmount = 10;
        this.size = size;
        points = findPoints();
    }

    public SquareSpiral(ScreenPoint centre, int turnAmount, double size) {
        this.centre = centre;
        this.turnAmount = turnAmount;
        this.size = size;
        points = findPoints();
    }

    public ScreenPoint getCentre() {
        return centre;
    }

    public void setCentre(ScreenPoint centre) {
        this.centre = centre;
        points = findPoints();
    }

    public int getTurnAmount() {
        return turnAmount;
    }

    public double getSize() {
        return size;
    }

    public void setTurnAmount(int turnAmount) {
        this.turnAmount = turnAmount;
        points = findPoints();
    }

    public void setSize(double size) {
        this.size = size;
        points = findPoints();
    }

    public void changeDirection() {
        clockwiseMovement = !clockwiseMovement;
    }

    /**
    * Метод ищет точки, которые находятся в месте,
    * где соединяются линии квадратной спирали и возвращает список этих точек.
    **/
    private ArrayList<ScreenPoint> findPoints() {
        ArrayList<ScreenPoint> points = new ArrayList<>();

        int lineLength = (int) (20 * size);
        ScreenPoint prevPoint = centre;
        Direction[] directions = Direction.values();
        int directionsTurnOrder = 0;

        for (int i = 0; i <= turnAmount; i++) {
            points.add(prevPoint);
            prevPoint = directions[directionsTurnOrder].findNextPoint(prevPoint, lineLength);
            //Меняет направление отрисовки линии на 90 градусов
            directionsTurnOrder = clockwiseMovement ? (directionsTurnOrder + 1) % directions.length :
                    (directionsTurnOrder + directions.length - 1) % directions.length;
            if (i % 2 == 1) {
                lineLength += 20 * size;
            }
        }

        return points;
    }

    public void draw(LineDrawer ld) {
        for (int i = 0; i < points.size() - 1; i++) {
            ld.drawLine(points.get(i), points.get(i + 1));
        }
    }

    private enum Direction {
        DOWN {
            @Override
            ScreenPoint findNextPoint(ScreenPoint start, int length) {
                return new ScreenPoint(start.getX(), start.getY() - length);
            }
        },
        RIGHT {
            @Override
            ScreenPoint findNextPoint(ScreenPoint start, int length) {
                return new ScreenPoint(start.getX() + length, start.getY());
            }
        },
        UP {
            @Override
            ScreenPoint findNextPoint(ScreenPoint start, int length) {
                return new ScreenPoint(start.getX(), start.getY() + length);
            }
        },
        LEFT {
            @Override
            ScreenPoint findNextPoint(ScreenPoint start, int length) {
                return new ScreenPoint(start.getX() - length, start.getY());
            }
        };

        abstract ScreenPoint findNextPoint(ScreenPoint start, int length);
    }
}
