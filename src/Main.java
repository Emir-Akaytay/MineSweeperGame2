import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Başlangıç Ayarları ========== ");
        while(true) {
            Scanner input = new Scanner(System.in);
            System.out.print("Satır Sayısını Belirleyin : ");
            int a = input.nextInt();
            System.out.print("Sütun Sayısını Belirleyin : ");
            int b = input.nextInt();
            MineSweeper mine = new MineSweeper(a, b);
            if (5 <= a * b) {
                System.out.println("Bomba Sayısı : " + mine.bombTotal);
                System.out.println("NOT : Turu Pas Geçmek İsterseniz  Satır:0 Sütun:0  Giriniz !!!");
                System.out.println("NOT : İlk Turdan Sonra Bayrak Eklenebilir !!!");
                mine.printLine();
                mine.run();
                break;
            } else {
                System.out.println("Lütfen En Az 5 Birimkare (5 < Satır * Sütun) Bir Alan Girin !!!");
                mine.printLine();
            }
        }
    }
}
