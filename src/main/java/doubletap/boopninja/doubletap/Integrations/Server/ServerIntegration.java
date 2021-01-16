package doubletap.boopninja.doubletap.Integrations.Server;

import doubletap.boopninja.doubletap.Integrations.Base.BaseIntegration;
import doubletap.boopninja.doubletap.Integrations.Base.Interfaces.RuntimeWiringInterface;

public class ServerIntegration extends BaseIntegration {

  public ServerIntegration() {
    super("server");
  }

  @Override
  public RuntimeWiringInterface wiring() {
    return new ServerRuntimeWiring();
  }
}
