package ru.yandex.practicum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("Проверяет корректность вычисления стоимости посылок для каждого типа.")
    void calculateDeliveryCost_forAllTypeParcels_expectedCostsAreCorrect() {
        int priceStandard = standardParcel.calculateDeliveryCost();
        int priceFragile = fragileParcel.calculateDeliveryCost();
        int pricePerishable = perishableParcel.calculateDeliveryCost();

        Assertions.assertEquals(20, priceStandard);
        Assertions.assertEquals(36, priceFragile);
        Assertions.assertEquals(24, pricePerishable);
    }

    @Test
    @DisplayName("Проверяет испортилась ли посылка, если срок годности не истек.")
    void isExpired_withinShelfLife_returnsFalse() {
        Assertions.assertFalse(perishableParcel.isExpired(3));
        Assertions.assertFalse(perishableParcel.isExpired(8));

    } // в данных двух тестах, которые проверяют метод isExpired не могу придумать
    // как использовать концепцию given-when-then

    @Test
    @DisplayName("Проверяет испортилась ли посылка, если срок годности истек.")
    void isExpired_afterShelfLife_returnsTrue() {
        Assertions.assertTrue(perishableParcel.isExpired(9));
    }

    @Test
    @DisplayName("Проверяет добавление посылок в коробки каждого типа.")
    void addParcel_successfulAddition_parcelIsInBox() {
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
    @DisplayName("Проверяет невозможность добавления посылок в коробки любого типа, если их вес превышает допустимый.")
    void addParcel_exceedingMaxWeight_parcelNotAdded() {
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
