package com.friendsbook;

public class Friend {
    private String name;
    private int age;
    private String email;
    private String phoneNumber;

    //requires: name != null, age > 0, email != null, phoneNumber != null
    //modifies: this
    //effects: creates a new Friend with the given parameters
    public Friend(String name, int age, String email, String phoneNumber) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    // Getters and setters with appropriate comments
    //requires: nothing
    //modifies: nothing
    //effects: returns the friend's name
    public String getName() { return name; }

    //requires: nothing
    //modifies: nothing
    //effects: returns the friend's age
    public int getAge() { return age; }

    //requires: nothing
    //modifies: nothing
    //effects: returns the friend's email
    public String getEmail() { return email; }

    //requires: nothing
    //modifies: nothing
    //effects: returns the friend's phone number
    public String getPhoneNumber() { return phoneNumber; }

    @Override
    public String toString() {
        return name + " (Age: " + age + ")";
    }
} 