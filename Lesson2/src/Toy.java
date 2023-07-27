import java.util.List;
import java.util.Queue;
import java.util.Random;


public class Toy{
    private static int counter = 0;
    private static int sumWeight = 0;
    private final int id;
    private String name;
    private int quantity;
    private int weight;


    public Toy(String name, int quantity, int weight) {
        this.id = ++counter;
        this.name = name;
        this.quantity = quantity;
        this.weight = weight;
        sumWeight += quantity * weight;
    }

    public Toy(int id, String name, int quantity, int weight) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.weight = weight;
    }

    public static int getSumWeight() {
        return sumWeight;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        sumWeight += (weight - this.weight) * quantity;
        this.weight = weight;
    }

    private void decreaseQuantity(){
        quantity--;
        sumWeight -= weight;
    }

    @Override
    public String toString() {
        return String.format("ID %d; Name - %s; Quantity - %d, Weight - %d", id, name, quantity, weight);
    }

    public static Toy get(List<Toy> toyList){
        Random rn = new Random();
        int randomWeight = rn.nextInt(sumWeight + 1);
        System.out.println("Рандомное значение " + randomWeight);
        int tempSumWeight = 0;
        for (Toy toy : toyList) {
            tempSumWeight += toy.getWeight() * toy.getQuantity();
//            System.out.println(toys);
//            System.out.printf("tempSumWeight = %d; randomWeight = %d; sumWeight = %d\n", tempSumWeight, randomWeight, sumWeight);
            if(tempSumWeight >= randomWeight){
                toy.decreaseQuantity();
                return toy;
            }
        }
        return null;
    }

}
