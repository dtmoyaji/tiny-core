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

import java.util.HashMap;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;

/**
 * Ldap Client
 *
 * @author daianjimax
 */
public class LdapClient {

    public static final String LDAP_URL = "ldap_url";
    public static final String LDAP_ADMIN_CONTEXT = "ldap_admin_context";
    public static final String LDAP_ADMIN_PASSWORD = "ldap_admin_password";

    private PropertyReader propReader = null;

    private String base_principal = "";

    private String admin_password = "";

    private HashMap<String, String> env;
    private DirContext context;

    private String partition;

    public LdapClient() {
        this.env = new HashMap<>();
    }

    public void loadProperty(String filePath) {
        this.propReader = new PropertyReader(filePath);
        this.init();
    }

    public String getProperty(String key) {
        return this.propReader.getProperty(key);
    }

    public void setPartition(String partition) {
        this.partition = partition;
    }

    public String getPartition() {
        return this.partition;
    }

    public static void main(String[] args) {

    }

    public void init() {

        this.env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        this.env.put(Context.SECURITY_AUTHENTICATION, "simple");

        this.env.put(Context.PROVIDER_URL, this.propReader.getProperty(LdapClient.LDAP_URL));
        this.base_principal = this.propReader.getProperty(LdapClient.LDAP_ADMIN_CONTEXT);
        this.admin_password = this.propReader.getProperty(LdapClient.LDAP_ADMIN_PASSWORD);

    }

    public NamingEnumeration get(String node, String filter) {
        NamingEnumeration results = null;
        try {
            SearchControls constraints = new SearchControls();
            constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);

            this.env.put(Context.SECURITY_PRINCIPAL, this.base_principal);
            this.env.put(Context.SECURITY_CREDENTIALS, this.admin_password);

            this.context = new InitialDirContext(new Hashtable<>(this.env));
            results = this.context.search(node, filter, constraints);
            this.context.close();

        } catch (NamingException ex) {
            Logger.getLogger(LdapClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return results;
    }

    public boolean auth(String uid, String password) {
        boolean res = false;

        //LDAP接続情報
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, uid); //ID, 組織
        env.put(Context.SECURITY_CREDENTIALS, password); //パスワード

        try {
            this.context = new InitialDirContext(new Hashtable<>(env));
            this.context.close();
            System.out.println("auth ok");
            res = true;
        } catch (NamingException ex) {
            Logger.getLogger(LdapClient.class.getName()).log(Level.SEVERE, null, ex);
        }

        return res;
    }

}
