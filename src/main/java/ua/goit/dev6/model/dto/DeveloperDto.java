package ua.goit.dev6.model.dto;

import java.util.Objects;

public class DeveloperDto {
    long id;
    String first_name;
    String last_name;
    int age;
    int salary;

    public DeveloperDto() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DeveloperDto)) return false;
        DeveloperDto that = (DeveloperDto) o;
        return id == that.id && age == that.age && salary == that.salary && Objects.equals(first_name, that.first_name)
                && Objects.equals(last_name, that.last_name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, first_name, last_name, age, salary);
    }

    @Override
    public String toString() {
        return "DeveloperDto{" +
                "id=" + id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", age=" + age +
                ", salary=" + salary +
                '}';
    }
}

