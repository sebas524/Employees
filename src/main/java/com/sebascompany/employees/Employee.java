package com.sebascompany.employees;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Employee {


    protected final DateTimeFormatter myDTF = DateTimeFormatter.ofPattern("M/d/yyyy");
    protected final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
    private final String peopleRegex = "(?<lastName>\\w+),\\s*(?<firstName>\\w+),\\s*(?<dob>\\d{1,2}/\\d{1,2}/\\d{4}),\\s*(?<role>\\w+)(?:,\\s*\\{(?<details>.*)\\})?\\n";
    protected final Pattern peoplePat = Pattern.compile(peopleRegex);
    protected final Matcher peopleMat;
    protected String lastName;
    protected String firstName;
    protected LocalDate dob;

    public Employee(String personText) {

        peopleMat = peoplePat.matcher(personText);
        if (peopleMat.find()) {
            this.lastName = peopleMat.group("lastName");
            this.firstName = peopleMat.group("firstName");
            this.dob = LocalDate.from(myDTF.parse(peopleMat.group("dob")));
        }
    }

    public abstract int getSalary();


    public double getBonus() {
        return getSalary() * 1.10;
    }


    @Override
    public String toString() {
        return String.format("%s, %s: %s - %s", this.lastName, this.firstName, this.currencyFormat.format(getSalary()), this.currencyFormat.format(getBonus()));
    }
}
