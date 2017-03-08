/**
 * (C) Copyright 2016-2017 teecube
 * (http://teecu.be) and others.
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

import java.io.File;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import t3.Messages;
import t3.plugin.annotations.Parameter;
import t3.toe.domains.DomainsMojosInformation;
import t3.toe.domains.bw6.CommonBW6Domains;
import t3.toe.domains.bw6.domain.BW6DomainCreateMojo;
import t3.toe.domains.bw6.domain.BW6DomainDeleteMojo;
import t3.toe.domains.bw6.domains.Domains.Domain;

/**
*
* @author Mathieu Debove &lt;mad@teecu.be&gt;
*
*/
public class CommonBW6DomainsTopology extends CommonBW6Domains {

	protected String bw6CreateGoal = "bw6-domain-create";
	protected String bw6DeleteGoal = "bw6-domain-delete";

	@Parameter (property = DomainsMojosInformation.BW6.Topology.domainTopologyFile, defaultValue = DomainsMojosInformation.BW6.Topology.domainTopologyFile_default)
	protected File domainsTopology;

	protected BW6DomainsMarshaller bW6DomainsMarshaller;

	@Override
	protected void initBwAdminAndDomains() throws MojoExecutionException {
		loadTopology();

		super.initBwAdminAndDomains();
	}

	@Override
	protected String getBwAdminMode() {
		return getTopologyMode().toString();
	}

	@Override
	protected void displayBwAdminMode() {
		getLog().info("bwadmin mode is set to '" + getTopologyMode().toString() + "'" + ("LOCAL".equals(getBwAdminMode()) && bwAdminCurrentMode.equals(BwAdminMode.ENTERPRISE) ? "     " : "") + " mode (by the topology).");
	}

	private Mode getTopologyMode() {
		return bW6DomainsMarshaller.getObject().mode;
	}

	protected void loadTopology() throws MojoExecutionException {
		getLog().info("Using topology file: " + domainsTopology.getAbsolutePath());
		getLog().info(Messages.MESSAGE_SPACE);

		try {
			bW6DomainsMarshaller = new BW6DomainsMarshaller(domainsTopology);
		} catch (JAXBException e) {
			throw new MojoExecutionException("Unable to load topology from file '" + domainsTopology.getAbsolutePath() + "'");
		}
		if (bW6DomainsMarshaller == null) {
			throw new MojoExecutionException("Unable to load topology from file '" + domainsTopology.getAbsolutePath() + "'");
		}

	}

	protected void createDomain(Domain domain) throws MojoExecutionException, MojoFailureException {
		getLog().info(Messages.MESSAGE_SPACE);
		getLog().info(">>> " + pluginDescriptor.getArtifactId() + ":" + pluginDescriptor.getVersion() + ":" + bw6CreateGoal + " (" + "default-cli" + ") @ " + project.getArtifactId() + " >>>");

		BW6DomainCreateMojo bw6DomainCreateMojo = new BW6DomainCreateMojo(this);
		List<Map.Entry<String,String>> ignoredParameters = new ArrayList<Map.Entry<String,String>>();
		ignoredParameters.add(new AbstractMap.SimpleEntry<>("domainName", BW6DomainCreateMojo.class.getCanonicalName()));
		bw6DomainCreateMojo.setIgnoredParameters(ignoredParameters);
		bw6DomainCreateMojo.domainName = domain.name;
		bw6DomainCreateMojo.execute();

		getLog().info(Messages.MESSAGE_SPACE);
		getLog().info("<<< " + pluginDescriptor.getArtifactId() + ":" + pluginDescriptor.getVersion() + ":" + bw6CreateGoal + " (" + "default-cli" + ") @ " + project.getArtifactId() + " <<<");
		getLog().info(Messages.MESSAGE_SPACE);
	}

	protected void deleteDomain(Domain domain) throws MojoExecutionException, MojoFailureException {
		getLog().info(Messages.MESSAGE_SPACE);
		getLog().info(">>> " + pluginDescriptor.getArtifactId() + ":" + pluginDescriptor.getVersion() + ":" + bw6DeleteGoal + " (" + "default-cli" + ") @ " + project.getArtifactId() + " >>>");

		BW6DomainDeleteMojo bw6DomainDeleteMojo = new BW6DomainDeleteMojo(this);
		List<Map.Entry<String,String>> ignoredParameters = new ArrayList<Map.Entry<String,String>>();
		ignoredParameters.add(new AbstractMap.SimpleEntry<>("domainName", BW6DomainDeleteMojo.class.getCanonicalName()));
		bw6DomainDeleteMojo.setIgnoredParameters(ignoredParameters);
		bw6DomainDeleteMojo.domainName = domain.name;
		bw6DomainDeleteMojo.execute();

		getLog().info(Messages.MESSAGE_SPACE);
		getLog().info("<<< " + pluginDescriptor.getArtifactId() + ":" + pluginDescriptor.getVersion() + ":" + bw6DeleteGoal + " (" + "default-cli" + ") @ " + project.getArtifactId() + " <<<");
		getLog().info(Messages.MESSAGE_SPACE);
	}

}
