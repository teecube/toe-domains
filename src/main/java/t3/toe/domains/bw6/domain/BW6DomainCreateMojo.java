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
package t3.toe.domains.bw6.domain;

import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import t3.CommonMojo;
import t3.Messages;
import t3.plugin.annotations.Mojo;
import t3.plugin.annotations.Parameter;
import t3.toe.domains.DomainsMojosInformation;
import t3.toe.domains.bw6.CommonBW6Domains;

/**
* <p>
* This goal creates a TIBCO BusinessWorks 6.x domain.
* </p>
*
* @author Mathieu Debove &lt;mad@teecu.be&gt;
*
*/
@Mojo(name = "bw6-domain-create", requiresProject = false)
public class BW6DomainCreateMojo extends CommonBW6Domains {

	@Parameter (property = DomainsMojosInformation.BW6.domainName, defaultValue = "")
	public String domainName;

	public BW6DomainCreateMojo(CommonMojo mojo) {
		super(mojo);
	}

	@Override
	protected void initDefaultParameters() throws MojoExecutionException {

	}

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		super.execute();

		if (domainExists(domainName)) {
			getLog().info(Messages.MESSAGE_SPACE);
			getLog().error("Domain '" + domainName + "' already exists.");

			throw new MojoExecutionException("Domain '" + domainName + "' already exists.");
		}

		List<String> arguments = new ArrayList<String>();
		arguments.add("create");
		arguments.add("domain");
		arguments.add(domainName);

		getLog().info(Messages.MESSAGE_SPACE);
		executeBWAdmin(arguments, "Creating domain '" + domainName  + "'...", "Domain '" + domainName  + "' created successfully.", "Unable to create domain '" + domainName  + "'");
	}

}
