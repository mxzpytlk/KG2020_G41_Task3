package rybas.models;

import rybas.ScreenConvertor;
import rybas.linedrawers.LineDrawer;
import rybas.points.RealPoint;
import rybas.points.ScreenPoint;

import java.awt.*;
import java.util.ArrayList;

public class SquareSpiral {
    private RealPoint centre;
    private int turnAmount = 10;
    private double size = 1;
    private boolean clockwiseMovement = true;
    private ArrayList<RealPoint> points;

    public SquareSpiral(RealPoint centre) {
        this.centre = centre;
        points = findPoints();
    }

    public SquareSpiral(RealPoint centre, int turnAmount) throws SpiralParametersException {
        this.centre = centre;
        if (turnAmount < 3) {
            throw new SpiralParametersException("Amounts of lines can't be less than 3");
        }
        this.turnAmount = turnAmount;
        points = findPoints();
    }

    public SquareSpiral(RealPoint centre, double size) throws SpiralParametersException {
        this.centre = centre;
        if (size <= 0) {
            throw new SpiralParametersException("Size can't be less than 0");
        }
        this.size = size;
        points = findPoints();
    }

    public SquareSpiral(RealPoint centre, int turnAmount, double size) throws SpiralParametersException {
        this.centre = centre;
        if (turnAmount < 3) {
            throw new SpiralParametersException("Amounts of lines can't be less than 3");
        }
        this.turnAmount = turnAmount;
        if (size <= 0) {
            throw new SpiralParametersException("Size can't be less than 0");
        }
        this.size = size;
        points = findPoints();
    }

    public RealPoint getCentre() {
        return centre;
    }

    public void setCentre(RealPoint centre) {
        this.centre = centre;
        points = findPoints();
    }

    public int getTurnAmount() {
        return turnAmount;
    }

    public double getSize() {
        return size;
    }

    public void setTurnAmount(int turnAmount) throws SpiralParametersException {
        if (turnAmount < 3) {
            throw new SpiralParametersException("Amounts of lines can't be less than 3");
        }
        this.turnAmount = turnAmount;
        points = findPoints();
    }

    public void setSize(double size) throws SpiralParametersException {
        if (size <= 0) {
            throw new SpiralParametersException("Size can't be less than 0");
        }
        this.size = size;
        points = findPoints();
    }

    protected ArrayList<RealPoint> getPoints() {
        return points;
    }

    public void changeDirection() {
        clockwiseMovement = !clockwiseMovement;
    }

    /**
    * Метод ищет точки, которые находятся в месте,
    * где соединяются линии квадратной спирали и возвращает список этих точек.
    **/
    private ArrayList<RealPoint> findPoints() {
        ArrayList<RealPoint> points = new ArrayList<>();

        double lineLength = (0.1 * size);
        RealPoint prevPoint = centre;
        Direction[] directions = Direction.values();
        int directionsTurnOrder = 0;

        for (int i = 0; i <= turnAmount; i++) {
            points.add(prevPoint);
            prevPoint = directions[directionsTurnOrder].findNextPoint(prevPoint, lineLength);
            //Меняет направление отрисовки линии на 90 градусов
            directionsTurnOrder = clockwiseMovement ? (directionsTurnOrder + 1) % directions.length :
                    (directionsTurnOrder + directions.length - 1) % directions.length;
            if (i % 2 == 1) {
                lineLength += 0.1 * size;
            }
        }

        return points;
    }

    public void draw(LineDrawer ld, ScreenConvertor sc) {
        for (int i = 0; i < points.size() - 1; i++) {
            ScreenPoint p1 = sc.r2s(points.get(i));
            ScreenPoint p2 = sc.r2s(points.get(i + 1));
            ld.drawLine(p1, p2);
        }
    }

    private enum Direction {
        DOWN {
            @Override
            RealPoint findNextPoint(RealPoint start, double length) {
                return new RealPoint(start.getX(), start.getY() + length);
            }
        },
        RIGHT {
            @Override
            RealPoint findNextPoint(RealPoint start, double length) {
                return new RealPoint(start.getX() + length, start.getY());
            }
        },
        UP {
            @Override
            RealPoint findNextPoint(RealPoint start, double length) {
                return new RealPoint(start.getX(), start.getY() - length);
            }
        },
        LEFT {
            @Override
            RealPoint findNextPoint(RealPoint start, double length) {
                return new RealPoint(start.getX() - length, start.getY());
            }
        };

        abstract RealPoint findNextPoint(RealPoint start, double length);
    }
}
