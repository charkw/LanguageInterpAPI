package edu.wpi.teamW;

public class ServiceException extends Exception {
  public ServiceException() {
    super("API failed to run");
  }
}
