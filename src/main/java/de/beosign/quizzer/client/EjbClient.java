package de.beosign.quizzer.client;
//package de.beosign.cashflow.client;
//
//import java.util.Hashtable;
//import java.util.Properties;
//
//import javax.ejb.EJB;
//import javax.naming.Context;
//import javax.naming.InitialContext;
//import javax.naming.NamingException;
//
//import de.beosign.cashflow.model.Account;
//import de.beosign.cashflow.service.AccountService;
//import de.beosign.cashflow.service.AccountServiceBean;
//
//public class EjbClient {
//
//    @EJB
//    private AccountService accountService;
//
//    public static void main(String[] args) throws NamingException {
//        AccountService service = lookupAccountService2();
//        System.out.println(service);
//        System.out.println(service.create(new Account()));
//        System.out.println(service.find(2));
//    }
//
//    private static AccountService lookupAccountService2() throws NamingException {
//        final Hashtable<String, String> jndiProperties = new Hashtable<>();
//        jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
//        jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
//        String urlName = "http-remoting://localhost:8080";
//        // p.put("java.naming.factory.initial", "org.jnp.interfaces.NamingContextFactory");
//        jndiProperties.put("java.naming.provider.url", urlName);
//        final Context context = new InitialContext(jndiProperties);
//
//        return (AccountService) context.lookup("CashFlow//AccountServiceBean!de.beosign.cashflow.service.AccountService");
//    }
//
//    private static AccountService lookupAccountService() throws NamingException {
//        final Hashtable<String, String> jndiProperties = new Hashtable<>();
//        jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
//        Properties p = new Properties();
//        String urlName = "http-remoting://localhost:8080";
//        // p.put("java.naming.factory.initial", "org.jnp.interfaces.NamingContextFactory");
//        p.put("java.naming.provider.url", urlName);
//        p.put("java.naming.factory.url.pkgs", "org.jboss.ejb.client.naming");
//        final Context context = new InitialContext(p);
//        // The app name is the application name of the deployed EJBs. This is typically the ear name
//        // without the .ear suffix. However, the application name could be overridden in the application.xml of the
//        // EJB deployment on the server.
//        // Since we haven't deployed the application as a .ear, the app name for us will be an empty string
//        final String appName = "CashFlow";
//        // This is the module name of the deployed EJBs on the server. This is typically the jar name of the
//        // EJB deployment, without the .jar suffix, but can be overridden via the ejb-jar.xml
//        // In this example, we have deployed the EJBs in a jboss-as-ejb-remote-app.jar, so the module name is
//        // jboss-as-ejb-remote-app
//        final String moduleName = AccountServiceBean.class.getSimpleName();
//        // AS7 allows each deployment to have an (optional) distinct name. We haven't specified a distinct name for
//        // our EJB deployment, so this is an empty string
//        final String distinctName = "";
//        // The EJB name which by default is the simple class name of the bean implementation class
//        final String beanName = AccountServiceBean.class.getSimpleName();
//        // the remote view fully qualified class name
//        final String viewClassName = AccountService.class.getName();
//        // let's do the lookup
//        Context ejbContext = (Context) context.lookup("ejb:");
//
//        return (AccountService) ejbContext.lookup("CashFlow//AccountServiceBean!de.beosign.cashflow.service.AccountService");
//    }
//
// }
