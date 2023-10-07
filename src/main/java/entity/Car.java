package entity;

import java.time.LocalDate;

public class Car implements Comparable<Car> {
    private Long id;
    private String name;
    private Integer speed;
    private LocalDate release_date;
    private String engine;
    private String color;

    public Car(Long id, String name, Integer speed, LocalDate release_date, String engine, String color) {
        this.id = id;
        this.name = name;
        this.speed = speed;
        this.release_date = release_date;
        this.engine = engine;
        this.color = color;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getSpeed() {
        return speed;
    }

    public LocalDate getRelease_date() {
        return release_date;
    }

    public String getEngine() {
        return engine;
    }

    public String getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", speed=" + speed +
                ", release_date=" + release_date +
                ", engine='" + engine + '\'' +
                ", color='" + color + '\'' +
                '}';
    }

    @Override
    public int compareTo(Car o) {
        return (int) (this.id - o.id);
    }
}

