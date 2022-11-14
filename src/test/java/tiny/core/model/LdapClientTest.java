/*
 * Copyright 2021 bythe.
 *
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
 */
package tiny.core.model;

import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchResult;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author bythe
 */
public class LdapClientTest {

    public LdapClientTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of loadProperty method, of class LdapClient.
     */
    @Test
    public void testLoadProperty() {
        System.out.println("loadProperty");
        String filePath = "target/classes/tinycore.properties";
        LdapClient instance = new LdapClient();
        instance.loadProperty(filePath);
    }

    /**
     * Test of getProperty method, of class LdapClient.
     */
    @Test
    public void testGetProperty() {
        System.out.println("getProperty");
        String key = LdapClient.LDAP_URL;
        LdapClient instance = new LdapClient();
        String filePath = "target/classes/tinycore.properties";
        instance.loadProperty(filePath);

        String result = instance.getProperty(key);
        assertNotNull(result);
    }

    /**
     * Test of setPartition method, of class LdapClient.
     */
    @Test
    public void testSetPartition() {
        System.out.println("setPartition");
        String partition = "cn=foo,dc=var";
        LdapClient instance = new LdapClient();
        instance.setPartition(partition);
        assertEquals(partition, instance.getPartition());
    }

    /**
     * Test of main method, of class LdapClient.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        LdapClient client = new LdapClient();
        client.loadProperty("target/classes/tinycore.properties");
        client.init();
        NamingEnumeration results = client.get(
                client.getProperty("test_ldap_search_node"),
                client.getProperty("test_ldap_search_filter")
        );

        try {
            while (results != null && results.hasMore()) {
                SearchResult si = (SearchResult) results.next();

                /* エントリ名の出力 */
                System.out.println("name: " + si.getName());

                Attributes attrs = si.getAttributes();
                if (attrs == null) {
                    System.out.println("No attributes");
                } else {
                    /* 属性の出力 */
                    for (NamingEnumeration ae = attrs.getAll();
                            ae.hasMoreElements();) {
                        Attribute attr = (Attribute) ae.next();
                        String attrId = attr.getID();

                        /* 属性値の出力 */
                        for (Enumeration vals = attr.getAll();
                                vals.hasMoreElements();
                                System.out.println(attrId + ": " + vals.nextElement()));
                    }
                }
                System.out.println();
            }
        } catch (Exception ex) {
            Logger.getLogger(LdapClient.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Test of get method, of class LdapClient.
     */
    @Test
    public void testGet() {
        System.out.println("get");
        LdapClient instance = new LdapClient();
        instance.loadProperty("target/classes/tinycore.properties");

        String node = instance.getProperty("test_ldap_search_node");
        String filter = instance.getProperty("test_ldap_search_filter");

        NamingEnumeration result = instance.get(node, filter);
        assertNotNull(result);
    }

    /**
     * Test of auth method, of class LdapClient.
     */
    @Test
    public void testAuth() {
        System.out.println("auth");
        LdapClient instance = new LdapClient();
        instance.loadProperty("target/classes/tinycore.properties");

        String uid = instance.getProperty("test_ldap_user");
        String password = instance.getProperty("test_ldap_password");

        boolean expResult = true;
        boolean result = instance.auth(uid, password);
        assertEquals(expResult, result);
    }

}
