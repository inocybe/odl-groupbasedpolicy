/*
 * Copyright (c) 2014 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.controller.config.yang.config.neutron_mapper.impl;

import org.opendaylight.groupbasedpolicy.neutron.mapper.NeutronMapper;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NeutronMapperModule extends org.opendaylight.controller.config.yang.config.neutron_mapper.impl.AbstractNeutronMapperModule {

    private final Logger LOG = LoggerFactory.getLogger(NeutronMapperModule.class);
    private BundleContext bundleContext;

    public NeutronMapperModule(org.opendaylight.controller.config.api.ModuleIdentifier identifier, org.opendaylight.controller.config.api.DependencyResolver dependencyResolver) {
        super(identifier, dependencyResolver);
    }

    public NeutronMapperModule(org.opendaylight.controller.config.api.ModuleIdentifier identifier, org.opendaylight.controller.config.api.DependencyResolver dependencyResolver, org.opendaylight.controller.config.yang.config.neutron_mapper.impl.NeutronMapperModule oldModule, java.lang.AutoCloseable oldInstance) {
        super(identifier, dependencyResolver, oldModule, oldInstance);
    }

    @Override
    public void customValidation() {
        // add custom validation form module attributes here.
    }

    @Override
    public java.lang.AutoCloseable createInstance() {
        final NeutronMapper neutronMapper = new NeutronMapper(getDataBrokerDependency(), getRpcRegistryDependency(), bundleContext);
        LOG.info("Neutron mapper started.");
        return new AutoCloseable() {

            @Override
            public void close() throws Exception {
                neutronMapper.close();
            }
        };
    }

    public void setBundleContext(BundleContext bundleContext) {
        this.bundleContext = bundleContext;
    }

}
