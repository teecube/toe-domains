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
package t3.toe.domains.bw6.domain;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import t3.Messages;
import t3.plugin.annotations.Mojo;
import t3.toe.domains.bw6.CommonBW6Domains;
import t3.toe.domains.bw6.Domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
* <p>
* This goal deletes all TIBCO BusinessWorks 6.x domains.
* </p>
*
* @author Mathieu Debove &lt;mad@teecu.be&gt;
*
*/
@Mojo(name = "bw6-domains-delete-all", requiresProject = false)
public class BW6DomainsDeleteAllMojo extends CommonBW6Domains {

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		super.execute();

		List<String> arguments = new ArrayList<String>();

		getLog().info(Messages.MESSAGE_SPACE);
		String domainName;
		for (Iterator<Domain> it = domains.iterator(); it.hasNext();) {
			Domain d = (Domain) it.next();
			domainName = d.getName();

			arguments.clear();
			arguments.add("delete");
			arguments.add("domain");
			arguments.add(domainName);

			executeBWAdmin(arguments, "Deleting domain '" + domainName  + "'...", "Domain '" + domainName  + "' deleted successfully.", "Unable to delete domain '" + domainName  + "'");

			it.remove();
			getLog().info(Messages.MESSAGE_SPACE);
		}
	}

}
