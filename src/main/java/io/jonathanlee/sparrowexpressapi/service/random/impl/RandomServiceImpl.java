package io.jonathanlee.sparrowexpressapi.service.random.impl;

import io.jonathanlee.sparrowexpressapi.constraint.CommonConstraints;
import io.jonathanlee.sparrowexpressapi.service.random.RandomService;
import java.security.SecureRandom;
import java.util.Base64;
import org.springframework.stereotype.Service;

@Service
public class RandomServiceImpl implements RandomService {

  private final SecureRandom secureRandom;

  public RandomServiceImpl() {
    this.secureRandom = new SecureRandom();
  }

  @Override
  public String generateNewId() {
    return this.generateString(CommonConstraints.ID_LENGTH);
  }

  @Override
  public String generateNewTokenValue() {
    return this.generateString(CommonConstraints.TOKEN_LENGTH);
  }

  public String generateString(int length) {
    if (length < 1 || length > CommonConstraints.TOKEN_LENGTH) {
      throw new IllegalArgumentException(String.format("Random generated strings only available of length %d - %d", 1, CommonConstraints.TOKEN_LENGTH));
    }
    byte[] bytes = new byte[length];
    this.secureRandom.nextBytes(bytes);
    return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes).substring(0, length);
  }

}
