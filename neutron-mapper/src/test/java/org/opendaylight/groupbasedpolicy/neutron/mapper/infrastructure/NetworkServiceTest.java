package org.opendaylight.groupbasedpolicy.neutron.mapper.infrastructure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.Set;

import org.junit.Test;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.ReadWriteTransaction;
import org.opendaylight.groupbasedpolicy.neutron.mapper.test.GbpDataBrokerTest;
import org.opendaylight.groupbasedpolicy.neutron.mapper.test.PolicyAssert;
import org.opendaylight.groupbasedpolicy.neutron.mapper.util.MappingUtils;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.inet.types.rev100924.IpPrefix;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.inet.types.rev100924.Ipv4Prefix;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.inet.types.rev100924.Ipv6Prefix;
import org.opendaylight.yang.gen.v1.urn.opendaylight.groupbasedpolicy.common.rev140421.SubjectName;
import org.opendaylight.yang.gen.v1.urn.opendaylight.groupbasedpolicy.common.rev140421.TenantId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.groupbasedpolicy.policy.rev140421.tenants.tenant.policy.subject.feature.instances.ClassifierInstance;

public class NetworkServiceTest extends GbpDataBrokerTest {

    // dhcp
    private static final SubjectName DHCP_SUBJECT_NAME = new SubjectName("ALLOW_DHCP");
    private static final String DHCP_IPV4_CLIENT_SERVER_NAME = "DHCP_IPv4_FROM_CLIENT_TO_SERVER";
    private static final String DHCP_IPV4_SERVER_CLIENT_NAME = "DHCP_IPv4_FROM_SERVER_TO_CLIENT";
    private static final String DHCP_IPV6_CLIENT_SERVER_NAME = "DHCP_IPv6_FROM_CLIENT_TO_SERVER";
    private static final String DHCP_IPV6_SERVER_CLIENT_NAME = "DHCP_IPv6_FROM_SERVER_TO_CLIENT";

    // dns
    private static final SubjectName DNS_SUBJECT_NAME = new SubjectName("ALLOW_DNS");
    private static final String DNS_UDP_IPV4_CLIENT_SERVER_NAME = "DNS_UDP_IPv4_FROM_CLIENT_TO_SERVER";
    private static final String DNS_UDP_IPV4_SERVER_CLIENT_NAME = "DNS_UDP_IPv4_FROM_SERVER_TO_CLIENT";
    private static final String DNS_UDP_IPV6_CLIENT_SERVER_NAME = "DNS_UDP_IPv6_FROM_CLIENT_TO_SERVER";
    private static final String DNS_UDP_IPV6_SERVER_CLIENT_NAME = "DNS_UDP_IPv6_FROM_SERVER_TO_CLIENT";
    private static final String DNS_TCP_IPV4_CLIENT_SERVER_NAME = "DNS_TCP_IPv4_FROM_CLIENT_TO_SERVER";
    private static final String DNS_TCP_IPV4_SERVER_CLIENT_NAME = "DNS_TCP_IPv4_FROM_SERVER_TO_CLIENT";
    private static final String DNS_TCP_IPV6_CLIENT_SERVER_NAME = "DNS_TCP_IPv6_FROM_CLIENT_TO_SERVER";
    private static final String DNS_TCP_IPV6_SERVER_CLIENT_NAME = "DNS_TCP_IPv6_FROM_SERVER_TO_CLIENT";

    // mgmt
    private static final SubjectName MGMT_SUBJECT_NAME = new SubjectName("ALLOW_MGMT");
    private static final String SSH_IPV4_SERVER_TO_CLIENT_NAME = "SSH_IPV4_FROM_SERVER_TO_CLIENT";
    private static final String SSH_IPV6_SERVER_TO_CLIENT_NAME = "SSH_IPV6_FROM_SERVER_TO_CLIENT";
    private static final String SSH_IPV4_CLIENT_TO_SERVER_NAME = "SSH_IPV4_FROM_CLIENT_TO_SERVER";
    private static final String SSH_IPV6_CLIENT_TO_SERVER_NAME = "SSH_IPV6_FROM_CLIENT_TO_SERVER";
    private static final String ICMP_IPV4_BETWEEN_SERVER_CLIENT_NAME = "ICMP_IPV4_BETWEEN_SERVER_CLIENT";
    private static final String ICMP_IPV6_BETWEEN_SERVER_CLIENT_NAME = "ICMP_IPV6_BETWEEN_SERVER_CLIENT";

