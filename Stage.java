public class Stage {
    public static final int CELL_NONE       = 0;
    public static final int CELL_BOMB       = 1;
    public static final int CELL_SEARCH_END = 2;
    public static final int CELL_OPEN       = 3;
    public static final int DEFAULT_WIDTH   = 10;
    public static final int DEFAULT_HEIGHT  = 10;

    public static int width;
    public static int height;

    private int[][] data;
    private int cntBomb;


    public Stage() {
        init(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }


    public Stage(int width, int height) {
        init(width, height);
    }


    private void init(int width, int height) {
        this.width = width;
        this.height = height;

        initStageData();
    }


    private void initStageData() {
        data = new int[width][height];

        for (int y=0; y<height; ++y)
        for (int x=0; x<width; ++x) {
            data[y][x] = CELL_NONE;
        }

        putBomb();
    }


    private void putBomb() {
        java.util.Random rd = new java.util.Random();

        for (int y=0; y<height; ++y)
        for (int x=0; x<width; ++x) {
            int result = rd.nextInt(100);

            if (result < 20) {
                data[y][x] = CELL_BOMB;
                ++cntBomb;
            }
        }
    }


    public void openCell(int x, int y) {
        data[y][x] = CELL_OPEN;

        openCell(x, y, true);
    }


    private void openCell(int x, int y, boolean loop) {
        if (loop) {
            for (int i=-1; i<=1; ++i)
            for (int j=-1; j<=1; ++j) {
                if (isStageOut(x+j, y+i)) continue;

                if (data[y+i][x+j] == CELL_NONE && 
                    countAroundBomb(x+j, y+i) == 0) {
                    data[y][x] = CELL_SEARCH_END;
                    openCell(x+j, y+i, true);
                } else {
                    data[y][x] = CELL_SEARCH_END;
                    openCell(x+j, y+i, false);
                }
            }
        }
    }


    public int countAroundBomb(int x, int y) {
        int cnt = 0;

        for (int i=-1; i<=1; ++i)
        for (int j=-1; j<=1; ++j) {
            if (isStageOut(x+j, y+i)) continue;

            if (data[y+i][x+j] == CELL_BOMB) {
                ++cnt;
            }
        }

        return cnt;
    }


    public boolean canPut(int x, int y) {
        return data[y][x] == CELL_NONE ||
               data[y][x] == CELL_BOMB;
    }


    public boolean isStageOut(int x, int y) {
        return x<0 || x>=width ||
               y<0 || y>=width;
    }


    public boolean isAllOpen() {
        for (int y=0; y<height; ++y)
        for (int x=0; x<width; ++x) {
            if (isCellNone(x, y)) {
                return false;
            }
        }

        return true;
    }


    public boolean isBomb(int x, int y) {
        return data[y][x] == CELL_BOMB;
    }


    public boolean isCellNone(int x, int y) {
        return data[y][x] == CELL_NONE;
    }


    public void setStageCell(int x, int y, int v) {
        data[y][x] = v;
    }


    public int getStageCell(int x, int y) {
        return data[y][x];
    }


    public int getCntBomb() {
        return cntBomb;
    }
}
