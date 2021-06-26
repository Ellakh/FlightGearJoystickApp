package com.example.myapplication1;

// IOnChange interface - declares the method that will be called when change detected
@FunctionalInterface
public interface IOnChange {
    // move - moves the object given the aileron and elevator parameters
    void move(double aileron, double elevator);
}
