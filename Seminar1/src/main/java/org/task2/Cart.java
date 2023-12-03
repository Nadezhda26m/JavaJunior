package org.task2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Корзина
 *
 * @param <T> Еда
 */
public class Cart<T extends Food> {

    /**
     * Товары в магазине
     */
    private final ArrayList<T> foodstuffs;
    private final UMarket market;
    private final Class<T> clazz;

    public Cart(Class<T> clazz, UMarket market) {
        this.clazz = clazz;
        this.market = market;
        foodstuffs = new ArrayList<>();
    }

    public Collection<T> getFoodstuffs() {
        return foodstuffs;
    }

    /**
     * Распечатать список продуктов в корзине
     */
    public void printFoodstuffs() {
        AtomicInteger index = new AtomicInteger(1);
        foodstuffs.forEach(food -> {
            System.out.printf("[%d] %s (Белки: %s Жиры: %s Углеводы: %s)\n",
                    index.getAndIncrement(), food.getName(),
                    food.getProteins() ? "Да" : "Нет",
                    food.getFats() ? "Да" : "Нет",
                    food.getCarbohydrates() ? "Да" : "Нет");
        });
    }

    /**
     * Балансировка корзины
     */
    public void cardBalancing() {
        boolean proteins = foodstuffs.stream().anyMatch(Food::getProteins);
        boolean fats = foodstuffs.stream().anyMatch(Food::getFats);
        boolean carbohydrates = foodstuffs.stream().anyMatch(Food::getCarbohydrates);

        if (proteins && fats && carbohydrates) {
            System.out.println("Корзина уже сбалансирована по БЖУ.");
            return;
        }

        if (checkPresenceAndAddMissing(proteins, thing -> !thing.getProteins())
                && checkPresenceAndAddMissing(fats, thing -> !thing.getFats())
                && checkPresenceAndAddMissing(carbohydrates, thing -> !thing.getCarbohydrates())) {
            System.out.println("Корзина сбалансирована по БЖУ.");
        } else System.out.println("Невозможно сбалансировать корзину по БЖУ.");

        /*boolean proteins = false;
        boolean fats = false;
        boolean carbohydrates = false;

        for (var food : foodstuffs) {
            if (!proteins && food.getProteins())
                proteins = true;
            else if (!fats && food.getFats())
                fats = true;
            else if (!carbohydrates && food.getCarbohydrates())
                carbohydrates = true;
            if (proteins && fats && carbohydrates)
                break; }

        if (proteins && fats && carbohydrates) {
            System.out.println("Корзина уже сбалансирована по БЖУ.");
            return; }

        for (var thing : market.getThings(clazz)) {
            if (!proteins && thing.getProteins()) {
                proteins = true;
                foodstuffs.add(thing);
            } else if (!fats && thing.getFats()) {
                fats = true;
                foodstuffs.add(thing);
            } else if (!carbohydrates && thing.getCarbohydrates()) {
                carbohydrates = true;
                foodstuffs.add(thing); }
            if (proteins && fats && carbohydrates)
                break; }

        if (proteins && fats && carbohydrates)
            System.out.println("Корзина сбалансирована по БЖУ.");
        else System.out.println("Невозможно сбалансировать корзину по БЖУ."); */
    }

    private boolean checkPresenceAndAddMissing(boolean isExist, Predicate<? super T> predicate) {
        if (!isExist) {
            var food = market.getThings(clazz).stream()
                    .dropWhile(predicate).findFirst().orElse(null);
            //      .filter(Food::getProteins).findAny().orElse(null);
            if (food == null) return false;
            foodstuffs.add(food);
        }
        return true;
    }

}
