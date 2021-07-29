import java.util.Random;
import java.util.Scanner;

public class game {
    public static int SIZE = 4;                 // размер поля
    public static final char DOT_EMPTY = '*';   // Признак пустого поля
    public static final char DOT_HUMAN = 'X';   // Фишка - человек
    public static final char DOT_AI = 'O';      // Фишка - компьюетр
    public static char[][] field;                 // двумерный масси хранит текущее состояние игры
    public static Scanner sc = new Scanner(System.in);  // вспомогательный класс для ввода данных
    public static Random rand = new Random();           // впомогательный класс для генерации случайных чисел

    //    инициализация объектов игры
    public static void initialize() {
        field = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                field[i][j] = DOT_EMPTY;
            }
        }
    }

    public static void main(String[] args) {
        initialize();
        printMap();
        while (true) {
            humanTurn();    // обработка хода человека
            printMap();
            if (checkWin(DOT_HUMAN)) {
                System.out.println("Победил человек");
                break;
            }
            if (isMapFull()) {
                System.out.println("Ничья");
                break;
            }
            aiTurn();
            printMap();
            if (checkWin(DOT_AI)) {
                System.out.println("Победил Искуственный Интеллект");
                break;
            }
            if (isMapFull()) {
                System.out.println("Ничья");
                break;
            }
        }
        System.out.println("Игра закончена");
    }


    // проверка победителя
    public static boolean checkWin(char symb) {
        int x = 0;
        // проверка по горизонталям
        for (int i = 0; i < SIZE; i++) {
            x = 0;
            for (int j = 0; j < SIZE; j++) {
                if (field[i][j] == symb) x++;
            }
            if (x == SIZE) return true;
        }

        // проверка по вертикалям
        for (int i = 0; i < SIZE; i++) {
            x = 0;
            for (int j = 0; j < SIZE; j++) {
                if (field[j][i] == symb) x++;
            }
            if (x == SIZE) return true;
        }

        // проверка диагонали лв-пн
        x = 0;
        for (int i = 0; i < SIZE; i++) {
            if (field[i][i] == symb) x++;
        }
        if (x == SIZE) return true;

        // проверка диагонали лн-нв
        x = 0;
        for (int i = 0, j=SIZE-1; i < SIZE; i++, j--) {
            if (field[i][j] == symb) x++;
        }
        if (x == SIZE) return true;

        return false;
    }

    // проверка заполнения поля
    public static boolean isMapFull() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (field[i][j] == DOT_EMPTY) return false;
            }
        }
        return true;
    }

    //    обработка хода компьютера
    public static void aiTurn() {
        int x, y;
        do {
            x = rand.nextInt(SIZE);
            y = rand.nextInt(SIZE);
        } while (!isCellValid(x, y));
        System.out.println("Компьютер походил в точку " + (x + 1) + " " + (y + 1));
        field[y][x] = DOT_AI;
    }

    //    обработка хода человека
    public static void humanTurn() {
        int x, y;
        do {
            System.out.println("Введите координаты в формате X Y");
            x = sc.nextInt() - 1;
            y = sc.nextInt() - 1;
        } while (!isCellValid(x, y));
        field[y][x] = DOT_HUMAN;
    }

    //проверка корректности ввода
    public static boolean isCellValid(int x, int y) {
        if (x < 0 || x >= SIZE || y < 0 || y >= SIZE) return false;
        if (field[y][x] == DOT_EMPTY) return true;
        return false;
    }

    // отрисовка игрового поля
    public static void printMap() {
        //первая строка
        System.out.print("+");
        for (int i = 0; i <= SIZE * 2; i++) {
            if (i % 2 == 0) System.out.print("-");
            else System.out.print(i / 2 + 1);
        }
        System.out.println();
        for (int y = 0; y < SIZE; y++) {
            System.out.print((y + 1) + "|");
            for (int x = 0; x < SIZE; x++) {
                System.out.print(field[y][x] + "|");
            }
            System.out.println();
        }
        for (int i = 0; i <= SIZE * 2 + 1; i++)
            System.out.print("-");
        System.out.println();
    }
}