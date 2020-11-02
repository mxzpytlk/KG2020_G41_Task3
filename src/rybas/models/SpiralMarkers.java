package rybas.models;

import rybas.ScreenConvertor;
import rybas.linedrawers.LineDrawer;
import rybas.points.RealPoint;
import rybas.points.ScreenPoint;

import java.awt.*;

public class SpiralMarkers extends SquareSpiral{

    private RealPoint[] corners = new RealPoint[4];
    private RealPoint centre;
    private boolean showMarkers = false;

    public SpiralMarkers(RealPoint centre, double scale) {
        super(centre, scale);
        setCorners();
    }

    public SpiralMarkers(RealPoint centre, int turnAmount, double scale) throws SpiralParametersException {
        super(centre, turnAmount, scale);
        setCorners();
    }

    public SpiralMarkers(RealPoint centre, double scale, double size) throws SpiralParametersException {
        super(centre, scale, size);
        setCorners();
    }

    public SpiralMarkers(RealPoint centre, int turnAmount, double scale, double size) throws SpiralParametersException {
        super(centre, turnAmount,scale, size);
        setCorners();
    }

    @Override
    public void setCentre(RealPoint centre) {
        super.setCentre(centre);
        setCorners();
    }

    @Override
    public void setSize(double size) throws SpiralParametersException {
        super.setSize(size);
        setCorners();
    }

    @Override
    public void setTurnAmount(int turnAmount) throws SpiralParametersException {
        super.setTurnAmount(turnAmount);
        setCorners();
    }

    public void changeMarkersVisibility() {
        showMarkers = !showMarkers;
    }

    public boolean isShowMarkers() {
        return showMarkers;
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
        ScreenPoint middlePoint = sc.r2s(centre);
        g.fillOval(middlePoint.getX() - 5, middlePoint.getY() - 5, 10, 10);
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

    public boolean isPointInCenter(RealPoint p) {
        return (p.getX() - centre.getX()) * (p.getX() - centre.getX()) +
                (p.getY() - centre.getY()) * (p.getY() - centre.getY()) < 2.5 * scale;
    }

    private RealPoint getRightDownCorner() {
        RealPoint rightPoint1 = corners[0].getX() > corners[2].getX() ? corners[0] : corners[2];
        RealPoint rightPoint2 = corners[1].getX() > corners[3].getX() ? corners[1] : corners[3];

        return rightPoint1.getY() < rightPoint2.getY() ? rightPoint1 : rightPoint2;
    }

    private RealPoint getLeftUpCorner() {
        RealPoint leftPoint1 = corners[0].getX() < corners[2].getX() ? corners[0] : corners[2];
        RealPoint leftPoint2 = corners[1].getX() < corners[3].getX() ? corners[1] : corners[3];
        
        return leftPoint1.getY() > leftPoint2.getY() ? leftPoint1 : leftPoint2;
    }

    private void setCorners() {
        centre = getPoints().get(0);
        for (int i = 0; i < 4; i++) {
            corners[i] = getPoints().get(getPoints().size() - 4 + i);
        }
    }
}
