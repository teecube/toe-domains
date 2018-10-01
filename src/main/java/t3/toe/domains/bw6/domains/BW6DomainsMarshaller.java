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

import org.xml.sax.SAXException;
import t3.xml.XMLMarshall;

import javax.xml.bind.JAXBException;
import java.io.File;

public class BW6DomainsMarshaller extends XMLMarshall<Domains, ObjectFactory> {

	public static final String NAMESPACE = "http://teecu.be/toe-domains/bw6-domains";

	public BW6DomainsMarshaller(File xmlFile) throws JAXBException, SAXException {
		super(xmlFile);
	}

}
