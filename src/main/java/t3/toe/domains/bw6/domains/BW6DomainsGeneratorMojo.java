/**
 * (C) Copyright 2016-2018 teecube
 * (https://teecu.be) and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package t3.toe.domains.bw6.domains;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import t3.Messages;
import t3.plugin.annotations.Mojo;
import t3.plugin.annotations.Parameter;
import t3.toe.domains.DomainsMojosInformation;
import t3.toe.domains.bw6.domains.Domains.Domain;

/**
* <p>
* This goal generates a TIBCO BusinessWorks 6.x topology of domains.
* </p>
*
* @author Mathieu Debove &lt;mad@teecu.be&gt;
*
*/
@Mojo(name = "bw6-domains-generate", requiresProject = false)
public class BW6DomainsGeneratorMojo extends CommonBW6DomainsTopology {

	@Parameter (property = DomainsMojosInformation.BW6.Topology.ifDomainExistsBehaviour, defaultValue = "fail")
	protected String ifDomainExistsBehaviour;
	private IfDomainExistsBehaviour _ifDomainExistsBehaviour;

	@Parameter (property = DomainsMojosInformation.BW6.Topology.allowOverrideIfDomainExistsInTopology, defaultValue = DomainsMojosInformation.BW6.Topology.allowOverrideIfDomainExistsInTopology_default)
	protected boolean allowOverrideIfDomainExistsBehaviourInTopology;

	private void initIfDomainExistsBehaviour() {
		switch (ifDomainExistsBehaviour) {
		case "delete":
			_ifDomainExistsBehaviour = IfDomainExistsBehaviour.DELETE;
			break;
		case "fail":
			_ifDomainExistsBehaviour = IfDomainExistsBehaviour.FAIL;
			break;
		case "keep":
			_ifDomainExistsBehaviour = IfDomainExistsBehaviour.KEEP;
			break;
		default:
			_ifDomainExistsBehaviour = IfDomainExistsBehaviour.FAIL; // default behaviour is to fail (this default should not occur as the property is enforced before getting here)
			break;
		}
	}

	private boolean overrideDomain(Domain domain) throws MojoExecutionException, MojoFailureException {
		switch (domain.ifExists) {
		case DELETE:
			getLog().info("Deleting domain '" + domain.name + "' first (as specified in topology).");
			deleteDomain(domain);
			return true;
		case FAIL:
			getLog().info(Messages.MESSAGE_SPACE);
			getLog().error("Domain '" + domain.name + "' already exists and cannot be kept nor deleted (as specified in topology).");
			getLog().info(Messages.MESSAGE_SPACE);
			throw new MojoExecutionException("Domain '" + domain.name + "' already exists and cannot be deleted.");
		case KEEP:
			getLog().info("Keeping domain '" + domain.name + "' (as specified in topology).");
			getLog().info(Messages.MESSAGE_SPACE);
			return false;
		}
		return true; // dead code anyway
	}

