package kr.ac.jbnu.se.tetris.models;

import java.util.Random;

public class Shape {
    public enum Tetrominoes {
        NoShape, ZShape, SShape, LineShape, TShape, SquareShape,
        LShape, MirroredLShape
    }

    private Tetrominoes pieceShape;
    private int[][] coords;
    private int[][][] coordsTable;
    static public int[][][] srsKick;
    static public int[][][] IShapeSrsKik;
    private int rotateIndex;    //회전 상태를 저장하는 변수


    public Shape() {
        srsKick = new int[][][]{    //증가는 right로테이션 감소는 left로테이션, x, y순서임
                {{0,0}, {-1, 0}, {-1, 1}, {0, -2}, {-1, -2}},   //0>>1
                {{0,0}, {1, 0}, {1, -1}, {0, 2}, {1, 2}},       //1>>2
                {{0,0}, {1, 0}, {1, 1}, {0, -2}, {1, -2}},      //2>>3
                {{0,0}, {-1, 0}, {-1, -1}, {0, 2}, {-1, 2}},    //3>>0
                {{0,0}, {1, 0}, {1, 1}, {0, -2}, {1, -2}},      //0>>3
                {{0,0}, {1, 0}, {1, -1}, {0, 2}, {1, 2}},      //1>>0
                {{0,0}, {-1, 0}, {-1, 1}, {0, -2}, {-1, -2}},   //2>>1
                {{0,0}, {-1, 0}, {-1, -1}, {0, 2}, {-1, 2}},    //3>>2
        };
        IShapeSrsKik = new int[][][]{
                {{0,0}, {-2, 0}, {1, 0}, {-2, -1}, {1, 2}},     //0>>1
                {{0,0}, {-1, 0}, {2, 0}, {-1, 2}, {2, -1}},     //1>>2
                {{0,0}, {2, 0}, {-1, 0}, {2, 1}, {-1, -2}},     //2>>3
                {{0,0}, {1, 0}, {-2, 0}, {1, -2}, {-2, 1}},     //3>>0
                {{0,0}, {-1, 0}, {2, 0}, {-1, 2}, {2, -1}},     //0>>3
                {{0,0}, {2, 0}, {-1, 0}, {2, 1}, {-1, -2}},     //1>>0
                {{0,0}, {1, 0}, {-2, 1}, {1, -2}, {-2, 1}},     //2>>1
                {{0,0}, {-2, 0}, {1, 0}, {-2, -1}, {1, 2}}      //3>>2
        };
        coords = new int[4][2];
        coordsTable = new int[][][]{    // 블럭의 모양을 초기화 x, y 순서임
                {{0, 0}, {0, 0}, {0, 0}, {0, 0}},

                {{-1, 1}, {0, 1}, {0, 0}, {1, 0}},    //ZShape

                {{-1, 0}, {0, 0}, {0, 1}, {1, 1}},    //SShape

                {{-1, 0}, {0, 0}, {1, 0}, {2, 0}},    //LineShape

                {{-1, 0}, {0, 0}, {1, 0}, {0, 1}},    //Tshape

                {{0, 0}, {1, 0}, {0, 1}, {1, 1}},     //squareShape

                {{-1, -1}, {0, -1}, {0, 0}, {0, 1}},    //LShape,

                {{-1, 1}, {-1, 0}, {0, 0}, {1, 0}}      //MirroredLShape

        };
        setPieceShape(Tetrominoes.NoShape); // 시작블록은 NoShape
        rotateIndex = 0;
    }

    public void plusRotateIndex(){
        this.rotateIndex = (this.rotateIndex + 1) % 4;
    }

    public void minusRotateIndex(){
        this.rotateIndex = (3 + this.rotateIndex) % 4;
    }


    public void setPieceShape(Tetrominoes pieceShape) {
        for (int i = 0; i < 4 ; i++) {
            System.arraycopy(coordsTable[pieceShape.ordinal()][i], 0, coords[i], 0, 2);
        }
        this.pieceShape = pieceShape;
    }

    public int getRotateIndex(){
        return rotateIndex;
    }



    private void setX(int index, int x) {
        coords[index][0] = x;
    }

    private void setY(int index, int y) {
        coords[index][1] = y;
    }

    public int x(int index) {
        return coords[index][0];
    }

    public int y(int index) {
        return coords[index][1];
    }

    public Tetrominoes getPieceShape() {
        return pieceShape;
    }

    public void setRandomShape() {
        Random r = new Random();
        int x = Math.abs(r.nextInt()) % 7 + 1;
        Tetrominoes[] values = Tetrominoes.values();
        setPieceShape(values[x]);
    }

    public int minX() {
        int m = coords[0][0];
        for (int i = 0; i < 4; i++) {
            m = Math.min(m, coords[i][0]);
        }
        return m;
    }


    public int minY() {
        int m = coords[0][1];
        for (int i = 0; i < 4; i++) {
            m = Math.min(m, coords[i][1]);
        }
        return m;
    }

    public Shape rotateLeft() {
        if (pieceShape == Tetrominoes.SquareShape)
            return this;

        Shape result = new Shape();
        result.pieceShape = pieceShape;

        for (int i = 0; i < 4; ++i) {
            result.setX(i, y(i));
            result.setY(i, -x(i));
        }
        return result;
    }

    public Shape rotateRight() {
        if (pieceShape == Tetrominoes.SquareShape)
            return this;

        Shape result = new Shape();
        result.pieceShape = pieceShape;

        for (int i = 0; i < 4; ++i) {
            result.setX(i, -y(i));
            result.setY(i, x(i));
        }
        return result;
    }

}
