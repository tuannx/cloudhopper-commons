package com.cloudhopper.commons.xbean;

/*
 * #%L
 * ch-commons-xbean
 * %%
 * Copyright (C) 2012 Cloudhopper by Twitter
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

// third party imports
import org.junit.*;
import org.apache.log4j.Logger;

// my imports
import com.cloudhopper.commons.xml.*;
import java.util.ArrayList;

public class XmlBeanAddTest {

    private static final Logger logger = Logger.getLogger(XmlBeanAddTest.class);

    @Test
    public void configureAdderNotSetter() throws Exception {
        StringBuilder string0 = new StringBuilder(200)
                .append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n")
                .append("<configuration>\n")
                .append("  <destinationAddress>")
                .append("    <toa>NATIONAL</toa>\n")
                .append("    <address>+13135551210</address>\n")
                .append("  </destinationAddress>")
                .append("  <destinationAddress>")
                .append("    <toa>NATIONAL</toa>\n")
                .append("    <address>+13135551211</address>\n")
                .append("  </destinationAddress>")
                .append("  <destinationAddress>")
                .append("    <toa>INTERNATIONAL</toa>\n")
                .append("    <address>+13135551212</address>\n")
                .append("  </destinationAddress>")
                .append("  <sourceAddress>")
                .append("    <toa>NATIONAL</toa>\n")
                .append("    <address>55550</address>\n")
                .append("  </sourceAddress>")
                .append("  <sourceAddress>")
                .append("    <toa>NATIONAL</toa>\n")
                .append("    <address>55551</address>\n")
                .append("  </sourceAddress>")
                .append("  <sourceAddress>")
                .append("    <toa>NATIONAL</toa>\n")
                .append("    <address>55552</address>\n")
                .append("  </sourceAddress>")
                .append("</configuration>")
                .append("");

        // parse xml
        XmlParser parser = new XmlParser();
        XmlParser.Node rootNode = parser.parse(string0.toString());

        // object we'll configure
        Configuration config = new Configuration();

        // configure it using default options
        XmlBean bean = new XmlBean();
        bean.configure(rootNode, config);

        // confirm properties
        Assert.assertEquals(3, config.destinationAddresses.size());
        Assert.assertEquals(TypeOfAddress.NATIONAL, config.destinationAddresses.get(0).toa);
        Assert.assertEquals("+13135551210", config.destinationAddresses.get(0).address);
        Assert.assertEquals(TypeOfAddress.NATIONAL, config.destinationAddresses.get(1).toa);
        Assert.assertEquals("+13135551211", config.destinationAddresses.get(1).address);
        Assert.assertEquals(TypeOfAddress.INTERNATIONAL, config.destinationAddresses.get(2).toa);
        Assert.assertEquals("+13135551212", config.destinationAddresses.get(2).address);

        Assert.assertEquals(3, config.sourceAddresses.size());
        Assert.assertEquals(TypeOfAddress.NATIONAL, config.sourceAddresses.get(0).toa);
        Assert.assertEquals("55550", config.sourceAddresses.get(0).address);
        Assert.assertEquals(TypeOfAddress.NATIONAL, config.sourceAddresses.get(1).toa);
        Assert.assertEquals("55551", config.sourceAddresses.get(1).address);
        Assert.assertEquals(TypeOfAddress.NATIONAL, config.sourceAddresses.get(2).toa);
        Assert.assertEquals("55552", config.sourceAddresses.get(2).address);
    }

    public static class Configuration {
        private ArrayList<Address> destinationAddresses;
        private ArrayList<Address> sourceAddresses;

        public Configuration() {
            this.destinationAddresses = new ArrayList<Address>();
            this.sourceAddresses = new ArrayList<Address>();
        }

        public void addDestinationAddress(Address value) {
            this.destinationAddresses.add(value);
        }

        public void addSourceAddress(Address value) {
            this.sourceAddresses.add(value);
        }
    }

    public static class Address {
        private TypeOfAddress toa;
        private String address;

        public void setToa(TypeOfAddress value) {
            this.toa = value;
        }

        public void setAddress(String value) throws ConfigurationException {
            if (value.length() < 2 || value.length() > 20) {
                throw new ConfigurationException("Address invalid length (min=2, max=20)");
            }
            this.address = value;
        }
    }

    public enum TypeOfAddress {
        NATIONAL,
        INTERNATIONAL
    }

    public static class ConfigurationException extends Exception {
        public ConfigurationException(String msg) {
            super(msg);
        }
    }

}
