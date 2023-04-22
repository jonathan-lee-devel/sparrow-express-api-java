package io.jonathanlee.sparrowexpressapi.random.impl;

import io.jonathanlee.sparrowexpressapi.constraint.CommonConstraints;
import io.jonathanlee.sparrowexpressapi.service.random.impl.RandomServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RandomServiceImplTest {

  private RandomServiceImpl randomService;

  @BeforeEach
  public void setUp() {
    this.randomService = new RandomServiceImpl();
  }

  @Test
  void generateNewId_shouldGenerateRandomStringOfCorrectLength() {
    String randomId = this.randomService.generateNewId();
    Assertions.assertNotNull(randomId);
    Assertions.assertEquals(CommonConstraints.ID_LENGTH, randomId.length());
  }

  @Test
  void generateNewTokenValue_shouldGenerateRandomStringOfCorrectLength() {
    String randomToken = this.randomService.generateNewTokenValue();
    Assertions.assertNotNull(randomToken);
    Assertions.assertEquals(CommonConstraints.TOKEN_LENGTH, randomToken.length());
  }

  @Test
  void generateString_shouldThrowIllegalArgumentExceptionForInvalidLength() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> this.randomService.generateString(0));
    Assertions.assertThrows(IllegalArgumentException.class, () -> this.randomService.generateString(CommonConstraints.TOKEN_LENGTH + 1));
  }
}
