import java.awt.Point;
import java.util.Scanner;


public class GameManager {
    private Stage stage;
    private Scanner sc;
    private int level;
    private Point cursor;
    private ConsoleController clear;


    public GameManager() {
        init();
    }


    private void init() {
        buildInstance();
        isGameOver = false;
    }


    private void buildInstance() {
        stage = new Stage();
        sc = new Scanner(System.in);
        cursor = new Point();
        clear = new ConsoleController("/bin/bash", "-c", "clear");
    }


    private void update() {
        if (!isEnd()) {
            if (stage.canPut(cursor.x, cursor.y)) {
                stage.openCell(cursor.x, cursor.y);
            }
        } else {
            isGameOver = true;
        }
    }


    private void input() {
        cursor.x = sc.nextInt();
        cursor.y = sc.nextInt();
    }


    private boolean isEnd() {
        boolean endFlag = false;

        if (stage.isBomb(cursor.x, cursor.y) ||
            stage.isAllOpen()) {
            endFlag = true;
        }

        return endFlag;
    }


    private void draw() {
        clear.execute();

        System.out.print("   ");
        for (int x=0; x<Stage.width; ++x) {
            System.out.printf(" %d ", x);
        }
        System.out.println();

        for (int y=0; y<Stage.width; ++y) {
            System.out.printf(" %d ", y);
            for (int x=0; x<Stage.height; ++x) {
                switch (stage.getStageCell(x, y)) {
                case Stage.CELL_NONE:
                    System.out.print(" + ");
                    break;

                case Stage.CELL_BOMB:
                    System.out.print(isEnd()?" B ":" + ");
                    break;

                case Stage.CELL_SEARCH_END:
                    // System.out.print(" + ");
                    System.out.printf(" %d ", stage.countAroundBomb(x, y));
                    break;

                case Stage.CELL_OPEN:
                    // System.out.print(" O ");
                    System.out.printf(" %d ", stage.countAroundBomb(x, y));
                    break;

                default:
                    System.out.print(" E ");
                }
            }
            System.out.println();
        }
    }


    private void selectStage() {
        System.out.println("ステージを選択してください。");
        System.out.println("1...初級, 2...中級, 3...上級");
        System.out.print("1〜3を入力>>>");
        level = sc.nextInt();
    }


    private void gameLoop() {
        clear.execute();
        selectStage();

        clear.execute();
        while (!isEnd()) {
            draw();
            input();
            update();
        }

        draw();
        System.exit(0);
    }


    public void run() {
        gameLoop();
    }
}
