package util;

import entity.Car;
import service.CarService;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class MenuUtil {
    static CarService carService = new CarService();

    public static void showMenu() {
        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Select menu" +
                    "\n1.Add a new Car" +
                    "\n2.Show all Cars" +
                    "\n3.Find car by ID" +
                    "\n4.Update Car by Name" +
                    "\n5.Delete car by ID" +
                    "\n6.Exit system");
            int menu = sc.nextInt();
            if (menu == 1) {
                System.out.println("Enter " + " car's" + "\nid: \nName: \nSpeed: \nRelease Date (YYYY-MM-DD): \nEngine: \nColor: ");
                sc = new Scanner(System.in);
                Long id = sc.nextLong();
                sc = new Scanner(System.in);
                String name = sc.nextLine();
                sc = new Scanner(System.in);
                Integer speed = sc.nextInt();
                sc = new Scanner(System.in);
                LocalDate release_date = LocalDate.parse(sc.nextLine());
                sc = new Scanner(System.in);
                String engine = sc.nextLine();
                String color = sc.nextLine();
                carService.createCar(new Car(id, name, speed, release_date, engine, color));
                System.out.println();
            } else if (menu == 2) {
                List<Car> cars = carService.getAllCars();
                Collections.sort(cars);
                cars
                        .stream()
                        .forEach(car -> System.out.println(car));
                System.out.println();
            } else if (menu == 3) {
                System.out.println("Enter car's id which you want to find ?");
                sc = new Scanner(System.in);
                Long id = sc.nextLong();
                carService.getCarById(id);
                System.out.println();
            } else if (menu == 4) {
                System.out.println("Enter car's id which you want to update");
                sc = new Scanner(System.in);
                Long id = sc.nextLong();
                System.out.println("Enter new name: ");
                sc = new Scanner(System.in);
                String name = sc.nextLine();
                carService.updateCarName(id, name);
                System.out.println();
            } else if (menu == 5) {
                System.out.println("Enter car's id which you want to delete ?");
                sc = new Scanner(System.in);
                Long id = sc.nextLong();
                carService.deleteCar(id);
                System.out.println();
            } else if (menu == 6) {
                System.exit(0);
                break;
            }
        }
    }
}
