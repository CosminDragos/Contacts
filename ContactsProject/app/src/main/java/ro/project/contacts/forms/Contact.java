package ro.project.contacts.forms;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by cosmin on 18.09.2017.
 */

public class Contact {

    @SerializedName("gender")
    @Expose
    private String gender;

    @SerializedName("name")
    @Expose
    private Name name;

    @SerializedName("location")
    @Expose
    private Location location;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("login")
    @Expose
    private Login login;

    @SerializedName("dob")
    @Expose
    private String dob;

    @SerializedName("registered")
    @Expose
    private String registered;

    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("cell")
    @Expose
    private String cell;

    @SerializedName("id")
    @Expose
    private Id id;

    @SerializedName("picture")
    @Expose
    private Picture picture;

    @SerializedName("nat")
    @Expose
    private String nat;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getRegistered() {
        return registered;
    }

    public void setRegistered(String registered) {
        this.registered = registered;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public String getNat() {
        return nat;
    }

    public void setNat(String nat) {
        this.nat = nat;
    }

    public String getAge() throws ParseException {
        int age;
        final Calendar calenderToday = Calendar.getInstance();
        int currentYear = calenderToday.get(Calendar.YEAR);
        int currentMonth = 1 + calenderToday.get(Calendar.MONTH);
        int todayDay = calenderToday.get(Calendar.DAY_OF_MONTH);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = dateFormat.parse(dob);

        age = currentYear - (date.getYear() + 1900);

        if(date.getMonth() > currentMonth){
            --age;
        }
        else if(date.getMonth() == currentMonth){
            if(date.getDay() > todayDay){
                --age;
            }
        }

        return String.valueOf(age);
    }

    public String getTimeDateRegistered() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = dateFormat.parse(registered);

        SimpleDateFormat dateFormatToPrint = new SimpleDateFormat("HH:mm");

        return dateFormatToPrint.format(date.getTime());
    }
}
