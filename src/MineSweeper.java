import java.util.Random;
import java.util.Scanner;

public class MineSweeper {
    int row;
    int column;
    int bombTotal;
    int bombCounter;
    int openingCounter;
    boolean isLose;
    int[][] bombPlaces;
    String[][] field;
    String[][] gameField;


    MineSweeper(int row, int colum) {
        this.row = row;
        this.column = colum;
        this.bombTotal = (this.row * this.column) / 4;
        if (this.bombTotal < 2) {
            this.bombTotal = 2;
        }
        this.bombCounter = 0;
        this.openingCounter = (this.row * this.column) - this.bombTotal;
        this.isLose = false;
        this.field = new String[this.row][this.column];
        this.gameField = new String[this.row][this.column];
        this.bombPlaces = new int[this.bombTotal][2];
    }

    void run() {
        System.out.println("MAYIN TARLASINA HOŞ GELDİNİZ !!!");
        printLine();
        bombPlaces();
        createField();
        createGameField();
        printMatrix(this.gameField);
        game();
        winningCondition();
    }

    void createField() {
        for (int i = 0; i < this.field.length; i++) {
            for (int j = 0; j < this.field[i].length; j++) {
                boolean isBomb = false;
                for (int[] row : this.bombPlaces) {
                    if (row[0] == i && row[1] == j) {
                        isBomb = true;
                        break;
                    }
                }
                if (isBomb) {
                    this.field[i][j] = "* ";
                } else {
                    this.field[i][j] = "- ";
                }
            }
        }
    }

    void createGameField() {
        for (int i = 0; i < this.gameField.length; i++) {
            for (int j = 0; j < this.gameField[i].length; j++) {
                gameField[i][j] = "- ";
            }
        }
    }

    void game() {
        boolean firstRound = true;
        while (0 < openingCounter) {
            Scanner input = new Scanner(System.in);
            if (!firstRound) {
                System.out.print("Bayrak Ekle-Çıkar ? 1:EVET 2:HAYIR ==> ");
                int condition = input.nextInt();
                if (condition == 1) {
                    System.out.print("EKLE:1  ÇIKAR:2  ÇIKIŞ:3 ==> ");
                    int condition2 = input.nextInt();
                    if (condition2 == 1) {
                        putFlag();
                    } else if (condition2 == 2) {
                        removeFlag();
                    }
                    printLine();
                    printMatrix(this.gameField);
                }
            }

            System.out.print("Aç Satır ==> ");
            int a = input.nextInt();
            int tempRow = a - 1;
            System.out.print("Aç Sütun ==> ");
            int b = input.nextInt();
            int tempColumn = b - 1;

            if ((a == 0) && (b == 0)) {
                printLine();
                printMatrix(this.gameField);
                firstRound = false;
                continue;
            }

            if ((tempRow < 0) || (tempColumn < 0) || (this.field.length <= tempRow) || (this.field[0].length <= tempColumn)) {
                System.out.println("Lütfen Geçerli Değerler Giriniz !!!");
            } else if (!this.gameField[tempRow][tempColumn].equals("- ")) {
                System.out.println("Açılmış veya Bayraklı Blokları Açamazsınız !!! Eğer Bayrak Varsa Önce Kaldırınız !!!");
                printLine();
                printMatrix(this.gameField);
                firstRound = false;
            } else if (this.field[tempRow][tempColumn].equals("* ")) {
                this.isLose = true;
                break;
            } else {
                this.bombCounter = 0;
                if (tempColumn > 0 && field[tempRow][tempColumn - 1].equals("* "))
                    this.bombCounter++;
                if (tempColumn < field[0].length - 1 && field[tempRow][tempColumn + 1].equals("* "))
                    this.bombCounter++;
                if (tempRow < field.length - 1 && field[tempRow + 1][tempColumn].equals("* "))
                    this.bombCounter++;
                if (tempRow > 0 && field[tempRow - 1][tempColumn].equals("* "))
                    this.bombCounter++;
                if (tempRow > 0 && tempColumn > 0 && field[tempRow - 1][tempColumn - 1].equals("* "))
                    this.bombCounter++;
                if (tempRow < field.length - 1 && tempColumn < field[0].length - 1 && field[tempRow + 1][tempColumn + 1].equals("* "))
                    this.bombCounter++;
                if (tempRow < field.length - 1 && tempColumn > 0 && field[tempRow + 1][tempColumn - 1].equals("* "))
                    this.bombCounter++;
                if (tempRow > 0 && tempColumn < field[0].length - 1 && field[tempRow - 1][tempColumn + 1].equals("* "))
                    this.bombCounter++;
                this.field[tempRow][tempColumn] = this.bombCounter + " ";
                this.gameField[tempRow][tempColumn] = this.bombCounter + " ";
                this.openingCounter--;
                printLine();
                printMatrix(this.gameField);
                firstRound = false;
            }
        }
    }

