package io.jonathanlee.sparrowexpressapi.constraint;

public abstract class CommonConstraints {

  public static final int ID_LENGTH = 32;

  public static final int TOKEN_LENGTH = 64;

  public static final int MIN_TITLE_LENGTH = 1;

  public static final int MAX_TITLE_LENGTH = 100;

  private CommonConstraints() {
    // Private constructor to hide the implicit public constructor
  }

}
