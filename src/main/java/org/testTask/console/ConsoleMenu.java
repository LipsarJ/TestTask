package org.testTask.console;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.testTask.dto.request.RequestOrderDTO;
import org.testTask.dto.request.RequestProductDTO;
import org.testTask.dto.response.ResponseOrderDTO;
import org.testTask.dto.response.ResponseProductDTO;
import org.testTask.entity.Product;
import org.testTask.service.OrderService;
import org.testTask.service.ProductService;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ConsoleMenu implements CommandLineRunner {

    private final ProductService productService;

    private final OrderService orderService;

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void run(String... args) {
        while (true) {
            showMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> addProduct();
                case 2 -> createOrder();
                case 3 -> showOrdersByCustomer();
                case 4 -> exportOrdersToJSON();
                case 5 -> exit();
                default -> System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }

    private void showMenu() {
        System.out.println("""
                Выберите действие:
                1 - Добавить продукт
                2 - Создать заказ
                3 - Вывести заказы по имени клиента
                4 - Экспортировать заказы в JSON
                5 - Выход
                """);
        System.out.print("Ваш выбор: ");
    }

    private void addProduct() {
        System.out.print("Введите название продукта: ");
        String name = scanner.nextLine();

        BigDecimal price = null;
        while (true) {
            System.out.print("Введите цену продукта: ");
            String priceInput = scanner.nextLine();

            if (priceInput.isBlank()) {
                System.out.println("Ошибка: поле цены не может быть пустым. Повторите ввод.");
                continue;
            }

            try {
                price = new BigDecimal(priceInput);
                if (price.compareTo(BigDecimal.ZERO) <= 0) {
                    System.out.println("Ошибка: цена должна быть числом больше нуля. Повторите ввод.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите корректное число.");
            }
        }

        RequestProductDTO requestProductDTO = new RequestProductDTO(name, price);
        try {
            ResponseProductDTO responseProductDTO = productService.addProduct(requestProductDTO);
            System.out.println("Продукт " + responseProductDTO + " добавлен.");
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка при добавлении продукта: " + e.getMessage());
        }
    }


    private void createOrder() {
        System.out.print("Введите имя клиента: ");
        String customerName = scanner.nextLine();

        System.out.print("Введите имена продуктов через запятую: ");
        String productNamesInput = scanner.nextLine();
        String[] productNames = productNamesInput.split(",");

        Set<Product> products = new HashSet<>();
        for (String productName : productNames) {
            Product product = productService.findProductByName(productName.toLowerCase());
            if (product != null) {
                products.add(product);
            } else if (!productName.isEmpty()) {
                System.out.println("Продукт с именем " + productName + " не найден и будет пропущен.");
            }
        }


        RequestOrderDTO requestOrderDTO = new RequestOrderDTO();
        requestOrderDTO.setCostumerName(customerName);
        requestOrderDTO.setProducts(products);

        try {
            ResponseOrderDTO responseOrderDTO = orderService.addNewOrder(requestOrderDTO);
            System.out.println("Заказ создан успешно: " + responseOrderDTO);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void showOrdersByCustomer() {
        System.out.print("Введите имя клиента: ");
        String customerName = scanner.nextLine();

        var orders = orderService.getAllOrdersForCostumer(customerName.toLowerCase());
        if (orders.isEmpty()) {
            System.out.println("Заказов не найдено.");
        } else {
            orders.forEach(order -> System.out.println(order));
        }
    }

    private void exportOrdersToJSON() {
        System.out.print("Введите путь для экспорта: ");
        String filePath = scanner.nextLine();

        try {
            orderService.exportOrdersToJson(filePath);
            System.out.println("Заказы экспортированы в JSON.");
        } catch (Exception e) {
            System.out.println("Ошибка при экспорте: " + e.getMessage());
        }
    }

    private void exit() {
        System.out.println("Завершение работы...");
        System.exit(0);
    }
}
