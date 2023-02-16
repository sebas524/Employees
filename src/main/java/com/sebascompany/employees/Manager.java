package com.sebascompany.employees;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Manager implements Employee {

    private String lastName;
    private String firstName;
    private LocalDate dob;
    private int orgSize = 0;
    private int directReports = 0;


    private final String managerRegex = "\\w+=(?<orgSize>\\w+),\\w+=(?<dr>\\w+)";
    private final Pattern managerPat = Pattern.compile(managerRegex);
    private final String peopleRegex = "(?<lastName>\\w+),\\s*(?<firstName>\\w+),\\s*(?<dob>\\d{1,2}/\\d{1,2}/\\d{4}),\\s*(?<role>\\w+)(?:,\\s*\\{(?<details>.*)\\})?\\n";
    private final Pattern peoplePat = Pattern.compile(peopleRegex);
    private final DateTimeFormatter myDTF = DateTimeFormatter.ofPattern("M/d/yyyy");
    private final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();


    public Manager(String personText) {

        Matcher peopleMat = peoplePat.matcher(personText);
        if (peopleMat.find()) {
            this.lastName = peopleMat.group("lastName");
            this.firstName = peopleMat.group("firstName");
            this.dob = LocalDate.from(myDTF.parse(peopleMat.group("dob")));
            Matcher managerMat = managerPat.matcher(peopleMat.group("details"));
            if (managerMat.find()) {
                this.orgSize = Integer.parseInt(managerMat.group("orgSize"));
                this.directReports = Integer.parseInt(managerMat.group("dr"));
            }
        }
    }

    public int getSalary() {
        return 3500 + this.orgSize * this.directReports;
    }

    @Override
    public String toString() {
        return String.format("%s, %s: %s", this.lastName, this.firstName, this.currencyFormat.format(getSalary()));
    }
}
