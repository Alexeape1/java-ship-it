package ru.yandex.practicum.delivery;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class DeliveryApp {

    private static final Scanner scanner = new Scanner(System.in);
    private static List<Parcel> allParcels = new ArrayList<>();
    private static List<Trackable> trackableParcels = new ArrayList<>();
    private static ParcelBox<StandardParcel> standardParcelBox = new ParcelBox<>(100);
    private static ParcelBox<FragileParcel> fragileParcelBox = new ParcelBox<>(80);
    private static ParcelBox<PerishableParcel> perishableParcelBox = new ParcelBox<>(60);

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            showMenu();
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    addParcel();
                    break;
                case 2:
                    sendParcels();
                    break;
                case 3:
                    calculateCosts();
                    break;
                case 4:
                    if (fragileParcelBox.getAllParcels().isEmpty()) {
                        System.out.println("Услуга недоступна для данных типов посылок");
                    } else {
                        System.out.println("Введите новое местоположение:");
                        String newLocation = scanner.nextLine();
                        for (Trackable parcel : trackableParcels) {
                            parcel.reportStatus(newLocation);
                        }
                    }
                    break;
                case 5:
                    System.out.println("Выберите тип коробки:");
                    System.out.println("1 — Стандартная");
                    System.out.println("2 — Хрупкая");
                    System.out.println("3 — Скоропортящаяся");
                    int boxType = scanner.nextInt();
                    scanner.nextLine();

                    switch (boxType) {
                        case 1:
                            if (standardParcelBox.getAllParcels().isEmpty()) {
                                System.out.println("Коробка пуста");
                            } else {
                                for (StandardParcel parcel : standardParcelBox.getAllParcels()) {

                                    System.out.println("Описание: " + parcel.description + ", Вес: " + parcel.weight
                                            + ", Адрес доставки: " + parcel.deliveryAddress + ", День отправки: "
                                            + parcel.sendDay);
                                }
                            }
                            break;
                        case 2:
                            if (fragileParcelBox.getAllParcels().isEmpty()) {
                                System.out.println("Коробка пуста");
                            } else {
                                for (FragileParcel parcel : fragileParcelBox.getAllParcels()) {

                                    System.out.println("Описание: " + parcel.description + ", Вес: " + parcel.weight
                                            + ", Адрес доставки: " + parcel.deliveryAddress + ", День отправки: "
                                            + parcel.sendDay);
                                }
                            }

                            break;
                        case 3:
                            if (perishableParcelBox.getAllParcels().isEmpty()) {
                                System.out.println("Коробка пуста");
                            } else {
                                for (PerishableParcel parcel : perishableParcelBox.getAllParcels()) {

                                    System.out.println("Описание: " + parcel.description + ", Вес: " + parcel.weight
                                            + ", Адрес доставки: " + parcel.deliveryAddress + ", День отправки: "
                                            + parcel.sendDay);
                                }
                            }
                            break;
                        default:
                            System.out.println("Неверный выбор.");
                    }
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Неверный выбор.");
            }
        }
    }

    private static void showMenu() {
        System.out.println("Выберите действие:");
        System.out.println("1 — Добавить посылку");
        System.out.println("2 — Отправить все посылки");
        System.out.println("3 — Посчитать стоимость доставки");
        System.out.println("4 - Ввести новый адрес доставки посылки");
        System.out.println("5 - Показать содержимое коробки");
        System.out.println("0 — Завершить");
    }

    private static void addParcel() {
        System.out.println("Введите описание посылки:");
        String description = scanner.nextLine();
        System.out.println("Введите вес посылки:");
        int weight = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Введите адрес доставки:");
        String deliveryAddress = scanner.nextLine();
        System.out.println("Введите день отправки:");
        int sendDay = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Выберите какой тип посылки хотите добавить:");
        System.out.println("1 — Стандартная");
        System.out.println("2 — Хрупкая");
        System.out.println("3 — Скоропортящаяся");

        int parcelType = scanner.nextInt();
        scanner.nextLine();
        if (parcelType == 1) {
            StandardParcel standardParcel = new StandardParcel(description, weight, deliveryAddress, sendDay);
            standardParcelBox.addParcel(standardParcel);
            if (standardParcelBox.getAllParcels().contains(standardParcel)) { // Проверяем, была ли посылка успешно добавлена в коробку
                allParcels.add(standardParcel);
            }
        } else if (parcelType == 2) {
            FragileParcel fragileParcel = new FragileParcel(description, weight, deliveryAddress, sendDay);
            fragileParcelBox.addParcel(fragileParcel);
            if (fragileParcelBox.getAllParcels().contains(fragileParcel)) {
                allParcels.add(fragileParcel);
                trackableParcels.add(fragileParcel);
            }
        } else if (parcelType == 3) {
            System.out.println("Введите количество дней, за которое посылка не испортится:");
            int timeToLive = scanner.nextInt();
            scanner.nextLine();
            PerishableParcel perishableParcel = new PerishableParcel(description, weight, deliveryAddress, sendDay,
                    timeToLive);
            perishableParcelBox.addParcel(perishableParcel);
            if (perishableParcelBox.getAllParcels().contains(perishableParcel)) {
                allParcels.add(perishableParcel);
            }
        }
    }

    private static void sendParcels() {
        if (allParcels.isEmpty()) {
            System.out.println("Посылок для отправки нет");
        } else {
            System.out.println("Введите текущую дату: ");
            int currentDay = scanner.nextInt();
            scanner.nextLine();
            for (Parcel parcel : allParcels) {
                if (parcel instanceof PerishableParcel) {
                    PerishableParcel perishableParcel = (PerishableParcel) parcel;
                    if (perishableParcel.isExpired(currentDay)) {
                        System.out.println("Посылка " + perishableParcel.description + " испортилась");
                    } else {
                        parcel.packageItem();
                        parcel.deliver();
                    }
                } else {
                    parcel.packageItem();
                    parcel.deliver();
                }
            }
        }
    }

    private static void calculateCosts() {
        int totalCost = 0;
        for (Parcel parcel : allParcels) {
            totalCost = totalCost + parcel.calculateDeliveryCost();
        }
        System.out.println("Общая стоимость доставки: " + totalCost);
    }
}