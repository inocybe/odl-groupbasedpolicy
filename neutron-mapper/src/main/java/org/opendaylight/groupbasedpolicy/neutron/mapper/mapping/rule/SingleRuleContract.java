/*
 * Copyright (c) 2014 Cisco Systems, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.groupbasedpolicy.neutron.mapper.mapping.rule;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import org.opendaylight.neutron.spi.NeutronSecurityRule;
import org.opendaylight.yang.gen.v1.urn.opendaylight.groupbasedpolicy.common.rev140421.Description;
import org.opendaylight.yang.gen.v1.urn.opendaylight.groupbasedpolicy.policy.rev140421.tenants.tenant.policy.Contract;
import org.opendaylight.yang.gen.v1.urn.opendaylight.groupbasedpolicy.policy.rev140421.tenants.tenant.policy.ContractBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.groupbasedpolicy.policy.rev140421.tenants.tenant.policy.contract.Clause;
import org.opendaylight.yang.gen.v1.urn.opendaylight.groupbasedpolicy.policy.rev140421.tenants.tenant.policy.contract.Subject;
import org.opendaylight.yang.gen.v1.urn.opendaylight.groupbasedpolicy.policy.rev140421.tenants.tenant.policy.contract.SubjectBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.groupbasedpolicy.policy.rev140421.tenants.tenant.policy.contract.subject.Rule;

import com.google.common.collect.ImmutableList;

@Immutable
public class SingleRuleContract {

    private final SingleClassifierRule singleClassifierRule;
    private final Rule rule;
    private final Subject subject;
    private final Clause clause;
    private final Contract contract;

    public SingleRuleContract(NeutronSecurityRule secRule, int subjectAndRuleOrder, @Nullable Description contractDescription) {
        this(secRule, new SingleClassifierRule(secRule, subjectAndRuleOrder), subjectAndRuleOrder, contractDescription);
    }

    public SingleRuleContract(NeutronSecurityRule secRule, SingleClassifierRule singleClassifierRule,
            int subjectOrder, @Nullable Description contractDescription) {
        checkNotNull(secRule);
        this.singleClassifierRule = checkNotNull(singleClassifierRule);
        this.rule = singleClassifierRule.getRule();
        this.subject = new SubjectBuilder().setName(SecRuleNameDecoder.getSubjectName(secRule))
            .setOrder(subjectOrder)
            .setRule(ImmutableList.of(rule))
            .build();
        this.clause = SecRuleEntityDecoder.getClause(secRule);
        this.contract = new ContractBuilder().setId(SecRuleEntityDecoder.getContractId(secRule))
            .setSubject(ImmutableList.of(subject))
            .setClause(ImmutableList.of(clause))
            .setDescription(contractDescription)
            .build();
    }

    public SingleClassifierRule getSingleClassifierRule() {
        return singleClassifierRule;
    }

    public Rule getRule() {
        return rule;
    }

    public Subject getSubject() {
        return subject;
    }

    public Clause getClause() {
        return clause;
    }

    public Contract getContract() {
        return contract;
    }

}
