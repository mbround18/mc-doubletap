package doubletap.boop.ninja.doubletap.Integrations.Server;

import doubletap.boop.ninja.doubletap.Integrations.Base.BaseIntegration;
import doubletap.boop.ninja.doubletap.Integrations.Base.Interfaces.RuntimeWiringInterface;

public class ServerIntegration extends BaseIntegration {

  public ServerIntegration() {
    super("server");
  }

  @Override
  public RuntimeWiringInterface wiring() {
    return new ServerRuntimeWiring();
  }
}
