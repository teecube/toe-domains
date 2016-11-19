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
package t3.toe.domains;

import t3.CommonMojoInformation;
import t3.plugin.annotations.Categories;
import t3.plugin.annotations.Category;
import t3.tic.bw6.BW6MojoInformation;

/**
* <p>
* Centralization of all Mojo parameters.
* </p>
*
* @author Mathieu Debove &lt;mad@teecu.be&gt;
*
*/
@Categories({
	@Category(title = DomainsMojosInformation.BW5.category, description = DomainsMojosInformation.BW5.category_description),
})
public class DomainsMojosInformation extends CommonMojoInformation {

	public static final String dotTIBCOHome = "tibco.dothome.directory";
	public static final String dotTIBCOHome_default = "${user.home}/.TIBCO";
	public static final String dotTIBCOHome_description = "tibco.dothome.directory";

	public static class BW5 {
		public static final String category = "TIBCO BusinessWorks 5";
		public static final String category_description = "Properties concerning " + category + " binaries & environment";

	}

	public static class BW6 {
		public static final String category = "TIBCO BusinessWorks 6";
		public static final String category_description = "Properties concerning " + category + " binaries & environment";

		public static final String domainHomes = "tibco.bw6.domainHomes";
		public static final String domainHomes_default = BW6MojoInformation.BW6.bwHome_default + "/domains/DomainHomes.properties";
		public static final String domainHomes_description = "The path to the 'DomainHomes.properties' file in the current " + category + " installation.";

		public static final String domainName = "tibco.bw6.domains.domainName";

		public static class Topology {
			public static final String domainTopologyFile = "tibco.bw6.domains.topology.domainTopology";
			public static final String domainTopologyFile_default = "${basedir}/bw6-domains.xml";

			public static final String ifDomainExistsBehaviour = "tibco.bw6.domains.topology.ifDomainExists";
			public static final String ifDomainExistsBehaviour_default = "fail";

			public static final String allowOverrideIfDomainExistsInTopology = "tibco.bw6.domains.topology.ifDomainExists.allowOverrideInTopology";
			public static final String allowOverrideIfDomainExistsInTopology_default = "true";
		}

		public static class Admin {
			public static final String mode = "tibco.bw6.admin.mode";	
			public static final String mode_default = "LOCAL";
			public static final String mode_description = "The mode of bwadmin. Can be either 'LOCAL' or 'ENTERPRISE'.";

			public static final String switchToRightMode = "tibco.bw6.admin.switchToRightMode";
			public static final String switchToRightMode_default = "true";
			public static final String switchToRightMode_description = "Whether to switch to the mode of configuration (set with '" + mode + "' property or with '/domains/@mode' attribute of a topology) when it is different from 'bwadmin' mode set in 'bwagent.ini' file.";

			public static final String alwaysInitBwAdminAndDomains = "tibco.bw6.admin.alwaysInitBwAdminAndDomains";
			public static final String alwaysInitBwAdminAndDomains_default = "false";
			public static final String alwaysInitBwAdminAndDomains_description = "Whether to force reinitialization of 'bwadmin' configuration and domains listing when firing up several goals in a row.";
		}
	}

}