    void bombPlaces() {
        Random rand = new Random();
        boolean oneTime = false;
        for (int i = 0; i < this.bombPlaces.length; i++) {
            int bombN1 = rand.nextInt(this.field.length);
            int bombN2 = rand.nextInt(this.field[0].length);
            if (isBombPlaceRepeat(this.bombPlaces, bombN1, bombN2)) {
                this.bombPlaces[i][0] = bombN1;
                this.bombPlaces[i][1] = bombN2;
            } else if (bombN1 == 0 && bombN2 == 0 && !oneTime) {
                this.bombPlaces[i][0] = 0;
                this.bombPlaces[i][1] = 0;
                oneTime = true;
            } else {
                i--;
            }
        }
    }

    boolean isBombPlaceRepeat(int[][] arr, int value, int value2) {
        for (int[] row : arr) {
            if (row[0] == value && row[1] == value2) return false;
        }
        return true;
    }

    void putFlag() {
        while (true) {
            Scanner input = new Scanner(System.in);
            System.out.print("Bayrak Satır : ");
            int a = input.nextInt();
            int row = a - 1;
            System.out.print("Bayrak Sütun : ");
            int b = input.nextInt();
            int column = b - 1;
            if ((row < 0) || (column < 0) || (this.field.length <= row) || (this.field[0].length <= column)) {
                System.out.print("Lütfen Geçerli Değerler Giriniz !!! YENİDENGİR:1*  ÇIKIŞ:2 ==> ");
                int condition = input.nextInt();
                if (condition == 2)
                    break;
            } else if (this.gameField[row][column].equals("P ")) {
                System.out.print("Lütfen Bayrak Olmayan Bir Yer Seçiniz !!! YENİDENGİR:1*  ÇIKIŞ:2 ==> ");
                int condition = input.nextInt();
                if (condition == 2)
                    break;
            } else if (!this.gameField[row][column].equals("- ")) {
                System.out.print("Bu Blok Zaten Açık !!! YENİDENGİR:1*  ÇIKIŞ:2 ==> ");
                int condition = input.nextInt();
                if (condition == 2)
                    break;
            } else {
                this.gameField[row][column] = "P ";
                break;
            }
        }
    }

    void removeFlag() {
        while (true) {
            Scanner input = new Scanner(System.in);
            System.out.print("Bayrak Satır : ");
            int a = input.nextInt();
            int row = a - 1;
            System.out.print("Bayrak Sütun : ");
            int b = input.nextInt();
            int column = b - 1;
            if ((row < 0) || (column < 0) || (this.field.length <= row) || (this.field[0].length <= column)) {
                System.out.println("Lütfen Geçerli Değerler Giriniz !!!");
            } else {
                if (this.gameField[row][column].equals("P ")) {
                    this.gameField[row][column] = "- ";
                    break;
                } else {
                    System.out.print("Seçtiğiniz Blokta Bayrak Yok ! YENİDENGİR:1*  ÇIKIŞ:2 ==> ");
                    int condition = input.nextInt();
                    if (condition == 2) {
                        break;
                    }
                }
            }
        }
    }

    void printMatrix(String[][] arr) {
        for (String[] row : arr) {
            for (String colum : row) {
                System.out.print(colum + " ");
            }
            System.out.println();
        }
    }

    void printLine() {
        System.out.println("====================");
    }

    void winningCondition() {
        if (this.isLose) {
            System.out.println("Mayına Bastınız !!! \nKAYBETTİNİZ !");
        } else {
            System.out.println("Bütün Mayınsız Kareleri Açtınız !!! \nKAZANDINIZ !");
        }
        printLine();
        printMatrix(this.field);
        printLine();
    }
}

