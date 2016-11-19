/**
 * (C) Copyright 2014-2016 teecube
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

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import t3.Messages;
import t3.plugin.annotations.Mojo;
import t3.toe.domains.bw6.domains.Domains.Domain;

/**
* <p>
* This goal deletes a TIBCO BusinessWorks 6.x topology of domains.
* </p>
*
* @author Mathieu Debove &lt;mad@teecu.be&gt;
*
*/
@Mojo(name = "bw6-domains-delete", requiresProject = false)
public class BW6DomainsDeleteMojo extends CommonBW6DomainsTopology {

	private void deleteTopology() throws MojoExecutionException, MojoFailureException {
		for (Domain domain : bW6DomainsMarshaller.getObject().domain) {
			getLog().info("Found a domain to delete: '" + domain.name + "'.");
			if (domainExists(domain.name)) {
				deleteDomain(domain);
			} else {
				if (DeleteDomainWhenNotExistsBehaviour.FAIL.equals(domain.deleteWhenNotExists)) {
					getLog().info(Messages.MESSAGE_SPACE);
					getLog().error("The domain does not exist. Topology is set to fail when deleting this domain if it does not exist.");
					getLog().info(Messages.MESSAGE_SPACE);
					throw new MojoExecutionException("Domain '" + domain.name + "' does not exist thus cannot be deleted.");
				} else { // default value is SKIP
					getLog().info("The domain does not exist. Skipping.");
					getLog().info(Messages.MESSAGE_SPACE);
				}
			}
		}
	}

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		super.execute();

		try {
			deleteTopology();
			getLog().info("Topology was successfully deleted.");
			return;
		} catch (Exception e) {
			getLog().error("Topology deletion failed.");
			throw new MojoExecutionException(e.getLocalizedMessage(), e);
		}
	}
}
