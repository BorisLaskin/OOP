package TreePack;

public class Prog {
    public static void main(String[] args) {
        Tree<Integer> mineTree = new Tree<>();
        mineTree.addValue(2);
        mineTree.addValue(4);
        mineTree.Print("Добавили элемент со значением 4");
        mineTree.addValue(3);
        mineTree.Print("Формат вывода следующий: ValueColor - Height");
        mineTree.addValue(6);
        mineTree.Print("'____' null Node, или проще говоря заполнение пустоты картинки");
        mineTree.addValue(1);
        System.out.println("Проверка включения числа '4' -" +mineTree.contains(4));
        System.out.println("Аналогично - 6 -" + mineTree.contains(6));
        System.out.println("Аналогично - 7 -" + mineTree.contains(7));
        mineTree.Print("Как видим дерево балансируется и рядом с каждым значение выведена черная высота");

    }
}
