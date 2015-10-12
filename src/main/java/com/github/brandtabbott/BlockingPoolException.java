package com.github.brandtabbott;

/* Caught exception for provisioning */
public class BlockingPoolException extends RuntimeException {

  private static final long serialVersionUID = 6589942387653952899L;

  public BlockingPoolException() {
    super();
  }

  public BlockingPoolException(String message) {
    super(message);
  }

  public BlockingPoolException(Throwable cause) {
    super(cause);
  }

  public BlockingPoolException(String message, Throwable cause) {
    super(message, cause);
  }

  public BlockingPoolException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
