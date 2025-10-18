package ru.yandex.practicum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.delivery.FragileParcel;
import ru.yandex.practicum.delivery.ParcelBox;
import ru.yandex.practicum.delivery.PerishableParcel;
import ru.yandex.practicum.delivery.StandardParcel;

public class DeliveryAppTest {
    private StandardParcel standardParcel;
    private FragileParcel fragileParcel;
    private PerishableParcel perishableParcel;

    @BeforeEach
    void setUp() {
        standardParcel = new StandardParcel("Описание стандартной посылки", 10,
                "Адрес доставки", 1);
        fragileParcel = new FragileParcel("Описание хрупкой посылки", 9,
                "Адрес доставки", 2);
        perishableParcel = new PerishableParcel("Описание скоропортящейся посылки", 8,
                "Адрес доставки", 3, 5);
    }

    @Test
    void testCalculateDeliveryCost() {
        int price = standardParcel.calculateDeliveryCost();
        Assertions.assertEquals(20, price);
        price = fragileParcel.calculateDeliveryCost();
        Assertions.assertEquals(36, price);
        price = perishableParcel.calculateDeliveryCost();
        Assertions.assertEquals(24, price);
    }

    @Test
    void shouldBePositiveIsExpired() {
        Assertions.assertFalse(perishableParcel.isExpired(3));
        Assertions.assertFalse(perishableParcel.isExpired(8));

    }

    @Test
    void shouldBeNegativeIsExpired() {
        Assertions.assertTrue(perishableParcel.isExpired(9));
    }

    @Test
    void testAddParcelInBox() {
        ParcelBox<StandardParcel> standardParcelBox = new ParcelBox<>(100);
        ParcelBox<FragileParcel> fragileParcelBox = new ParcelBox<>(80);
        ParcelBox<PerishableParcel> perishableParcelBox = new ParcelBox<>(60);

        standardParcelBox.addParcel(standardParcel);
        fragileParcelBox.addParcel(fragileParcel);
        perishableParcelBox.addParcel(perishableParcel);

        Assertions.assertTrue(standardParcelBox.getAllParcels().contains(standardParcel));
        Assertions.assertTrue(fragileParcelBox.getAllParcels().contains(fragileParcel));
        Assertions.assertTrue(perishableParcelBox.getAllParcels().contains(perishableParcel));
    }

    @Test
    void testAddParcelMaxWeight() {
        ParcelBox<StandardParcel> standardParcelBoxMaxWeight = new ParcelBox<>(10);
        ParcelBox<FragileParcel> fragileParcelBoxMaxWeight = new ParcelBox<>(11);
        ParcelBox<PerishableParcel> perishableParcelBoxMaxWeight = new ParcelBox<>(12);
        StandardParcel standardParcelMaxWeight = new StandardParcel("Максимальная посылка",
                15, "Адрес доставки", 1);
        FragileParcel fragileParcelMaxWeight = new FragileParcel("Максимальная посылка",
                20, "Адрес доставки", 1);
        PerishableParcel perishableParcelMaxWeight = new PerishableParcel("Максимальная посылка",
                20, "Адрес доставки", 1, 10);

        standardParcelBoxMaxWeight.addParcel(standardParcelMaxWeight);
        Assertions.assertFalse(standardParcelBoxMaxWeight.getAllParcels().contains(standardParcelMaxWeight));
        fragileParcelBoxMaxWeight.addParcel(fragileParcelMaxWeight);
        Assertions.assertFalse(fragileParcelBoxMaxWeight.getAllParcels().contains(fragileParcelMaxWeight));
        perishableParcelBoxMaxWeight.addParcel(perishableParcelMaxWeight);
        Assertions.assertFalse(perishableParcelBoxMaxWeight.getAllParcels().contains(perishableParcelMaxWeight));
    }
}
