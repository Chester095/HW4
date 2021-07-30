import java.util.Random;
import java.util.Scanner;

public class game {
    public static int SIZE = 4;                 // размер поля
    public static final char DOT_EMPTY = '*';   // Признак пустого поля
    public static final char DOT_HUMAN = 'X';   // Фишка - человек
    public static final char DOT_AI = 'O';      // Фишка - компьютер
    public static char[][] field;                 // двумерный массив хранит текущее состояние игры
    public static Scanner sc = new Scanner(System.in);  // вспомогательный класс для ввода данных
    public static Random rand = new Random();           // вспомогательный класс для генерации случайных чисел

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
        int variant = 0;
        while (variant != 2) {
            System.out.println("1. Сыграть с компьютером\n2. Выйти");
            variant = sc.nextInt();

            if (variant == 1) {
                System.out.println("Введите размер поля");
                SIZE = sc.nextInt();
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
                        System.out.println("Победил Искусственный Интеллект");
                        break;
                    }
                    if (isMapFull()) {
                        System.out.println("Ничья");
                        break;
                    }
                }
                System.out.println("Игра закончена\n");
            }
        }
        sc.close();
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
        for (int i = 0, j = SIZE - 1; i < SIZE; i++, j--) {
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
        int[][] best;

        // ИЩЕМ ГДЕ БОЛЬШЕ ВСЕГО КРЕСТИКОВ и НОЛИКОВ
        int xHuman = 0;                 // строка с наибольшим кол-вом Крестиков
        int xAI = 0;                    // строка с наибольшим кол-вом Ноликов
        int xiHuman = 0, xjHuman = 0;   // координаты свободного места для хода по Крестикам
        int xiAI = 0, xjAI = 0;         // координаты свободного места для хода по Ноликам
        // проверка по горизонталям
        for (int i = 0; i < SIZE; i++) {
            int xHuman1 = 0;                // считаем кол-во в строке Крестиков
            int xAI1 = 0;                   // считаем кол-во в строке Ноликов
            boolean check = false;          // проверяем чтобы было куда пойти
            int tempj = 0;                  // временная переменная j
            for (int j = 0; j < SIZE; j++) {
                if (field[i][j] == DOT_HUMAN) xHuman1++;
                if (field[i][j] == DOT_AI) xAI1++;
                if (field[i][j] == DOT_EMPTY) {
                    check = true;
                    tempj = j;
                }
            }
            if (check) {                                    // проверка на возможность хода
                if (xHuman1 > xHuman) {
                    xHuman = xHuman1;
                    xiHuman = i;
                    xjHuman = tempj;
                }     // выбираем максимальную строку c Крестиками
                if (xAI1 > xAI) {
                    xAI = xAI1;
                    xiAI = i;
                    xjAI = tempj;
                }                      // выбираем максимальную строку c Ноликами
            }
        }
        // проверка по вертикалям
        int yHuman = 0;                 // столбец с наибольшим кол-вом Крестиков
        int yAI = 0;                    // столбец с наибольшим кол-вом Ноликов
        int yiHuman = 0, yjHuman = 0;   // координаты свободного места для хода по Крестикам
        int yiAI = 0, yjAI = 0;         // координаты свободного места для хода по Ноликам
        for (int i = 0; i < SIZE; i++) {
            int yHuman1 = 0;                // считаем кол-во в столбце Крестиков
            int yAI1 = 0;                   // считаем кол-во в столбце Ноликов
            boolean check = false;          // проверяем чтобы было куда пойти
            int tempj = 0;                  // временная переменная j
            for (int j = 0; j < SIZE; j++) {
                if (field[j][i] == DOT_HUMAN) yHuman1++;
                if (field[j][i] == DOT_AI) yAI1++;
                if (field[j][i] == DOT_EMPTY) {
                    check = true;
                    tempj = j;
                }
            }
            if (check) {                                    // проверка на возможность хода
                if (yHuman1 > yHuman) {
                    yHuman = yHuman1;
                    yiHuman = i;
                    yjHuman = tempj;
                }     // выбираем максимальный столбец c Крестиками
                if (yAI1 > yAI) {
                    yAI = yAI1;
                    yjAI = i;
                    yiAI = tempj;
                }                       // выбираем максимальный столбец c Ноликами
            }
        }
        // проверка диагонали лв-пн
        int xyHuman = 0;            // считаем кол-во Крестиков по диагонали лв-пн
        int xyAI = 0;               // считаем кол-во Ноликов по диагонали лв-пн
        boolean checkXY = false;    // проверяем чтобы было куда пойти
        int xyi = 0, xyj = 0;       // координаты свободного места для хода
        for (int i = 0; i < SIZE; i++) {
            if (field[i][i] == DOT_HUMAN) xyHuman++;
            if (field[i][i] == DOT_AI) xyAI++;
            if (field[i][i] == DOT_EMPTY) {
                checkXY = true;
                xyi = i;
                xyj = i;
            }
        }
        if (!checkXY) {
            xyHuman = 0;
            xyAI = 0;
        }
        // проверка диагонали лн-нв
        int yxHuman = 0;            // считаем кол-во Крестиков по диагонали лн-нв
        int yxAI = 0;               // считаем кол-во Ноликов по диагонали лн-нв
        boolean checkYX = false;    // проверяем чтобы было куда пойти
        int yxi = 0, yxj = 0;       // координаты свободного места для хода
        for (int i = 0, j = SIZE - 1; i < SIZE; i++, j--) {
            if (field[i][j] == DOT_HUMAN) yxHuman++;
            if (field[i][j] == DOT_AI) yxAI++;
            if (field[i][j] == DOT_EMPTY) {
                checkYX = true;
                yxi = i;
                yxj = j;
            }
        }
        if (!checkYX) {
            yxHuman = 0;
            yxAI = 0;
        }

        //TODO надо прихуячить проверку на тупые ходы. Сделать, чтобы

        // выбираем лучший вариант
        // проверяем есть ли ход, который сразу приведёт к победе
        if (xAI == SIZE - 1 || yAI == SIZE - 1 || xyAI == SIZE - 1 || yxAI == SIZE - 1) {
            if (xAI == SIZE - 1) field[xiAI][xjAI] = DOT_AI;
            else if (yAI == SIZE - 1) field[yiAI][yjAI] = DOT_AI;
            else if (xyAI == SIZE - 1) field[xyi][xyj] = DOT_AI;
            else field[yxi][yxj] = DOT_AI;
        } else {
            int max = xHuman, maxX = xiHuman, maxY = xjHuman;
            if (yHuman > max) {
                max = yHuman;
                maxX = yjHuman;
                maxY = yiHuman;
            }
            if (xyHuman > max) {
                max = xyHuman;
                maxX = xyi;
                maxY = xyj;
            }
            if (yxHuman > max) {
                maxX = yxi;
                maxY = yxj;
            }
            field[maxX][maxY] = DOT_AI;
        }
    }

    //    обработка хода человека
    public static void humanTurn() {
        int x, y;
        do {
            System.out.println("Введите номер столбца и номер строки через пробел");
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