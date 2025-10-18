package ru.yandex.practicum.delivery;

public class PerishableParcel extends Parcel {
    private final int timeToLive;

    public PerishableParcel(String description, int weight, String deliveryAddress, int sendDay, int timeToLive) {
        super(description, weight, deliveryAddress, sendDay);
        this.timeToLive = timeToLive;
    }

    public boolean isExpired(int currentDay) {
        if ((sendDay + timeToLive) >= currentDay) {
            return false;
        }
        return true;
    }

    @Override
    public int calculateDeliveryCost() {
        int costOfOneUnit = 3;
        return costOfOneUnit * weight;
    }
}
