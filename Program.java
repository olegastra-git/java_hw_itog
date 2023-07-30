package ru.geekbrains.lesson4;

public class Program {
    public static void main(String[] args) {

        HashMap<String, String> hashMap = new HashMap<>();

        hashMap.add("+79005554433", "Андрей");
        hashMap.add("+79005554432", "Алексей");
        hashMap.add("+79005554432", "Дарья1");
        hashMap.add("+79005554433", "Дарья2");
        hashMap.add("+79005554434", "Дарья3");
        hashMap.add("+79005554435", "Дарья4");
        hashMap.add("+79005554436", "Дарья5");
        hashMap.add("+79005554437", "Дарья6");
        hashMap.add("+79005554438", "Дарья7");
        hashMap.add("+79005554439", "Дарья8");

        String res = hashMap.get("+79005554436");

        hashMap.remove("+79005554438");

        for (HashMap.Entity<String, String> element : hashMap) {
            System.out.println(element.key + " - " + element.value);
        }

    }

}