    private final String tenantId = "00000000-0000-0000-0000-000000000001";
    private final IpPrefix ipv4Prefix = new IpPrefix(new Ipv4Prefix("170.0.0.1/8"));
    private final IpPrefix ipv6Prefix = new IpPrefix(new Ipv6Prefix("2001:0db8:85a3:0000:0000:8a2e:0370:7334/128"));

    @Test
    public void instantiate() {
        NetworkService service = new NetworkService();
        assertNotNull(service);
    }

    @Test
    public void testWriteDhcpClauseWithConsProvEicIpv4() throws Exception {
        DataBroker dataBroker = getDataBroker();
        ReadWriteTransaction rwTx = dataBroker.newReadWriteTransaction();
        NetworkService.writeDhcpClauseWithConsProvEic(new TenantId(tenantId), ipv4Prefix, rwTx);
        rwTx.submit().get();

        // expected clause name
        String clauseNameIpV4 = DHCP_SUBJECT_NAME.getValue() + MappingUtils.NAME_DOUBLE_DELIMETER
                + ipv4Prefix.getIpv4Prefix().getValue();
        clauseNameIpV4 = clauseNameIpV4.replace('/', '_');

        PolicyAssert.assertClauseExists(dataBroker, tenantId, NetworkService.DHCP_CONTRACT_ID.getValue(),
                clauseNameIpV4);
    }

    @Test
    public void testWriteDhcpClauseWithConsProvEicIpv6() throws Exception {
        DataBroker dataBroker = getDataBroker();
        ReadWriteTransaction rwTx = dataBroker.newReadWriteTransaction();
        NetworkService.writeDhcpClauseWithConsProvEic(new TenantId(tenantId), ipv6Prefix, rwTx);
        rwTx.submit().get();

        // expected clause name
        String clauseNameIpV6 = DHCP_SUBJECT_NAME.getValue() + MappingUtils.NAME_DOUBLE_DELIMETER
                + ipv6Prefix.getIpv6Prefix().getValue();
        clauseNameIpV6 = clauseNameIpV6.replace('/', '_').replace(':', '.');

        PolicyAssert.assertClauseExists(dataBroker, tenantId, NetworkService.DHCP_CONTRACT_ID.getValue(),
                clauseNameIpV6);
    }

    @Test
    public void testWriteDnsClauseWithConsProvEicIpv4() throws Exception {
        // ipv4
        DataBroker dataBroker = getDataBroker();
        ReadWriteTransaction rwTx = dataBroker.newReadWriteTransaction();
        NetworkService.writeDnsClauseWithConsProvEic(new TenantId(tenantId), ipv4Prefix, rwTx);
        rwTx.submit().get();

        // expected clause name
        String clauseNameIpV4 = DNS_SUBJECT_NAME.getValue() + MappingUtils.NAME_DOUBLE_DELIMETER
                + ipv4Prefix.getIpv4Prefix().getValue();
        clauseNameIpV4 = clauseNameIpV4.replace('/', '_');

        PolicyAssert.assertClauseExists(dataBroker, tenantId, NetworkService.DNS_CONTRACT_ID.getValue(),
                clauseNameIpV4);
    }

    @Test
    public void testWriteDnsClauseWithConsProvEicIpv6() throws Exception {
        // ipv6
        DataBroker dataBroker = getDataBroker();
        ReadWriteTransaction rwTx = dataBroker.newReadWriteTransaction();
        NetworkService.writeDnsClauseWithConsProvEic(new TenantId(tenantId), ipv6Prefix, rwTx);
        rwTx.submit().get();

        // expected clause name
        String clauseNameIpV6 = DNS_SUBJECT_NAME.getValue() + MappingUtils.NAME_DOUBLE_DELIMETER
                + ipv6Prefix.getIpv6Prefix().getValue();
        clauseNameIpV6 = clauseNameIpV6.replace('/', '_').replace(':', '.');

        PolicyAssert.assertClauseExists(dataBroker, tenantId, NetworkService.DNS_CONTRACT_ID.getValue(),
                clauseNameIpV6);
    }

