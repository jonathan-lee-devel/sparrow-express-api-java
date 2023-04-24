package io.jonathanlee.sparrowexpressapi.util.profile;

public interface ActiveProfileService {

  boolean isLocalActiveProfile();

  boolean isProductionActiveProfile();

}
