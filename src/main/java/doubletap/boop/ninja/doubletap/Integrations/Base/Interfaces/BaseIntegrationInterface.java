package doubletap.boop.ninja.doubletap.Integrations.Base.Interfaces;

public interface BaseIntegrationInterface {
  default RuntimeWiringInterface wiring() {
    return null;
  }
}