    @Test
    public void testWriteNetworkServiceEntitiesToTenant() throws Exception {
        // write everything
        DataBroker dataBroker = getDataBroker();
        ReadWriteTransaction rwTx = dataBroker.newReadWriteTransaction();
        NetworkService.writeNetworkServiceEntitiesToTenant(new TenantId(tenantId), rwTx);
        rwTx.submit().get();

        // read classifier instances
        PolicyAssert.assertClassifierInstanceExists(dataBroker, tenantId, DHCP_IPV4_CLIENT_SERVER_NAME);
        PolicyAssert.assertClassifierInstanceExists(dataBroker, tenantId, DHCP_IPV4_SERVER_CLIENT_NAME);
        PolicyAssert.assertClassifierInstanceExists(dataBroker, tenantId, DHCP_IPV6_CLIENT_SERVER_NAME);
        PolicyAssert.assertClassifierInstanceExists(dataBroker, tenantId, DHCP_IPV6_SERVER_CLIENT_NAME);
        PolicyAssert.assertClassifierInstanceExists(dataBroker, tenantId, DNS_UDP_IPV4_CLIENT_SERVER_NAME);
        PolicyAssert.assertClassifierInstanceExists(dataBroker, tenantId, DNS_UDP_IPV4_SERVER_CLIENT_NAME);
        PolicyAssert.assertClassifierInstanceExists(dataBroker, tenantId, DNS_UDP_IPV6_CLIENT_SERVER_NAME);
        PolicyAssert.assertClassifierInstanceExists(dataBroker, tenantId, DNS_UDP_IPV6_SERVER_CLIENT_NAME);
        PolicyAssert.assertClassifierInstanceExists(dataBroker, tenantId, DNS_TCP_IPV4_CLIENT_SERVER_NAME);
        PolicyAssert.assertClassifierInstanceExists(dataBroker, tenantId, DNS_TCP_IPV4_SERVER_CLIENT_NAME);
        PolicyAssert.assertClassifierInstanceExists(dataBroker, tenantId, DNS_TCP_IPV6_CLIENT_SERVER_NAME);
        PolicyAssert.assertClassifierInstanceExists(dataBroker, tenantId, DNS_TCP_IPV6_SERVER_CLIENT_NAME);
        PolicyAssert.assertClassifierInstanceExists(dataBroker, tenantId, SSH_IPV4_SERVER_TO_CLIENT_NAME);
        PolicyAssert.assertClassifierInstanceExists(dataBroker, tenantId, SSH_IPV6_SERVER_TO_CLIENT_NAME);
        PolicyAssert.assertClassifierInstanceExists(dataBroker, tenantId, SSH_IPV4_CLIENT_TO_SERVER_NAME);
        PolicyAssert.assertClassifierInstanceExists(dataBroker, tenantId, SSH_IPV6_CLIENT_TO_SERVER_NAME);
        PolicyAssert.assertClassifierInstanceExists(dataBroker, tenantId, ICMP_IPV4_BETWEEN_SERVER_CLIENT_NAME);
        PolicyAssert.assertClassifierInstanceExists(dataBroker, tenantId, ICMP_IPV6_BETWEEN_SERVER_CLIENT_NAME);

        // read contracts
        PolicyAssert.assertContractExists(dataBroker, tenantId, NetworkService.DHCP_CONTRACT_ID.getValue());
        PolicyAssert.assertContractExists(dataBroker, tenantId, NetworkService.DNS_CONTRACT_ID.getValue());
        PolicyAssert.assertContractExists(dataBroker, tenantId, NetworkService.MGMT_CONTRACT_ID.getValue());

        // read group id
        PolicyAssert.assertEndpointGroupExists(dataBroker, tenantId, NetworkService.EPG_ID.getValue());
    }

    @Test
    public void testGetAllClassifierInstances() {
        Set<ClassifierInstance> classifierInstances = NetworkService.getAllClassifierInstances();
        assertNotNull(classifierInstances);
        assertFalse(classifierInstances.isEmpty());
        assertEquals(classifierInstances.size(), 18);
    }

}
