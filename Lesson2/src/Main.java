import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Queue<Toy> toyList = new PriorityQueue<>();
        createRandomToys(toyList);
        boolean runnable = true;
        Scanner scanner = new Scanner(System.in);
        while (runnable) {
            showToysList(toyList);
            System.out.println("Выберете, что вы хотите сделать:");
            System.out.println("1 - Добавить игрушек");
            System.out.println("2 - Изменить вес игрушек");
            System.out.println("3 - Вытянуть игрушку из автомата");
            System.out.println("0 - Выход");
            int customerChoiceInt = scanner.nextInt();
            switch (customerChoiceInt) {
                case 1 -> addToy(toyList, scanner);
                case 2 -> editWeightToy(toyList, scanner);
                case 3 -> {
                    if (toyList.size() > 0) {
                        System.out.println(getToy(toyList));
                    } else System.out.println("Автомат пуст");
                }
                case 0 -> runnable = false;
                default -> System.out.println("Введите корректную цифру");
            }
        }
    }

    private static void editWeightToy(Queue<Toy> toyList, Scanner scanner) {
        System.out.println("Введите ID игрушки");
        int toyId = scanner.nextInt();
        if (toyId >= 0 && toyId < toyList.size()) {
            Toy toyToEdit = toyList.stream()
                    .filter(t -> t.getId() == toyId)
                    .findAny().orElse(null);
            System.out.println("Введите вес игрушки");
            int toyWeight = scanner.nextInt();
            if (toyToEdit != null) {
                toyToEdit.setWeight(toyWeight);
            }
        } else {
            System.out.printf("Игрушек с ID %d не найдено\n", toyId);
        }
    }

    private static void addToy(Queue<Toy> toyList, Scanner scanner) {
        System.out.println("Введите имя игрушки");
        String toyName = scanner.next();
        System.out.println("Введите количство");
        int toyQuantity = scanner.nextInt();
        System.out.println("Введите вес");
        int toyWeight = scanner.nextInt();
        toyList.add(new Toy(toyName, toyQuantity, toyWeight));
    }


    private static Toy getToy(Queue<Toy> toyList) {
        Toy toy = Toy.get(toyList);
        if (toy == null) throw new NullPointerException("Ошибка!");
        if (toy.getQuantity() == 0) {
            toyList.remove(toy);
        }
        return toy;
    }

    private static void showToysList(Queue<Toy> toyList) {
        System.out.println("------------------------------");
        if (toyList.size() > 0) {
            for (Toy toy : toyList) {
                System.out.println(toy);
            }
        } else System.out.println("Пусто");
        System.out.println("------------------------------");
    }

    private static void createRandomToys(Queue<Toy> toyList) {
        String[] typesOfToys = {"Конструктор", "Кукла", "Робот"};
        Random rn = new Random();
        for (String toyName : typesOfToys) {
            toyList.add(new Toy(toyName, rn.nextInt(1, 5), rn.nextInt(1, 7)));
        }
    }
}