	private void generateTopology() throws MojoExecutionException, MojoFailureException {
		for (Domain domain : bW6DomainsMarshaller.getObject().domain) {
			getLog().info("Found a domain to create: '" + domain.name + "'.");
			if (BwAdminMode.LOCAL.equals(bwAdminCurrentMode) && domainExistsInOneOfTwoModes(domain.name) && !domainExists(domain.name)) {
				getLog().info(Messages.MESSAGE_SPACE);
				getLog().error("Domain '" + domain.name + "' already exists in 'ENTERPRISE' mode thus cannot be created in 'LOCAL' mode.");
				getLog().error("Define a topology in 'ENTERPRISE' mode or delete domain '" + domain.name + "' in 'ENTERPRISE' mode.");
				getLog().info(Messages.MESSAGE_SPACE);
				throw new MojoExecutionException("Domain '" + domain.name + "' already exists in 'ENTERPRISE' mode.");
			}
			if (domainExists(domain.name)) {
				getLog().info("The domain already exists.");

				switch (_ifDomainExistsBehaviour) {
				case DELETE:
					if (allowOverrideIfDomainExistsBehaviourInTopology) {
						if (domain.ifExists == null) { // override but nothing is set so use default behaviour
							getLog().info("Deleting domain '" + domain.name + "' first (as specified by '" + DomainsMojosInformation.BW6.Topology.ifDomainExistsBehaviour + "' property set to '" + IfDomainExistsBehaviour.DELETE.value() + "' and because no attribute 'ifExists' was set in the topology for this domain).");
							deleteDomain(domain);
						} else {
							if (!overrideDomain(domain)) continue;
						}
					} else { // no override so delete the domain
						getLog().info("Deleting domain '" + domain.name + "' first (as specified by '" + DomainsMojosInformation.BW6.Topology.ifDomainExistsBehaviour + "' property set to '" + IfDomainExistsBehaviour.DELETE.value() + "').");
						getLog().info("Notice that any attribute 'ifExists' set in the topology for this domain will be ignored with this configuration because '" + DomainsMojosInformation.BW6.Topology.allowOverrideIfDomainExistsInTopology + "' property is set to 'false'.");
						deleteDomain(domain);
					}
					break;
				case FAIL:
					if (allowOverrideIfDomainExistsBehaviourInTopology) {
						if (domain.ifExists == null) { // override but nothing is set so use default behaviour
							getLog().info(Messages.MESSAGE_SPACE);
							getLog().error("Domain '" + domain.name + "' already exists and cannot be kept nor deleted (as specified by '" + DomainsMojosInformation.BW6.Topology.ifDomainExistsBehaviour + "' property set to '" + IfDomainExistsBehaviour.FAIL.value() + "' and because no attribute 'ifExists' was set in the topology for this domain).");
							getLog().info(Messages.MESSAGE_SPACE);
							throw new MojoExecutionException("Domain '" + domain.name + "' already exists and cannot be deleted.");
						} else {
							if (!overrideDomain(domain)) continue;
						}
					} else { // no override so fail (cannot delete the domain)
						getLog().info(Messages.MESSAGE_SPACE);
						getLog().error("Domain '" + domain.name + "' already exists and cannot be kept nor deleted (as specified by '" + DomainsMojosInformation.BW6.Topology.ifDomainExistsBehaviour + "' property set to '" + IfDomainExistsBehaviour.FAIL.value() + "').");
						getLog().info("Notice that any attribute 'ifExists' set in the topology for this domain will be ignored with this configuration because '" + DomainsMojosInformation.BW6.Topology.allowOverrideIfDomainExistsInTopology + "' property is set to 'false'.");
						getLog().info(Messages.MESSAGE_SPACE);
						throw new MojoExecutionException("Domain '" + domain.name + "' already exists and cannot be deleted.");
					}
					break;
				case KEEP:
					if (allowOverrideIfDomainExistsBehaviourInTopology) {
						if (domain.ifExists == null) { // override but nothing is set so use default behaviour
							getLog().info("Keeping domain '" + domain.name + "' (as specified by '" + DomainsMojosInformation.BW6.Topology.ifDomainExistsBehaviour + "' property set to '" + IfDomainExistsBehaviour.KEEP.value() + "' and because no attribute 'ifExists' was set in the topology for this domain).");
							getLog().info(Messages.MESSAGE_SPACE);
							continue; // keep it so do nothing
						} else {
							if (!overrideDomain(domain)) continue;
						}
					} else { // no override so keep the domain
						getLog().info("Keeping domain '" + domain.name + "' (as specified by '" + DomainsMojosInformation.BW6.Topology.ifDomainExistsBehaviour + "' property set to '" + IfDomainExistsBehaviour.FAIL.value() + "').");
						getLog().info("Notice that any attribute 'ifExists' set in the topology for this domain will be ignored with this configuration because '" + DomainsMojosInformation.BW6.Topology.allowOverrideIfDomainExistsInTopology + "' property is set to 'false'.");
						getLog().info(Messages.MESSAGE_SPACE);
						continue; // keep it so do nothing
					}
					break;
				}
			}
			getLog().info("Domain '" + domain.name + "' will be created.");
			createDomain(domain);
		}
	}

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		super.execute();

		initIfDomainExistsBehaviour();

		try {
			generateTopology();
			getLog().info("Topology was successfully created.");
			return;
		} catch (Exception e) {
			getLog().error("Topology creation failed.");
			throw new MojoExecutionException(e.getLocalizedMessage(), e);
		}
	}

}
