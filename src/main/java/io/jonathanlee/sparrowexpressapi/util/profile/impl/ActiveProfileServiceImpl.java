package io.jonathanlee.sparrowexpressapi.util.profile.impl;

import io.jonathanlee.sparrowexpressapi.util.profile.ActiveProfileService;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActiveProfileServiceImpl implements ActiveProfileService {

  private final Environment environment;

  @Override
  public boolean isLocalActiveProfile() {
    return containsActiveProfile("local");
  }

  @Override
  public boolean isProductionActiveProfile() {
    return containsActiveProfile("production");
  }

  public boolean containsActiveProfile(String profile) {
    AtomicBoolean doesContain = new AtomicBoolean(false);
    Arrays.stream(this.environment.getActiveProfiles()).forEach(activeProfile -> {
      if (activeProfile.equals(profile)) {
        doesContain.set(true);
      }
    });
    return doesContain.get();
  }

}
