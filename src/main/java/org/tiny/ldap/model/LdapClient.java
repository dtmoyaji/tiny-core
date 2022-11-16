package org.tiny.ldap.model;

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
import javax.naming.directory.SearchResult;

/**
 * Ldap Client
 *
 * @author daianjimax
 */
public class LdapClient {

    public static final String LDAP_URL = "ldap_url";
    public static final String LDAP_ADMIN_CONTEXT = "ldap_admin_context";
    public static final String LDAP_ADMIN_PASSWORD = "ldap_admin_password";

    private final HashMap<String, String> env;
    private DirContext context;

    private String partition;

    public LdapClient() {
        this.env = new HashMap<>();
        this.env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        this.env.put(Context.SECURITY_AUTHENTICATION, "simple");
    }

    /**
     * プロパティファイルに記述した設定値を格納する。
     * 
     * @param filePath
     */
    public void loadProperty(String filePath) {
        PropertyReader propReader = new PropertyReader(filePath);
        this.setLdapUrl(propReader.getProperty(LdapClient.LDAP_URL));
        this.setAdminContext(propReader.getProperty(LdapClient.LDAP_ADMIN_CONTEXT));
        this.setAdminPassword(propReader.getProperty(LdapClient.LDAP_ADMIN_PASSWORD));
    }

    public void setLdapUrl(String url) {
        this.env.put(Context.PROVIDER_URL, url);
    }

    public String getLdapUrl() {
        return this.env.get(Context.PROVIDER_URL);
    }

    public String getProperty(String key) {
        return this.env.get(key);
    }

    public void setPartition(String partition) {
        this.partition = partition;
    }

    public String getAdminPassword() {
        return this.env.get(LDAP_ADMIN_PASSWORD);
    }

    public void setAdminPassword(String password) {
        this.env.put(LDAP_ADMIN_PASSWORD, password);
    }

    public String getPartition() {
        return this.partition;
    }

    public void setAdminContext(String principal) {
        this.env.put(LDAP_ADMIN_CONTEXT, principal);
    }

    public String getAdminContext() {
        return this.env.get(LDAP_ADMIN_CONTEXT);
    }

    public NamingEnumeration<SearchResult> get(String node, String filter) {
        return get(node, filter, new String[] { "*" });
    }

    /**
     * LDAP内を検索する
     * 
     * @param node
     * @param filter
     * @param returnAttributes
     * @return 検索結果
     */
    public NamingEnumeration<SearchResult> get(String node, String filter, String[] returnAttributes) {
        NamingEnumeration<SearchResult> results = null;
        try {
            SearchControls constraints = new SearchControls();
            constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
            constraints.setReturningAttributes(returnAttributes);

            // 管理者で検索
            this.env.put(Context.SECURITY_PRINCIPAL, this.getAdminContext());
            this.env.put(Context.SECURITY_CREDENTIALS, this.getAdminPassword());

            this.context = new InitialDirContext(new Hashtable<>(this.env));
            results = this.context.search(node, filter, constraints);
            this.context.close();

        } catch (NamingException ex) {
            Logger.getLogger(LdapClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return results;
    }

    /**
     * ユーザー認証を行う
     * 
     * @param uid
     * @param password
     * @return true-可 false-不可
     */
    public boolean auth(String uid, String password) {
        boolean res = false;

        // ユーザーで検索
        this.env.put(Context.SECURITY_PRINCIPAL, uid); // ID, 組織
        this.env.put(Context.SECURITY_CREDENTIALS, password); // パスワード

        try {
            this.context = new InitialDirContext(new Hashtable<>(this.env));
            this.context.close();
            Logger.getLogger(LdapClient.class.getName()).log(Level.INFO, "Authenticated.");
            res = true;
        } catch (NamingException ex) {
            Logger.getLogger(LdapClient.class.getName()).log(Level.SEVERE, null, ex);
        }

        return res;
    }

}
