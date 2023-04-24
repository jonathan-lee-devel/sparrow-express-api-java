package io.jonathanlee.sparrowexpressapi.util.profile.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;

class ActiveProfileServiceImplTest {

  private ActiveProfileServiceImpl activeProfileService;

  @Mock
  private Environment environment;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    activeProfileService = new ActiveProfileServiceImpl(environment);
  }

  @Test
  void isLocalActiveProfile_WhenLocalProfileIsActive_ReturnsTrue() {
    when(environment.getActiveProfiles()).thenReturn(new String[]{"local", "dev"});
    assertTrue(activeProfileService.isLocalActiveProfile());
  }

  @Test
  void isLocalActiveProfile_WhenLocalProfileIsNotActive_ReturnsFalse() {
    when(environment.getActiveProfiles()).thenReturn(new String[]{"dev", "qa"});
    assertFalse(activeProfileService.isLocalActiveProfile());
  }

  @Test
  void isProductionActiveProfile_WhenProductionProfileIsActive_ReturnsTrue() {
    when(environment.getActiveProfiles()).thenReturn(new String[]{"production", "qa"});
    assertTrue(activeProfileService.isProductionActiveProfile());
  }

  @Test
  void isProductionActiveProfile_WhenProductionProfileIsNotActive_ReturnsFalse() {
    when(environment.getActiveProfiles()).thenReturn(new String[]{"local", "dev"});
    assertFalse(activeProfileService.isProductionActiveProfile());
  }

  @Test
  void containsActiveProfile_WhenProfileIsActive_ReturnsTrue() {
    when(environment.getActiveProfiles()).thenReturn(new String[]{"local", "dev", "qa"});
    assertTrue(activeProfileService.containsActiveProfile("qa"));
  }

  @Test
  void containsActiveProfile_WhenProfileIsNotActive_ReturnsFalse() {
    when(environment.getActiveProfiles()).thenReturn(new String[]{"local", "dev"});
    assertFalse(activeProfileService.containsActiveProfile("qa"));
  }

}
