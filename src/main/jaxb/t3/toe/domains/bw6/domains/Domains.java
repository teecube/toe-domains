//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.04.28 at 02:38:28 PM CEST 
//


package t3.toe.domains.bw6.domains;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="domain" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;simpleContent&gt;
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
 *                 &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                 &lt;attribute name="ifExists" type="{http://teecu.be/toe-domains/bw6-domains}ifDomainExistsBehaviour" /&gt;
 *                 &lt;attribute name="deleteWhenNotExists" type="{http://teecu.be/toe-domains/bw6-domains}deleteDomainWhenNotExistsBehaviour" default="skip" /&gt;
 *               &lt;/extension&gt;
 *             &lt;/simpleContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="mode" use="required" type="{http://teecu.be/toe-domains/bw6-domains}mode" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "domain"
})
@XmlRootElement(name = "domains")
public class Domains {

    protected List<Domains.Domain> domain;
    @XmlAttribute(name = "mode", required = true)
    protected Mode mode;

    /**
     * Gets the value of the domain property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the domain property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDomain().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Domains.Domain }
     * 
     * 
     */
    public List<Domains.Domain> getDomain() {
        if (domain == null) {
            domain = new ArrayList<Domains.Domain>();
        }
        return this.domain;
    }

    /**
     * Gets the value of the mode property.
     * 
     * @return
     *     possible object is
     *     {@link Mode }
     *     
     */
    public Mode getMode() {
        return mode;
    }

    /**
     * Sets the value of the mode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Mode }
     *     
     */
    public void setMode(Mode value) {
        this.mode = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;simpleContent&gt;
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
     *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *       &lt;attribute name="ifExists" type="{http://teecu.be/toe-domains/bw6-domains}ifDomainExistsBehaviour" /&gt;
     *       &lt;attribute name="deleteWhenNotExists" type="{http://teecu.be/toe-domains/bw6-domains}deleteDomainWhenNotExistsBehaviour" default="skip" /&gt;
     *     &lt;/extension&gt;
     *   &lt;/simpleContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class Domain {

        @XmlValue
        protected String value;
        @XmlAttribute(name = "name")
        protected String name;
        @XmlAttribute(name = "ifExists")
        protected IfDomainExistsBehaviour ifExists;
        @XmlAttribute(name = "deleteWhenNotExists")
        protected DeleteDomainWhenNotExistsBehaviour deleteWhenNotExists;

        /**
         * Gets the value of the value property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getValue() {
            return value;
        }

        /**
         * Sets the value of the value property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setValue(String value) {
            this.value = value;
        }

        /**
         * Gets the value of the name property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getName() {
            return name;
        }

        /**
         * Sets the value of the name property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setName(String value) {
            this.name = value;
        }

        /**
         * Gets the value of the ifExists property.
         * 
         * @return
         *     possible object is
         *     {@link IfDomainExistsBehaviour }
         *     
         */
        public IfDomainExistsBehaviour getIfExists() {
            return ifExists;
        }

        /**
         * Sets the value of the ifExists property.
         * 
         * @param value
         *     allowed object is
         *     {@link IfDomainExistsBehaviour }
         *     
         */
        public void setIfExists(IfDomainExistsBehaviour value) {
            this.ifExists = value;
        }

        /**
         * Gets the value of the deleteWhenNotExists property.
         * 
         * @return
         *     possible object is
         *     {@link DeleteDomainWhenNotExistsBehaviour }
         *     
         */
        public DeleteDomainWhenNotExistsBehaviour getDeleteWhenNotExists() {
            if (deleteWhenNotExists == null) {
                return DeleteDomainWhenNotExistsBehaviour.SKIP;
            } else {
                return deleteWhenNotExists;
            }
        }

        /**
         * Sets the value of the deleteWhenNotExists property.
         * 
         * @param value
         *     allowed object is
         *     {@link DeleteDomainWhenNotExistsBehaviour }
         *     
         */
        public void setDeleteWhenNotExists(DeleteDomainWhenNotExistsBehaviour value) {
            this.deleteWhenNotExists = value;
        }

    }

}
