/*
 * Copyright (c) 2015 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.controller.config.yang.config.neutron_ovsdb.impl;

import org.opendaylight.groupbasedpolicy.neutron.ovsdb.NeutronOvsdb;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NeutronOvsdbModule extends org.opendaylight.controller.config.yang.config.neutron_ovsdb.impl.AbstractNeutronOvsdbModule {

    private final Logger LOG = LoggerFactory.getLogger(NeutronOvsdbModule.class);
    private BundleContext bundleContext;

    public NeutronOvsdbModule(org.opendaylight.controller.config.api.ModuleIdentifier identifier, org.opendaylight.controller.config.api.DependencyResolver dependencyResolver) {
        super(identifier, dependencyResolver);
    }

    public NeutronOvsdbModule(org.opendaylight.controller.config.api.ModuleIdentifier identifier, org.opendaylight.controller.config.api.DependencyResolver dependencyResolver, org.opendaylight.controller.config.yang.config.neutron_ovsdb.impl.NeutronOvsdbModule oldModule, java.lang.AutoCloseable oldInstance) {
        super(identifier, dependencyResolver, oldModule, oldInstance);
    }

    @Override
    public void customValidation() {
        // add custom validation form module attributes here.
    }

    @Override
    public java.lang.AutoCloseable createInstance() {
        final NeutronOvsdb neutronOvsdb = new NeutronOvsdb(getDataBrokerDependency(), getRpcRegistryDependency(), bundleContext);
        LOG.info("Neutron ovsdb started.");
        return new AutoCloseable() {

            @Override
            public void close() throws Exception {
                neutronOvsdb.close();
            }
        };
    }

    public void setBundleContext(BundleContext bundleContext) {
        this.bundleContext = bundleContext;
    }

}
