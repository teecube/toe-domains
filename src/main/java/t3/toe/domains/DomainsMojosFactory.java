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
package t3.toe.domains;

import org.apache.maven.plugin.AbstractMojo;

import t3.MojosFactory;
import t3.toe.domains.bw6.domain.BW6DomainCreateMojo;
import t3.toe.domains.bw6.domain.BW6DomainDeleteMojo;
import t3.toe.domains.bw6.domain.BW6DomainsDeleteAllMojo;
import t3.toe.domains.bw6.domains.BW6DomainsGeneratorMojo;

/**
 *
 * @author Mathieu Debove &lt;mad@teecu.be&gt;
 *
 */
public class DomainsMojosFactory extends MojosFactory {
	@SuppressWarnings("unchecked")
	public <T extends AbstractMojo> T getMojo(Class<T> type) {
		if (type == null) {
			return null;
		}

		String typeName = type.getSimpleName();

		switch (typeName) {
		case "BW6DomainCreateMojo":
			return (T) new BW6DomainCreateMojo(null);
		case "BW6DomainDeleteMojo":
			return (T) new BW6DomainDeleteMojo(null);
		case "BW6DomainsDeleteAllMojo":
			return (T) new BW6DomainsDeleteAllMojo();
		case "BW6DomainsGeneratorMojo":
			return (T) new BW6DomainsGeneratorMojo();

		default:
			return super.getMojo(type);
		}

	}

	@SuppressWarnings("unchecked")
	public static <T extends CommonDomains> T getDomainsMojo(String goal) {
		if (goal == null) {
			return null;
		}

		switch (goal) {
		case "toe-domains:bw6-domain-create":
			return (T) new BW6DomainCreateMojo(null);
		case "toe-domains:bw6-domain-delete":
			return (T) new BW6DomainDeleteMojo(null);
		case "toe-domains:bw6-domains-delete-all":
			return (T) new BW6DomainsDeleteAllMojo();
		case "toe-domains:bw6-domains-generate":
			return (T) new BW6DomainsGeneratorMojo();

		default:
			return null;
		}
	}

}
