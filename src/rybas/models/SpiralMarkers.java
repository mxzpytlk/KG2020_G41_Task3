package rybas.models;

import rybas.ScreenConvertor;
import rybas.linedrawers.LineDrawer;
import rybas.points.RealPoint;
import rybas.points.ScreenPoint;

import java.awt.*;

import static java.lang.Math.max;


public class SpiralMarkers extends SquareSpiral{

    private RealPoint[] corners = new RealPoint[4];
    private boolean showMarkers = false;

    public SpiralMarkers(RealPoint centre) {
        super(centre);
        setCorners();
    }

    public SpiralMarkers(RealPoint centre, int turnAmount) throws SpiralParametersException {
        super(centre, turnAmount);
        setCorners();
    }

    public SpiralMarkers(RealPoint centre, double size) throws SpiralParametersException {
        super(centre, size);
        setCorners();
    }

    public SpiralMarkers(RealPoint centre, int turnAmount, double size) throws SpiralParametersException {
        super(centre, turnAmount, size);
        setCorners();
    }

    public void changeMarkersVisibility() {
        showMarkers = !showMarkers;
    }

    public void draw(LineDrawer ld, ScreenConvertor sc, Graphics g) {
        if (showMarkers) {
            drawWithMarkers(ld, sc, g);
        } else {
            super.draw(ld, sc);
        }
    }

    private void drawWithMarkers(LineDrawer ln, ScreenConvertor sc, Graphics g) {
        draw(ln, sc);
        g.setColor(Color.BLUE);
        for (RealPoint p : corners) {
            ScreenPoint corn = sc.r2s(p);
            g.fillOval(corn.getX() - 5, corn.getY() - 5, 10, 10);
        }
    }

    public boolean isPointInSpiral(RealPoint p) {
        RealPoint leftUp = getLeftUpCorner();
        RealPoint rightDown = getRightDownCorner();

        return p.getX() > leftUp.getX() && p.getX() < rightDown.getX()
                && p.getY() < leftUp.getY() && p.getY() > rightDown.getY();
    }

    private RealPoint getRightDownCorner() {
        RealPoint rightPoint1 = corners[0].getX() > corners[2].getX() ? corners[0] : corners[2];
        RealPoint rightPoint2 = corners[1].getX() > corners[3].getX() ? corners[0] : corners[2];

        return rightPoint1.getY() < rightPoint2.getY() ? rightPoint1 : rightPoint2;
    }

    private RealPoint getLeftUpCorner() {
        RealPoint leftPoint1 = corners[0].getX() < corners[2].getX() ? corners[0] : corners[2];
        RealPoint leftPoint2 = corners[1].getX() < corners[3].getX() ? corners[0] : corners[2];
        
        return leftPoint1.getY() > leftPoint2.getY() ? leftPoint1 : leftPoint2;
    }

    private void setCorners() {
        for (int i = 0; i < 4; i++) {
            corners[i] = getPoints().get(getPoints().size() - 4 + i);
        }
    }
}
