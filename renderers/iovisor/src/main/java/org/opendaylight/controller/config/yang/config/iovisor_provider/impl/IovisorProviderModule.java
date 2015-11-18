package org.opendaylight.controller.config.yang.config.iovisor_provider.impl;

import org.opendaylight.groupbasedpolicy.renderer.iovisor.IovisorRenderer;

public class IovisorProviderModule extends org.opendaylight.controller.config.yang.config.iovisor_provider.impl.AbstractIovisorProviderModule {
    public IovisorProviderModule(org.opendaylight.controller.config.api.ModuleIdentifier identifier, org.opendaylight.controller.config.api.DependencyResolver dependencyResolver) {
        super(identifier, dependencyResolver);
    }

    public IovisorProviderModule(org.opendaylight.controller.config.api.ModuleIdentifier identifier, org.opendaylight.controller.config.api.DependencyResolver dependencyResolver, org.opendaylight.controller.config.yang.config.iovisor_provider.impl.IovisorProviderModule oldModule, java.lang.AutoCloseable oldInstance) {
        super(identifier, dependencyResolver, oldModule, oldInstance);
    }

    @Override
    public void customValidation() {
        // add custom validation form module attributes here.
    }

    @Override
    public java.lang.AutoCloseable createInstance() {
        return  new IovisorRenderer(getDataBrokerDependency());
    }
}
