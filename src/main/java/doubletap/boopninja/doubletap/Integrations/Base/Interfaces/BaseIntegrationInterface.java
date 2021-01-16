package doubletap.boopninja.doubletap.Integrations.Base.Interfaces;

public interface BaseIntegrationInterface {
  default RuntimeWiringInterface wiring() {
    return null;
  }
}
