import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<Toy> toyList = new ArrayList<>();
        createRandomToys(toyList);
        boolean runnable = true;
        Scanner scanner = new Scanner(System.in);
        while (runnable) {
            showToysList(toyList);
            System.out.println("Выберете, что вы хотите сделать:");
            System.out.println("1 - Добавить игрушек");
            System.out.println("2 - Изменить вес игрушек");
            System.out.println("3 - Вытянуть игрушку из автомата");
            System.out.println("4 - Прочитать данные из файла");
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
                case 4 -> showDataFromJson();
                case 0 -> runnable = false;
                default -> System.out.println("Введите корректную цифру");
            }
        }
    }

    private static void showDataFromJson() {
        List<Toy> toyList = ManageFile.readFile();
        if (toyList != null){
            showToysList(toyList);
        } else System.out.println("Файл пуст или не найден");
    }

    private static void editWeightToy(List<Toy> toyList, Scanner scanner) {
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

    private static void addToy(List<Toy> toyList, Scanner scanner) {
        System.out.println("Введите имя игрушки");
        String toyName = scanner.next();
        System.out.println("Введите количство");
        int toyQuantity = scanner.nextInt();
        System.out.println("Введите вес");
        int toyWeight = scanner.nextInt();
        toyList.add(new Toy(toyName, toyQuantity, toyWeight));
    }


    private static Toy getToy(List<Toy> toyList) {
        Toy toy = Toy.get(toyList);
        if (toy == null) throw new NullPointerException("Ошибка!");
        ManageFile.saveToJson(toy);
        if (toy.getQuantity() == 0) {
            toyList.remove(toy);
        }
        return toy;
    }

    private static void showToysList(List<Toy> toyList) {
        System.out.println("------------------------------");
        if (toyList.size() > 0) {
            for (Toy toy : toyList) {
                System.out.println(toy);
            }
        } else System.out.println("Пусто");
        System.out.println("------------------------------");
    }

    private static void createRandomToys(List<Toy> toyList) {
        String[] typesOfToys = {"Конструктор", "Кукла", "Робот"};
        Random rn = new Random();
        for (String toyName : typesOfToys) {
            toyList.add(new Toy(toyName, rn.nextInt(1, 5), rn.nextInt(1, 7)));
        }
    }
}