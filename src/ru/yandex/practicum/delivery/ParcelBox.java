package ru.yandex.practicum.delivery;
import java.util.ArrayList;
import java.util.List;

public class ParcelBox<T extends Parcel> {
    private final List<T> parcels;
    private final int maxWeight;
    private int weightParcel = 0;

    public ParcelBox(int maxWeight) {
        this.maxWeight = maxWeight;
        this.parcels = new ArrayList<>();
    }

    public void addParcel(T parcel) {
        if (weightParcel + parcel.weight <= maxWeight) {
            parcels.add(parcel);
            weightParcel += parcel.weight;
        } else {
            System.out.println("Превышен максимальный вес коробки. Посылка не может быть добавлена.");
        }
    }

    public List<T> getAllParcels() {
        return parcels;
    }
}