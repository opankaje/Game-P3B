package com.example.tugasbesar2;

public class LaserBeam {
    private int beamPosX;
    private int beamStartY;
    private int beamEndY;

    public LaserBeam(int beamPosX, int beamStartY, int beamEndY) {
        this.beamPosX = beamPosX;
        this.beamStartY = beamStartY;
        this.beamEndY = beamEndY;
    }

    public int getBeamPosX() {
        return beamPosX;
    }

    public void setBeamPosX(int beamPosX) {
        this.beamPosX = beamPosX;
    }

    public int getBeamStartY() {
        return beamStartY;
    }

    public void setBeamStartY(int beamStartY) {
        this.beamStartY = beamStartY;
    }

    public int getBeamEndY() {
        return beamEndY;
    }

    public void setBeamEndY(int beamEndY) {
        this.beamEndY = beamEndY;
    }
}
