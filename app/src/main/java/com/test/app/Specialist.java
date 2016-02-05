package com.test.app;

import java.io.IOException;
import java.io.NotActiveException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by boris on 03.02.16.
 */
public class Specialist implements Serializable {

    private Integer id;
    private String name;
    private String surname;
    private Integer yob;
    private String position;
    private String city;

    public Specialist() {}

    public Specialist(Integer id, String name, String surname, Integer yob, String position, String city) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.yob = yob;
        this.position = position;
        this.city = city;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Integer getYob() {
        return yob;
    }

    public void setYob(Integer yob) {
        this.yob = yob;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Specialist)) return false;

        Specialist that = (Specialist) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (surname != null ? !surname.equals(that.surname) : that.surname != null) return false;
        if (yob != null ? !yob.equals(that.yob) : that.yob != null) return false;
        if (position != null ? !position.equals(that.position) : that.position != null)
            return false;
        return !(city != null ? !city.equals(that.city) : that.city != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (yob != null ? yob.hashCode() : 0);
        result = 31 * result + (position != null ? position.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Specialist{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", yob=" + yob +
                ", position='" + position + '\'' +
                ", city='" + city + '\'' +
                '}';
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
    }

    private void writeObject(ObjectOutputStream ous) throws IOException {
        ous.defaultWriteObject();
    }
}
