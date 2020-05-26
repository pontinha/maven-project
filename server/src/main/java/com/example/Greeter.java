package com.example;

/**
 * This is a class.
 */
public class Greeter {

  /**
   * This is a constructor.
   */
  public Greeter() {

  }

  /**
   * A javadoc comment
   * @parameter someone a name
   * @return a greeting
   */
  public final String greet(final String someone) {
    return String.format("Hello, %s!", someone);
  }
}
