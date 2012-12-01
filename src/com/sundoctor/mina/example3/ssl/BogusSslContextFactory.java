package com.sundoctor.mina.example3.ssl;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.Security;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BogusSslContextFactory {

	private static final Logger log = LoggerFactory.getLogger(BogusSslContextFactory.class);

	private static String serverKeys = "server.jks";
	private static String serverKeysPassword = "123456";
	private static String serverTrust = "server.jks";
	private static String serverTrustPassword = "123456";

	private static String clientKeys = "client.jks";
	private static String clientKeysPassword = "123456";
	private static String clientTrust = "client.jks";
	private static String clientTrustPassword = "123456";

	
	private static final String PROTOCOL = "TLS";
	private static final String KEY_MANAGER_FACTORY_ALGORITHM;	
	private static final String TRUST_MANAGER_FACTORY_ALGORITHM;

	static {
		String algorithm = Security.getProperty("ssl.KeyManagerFactory.algorithm");
		if (algorithm == null) {
			algorithm = KeyManagerFactory.getDefaultAlgorithm();
		}
		KEY_MANAGER_FACTORY_ALGORITHM = algorithm;
		
		algorithm = Security.getProperty("ssl.TrustManagerFactory.algorithm");
		if (algorithm == null) {
			algorithm = TrustManagerFactory.getDefaultAlgorithm();
		}
		TRUST_MANAGER_FACTORY_ALGORITHM = algorithm;
	}

	private static SSLContext serverInstance = null;
	private static SSLContext clientInstance = null;

	/**
	 * Get SSLContext singleton.
	 * 
	 * @return SSLContext
	 * @throws java.security.GeneralSecurityException
	 * 
	 */
	public static SSLContext getInstance(boolean server) throws GeneralSecurityException, IOException {
		SSLContext retInstance = null;
		if (server) {
			synchronized (BogusSslContextFactory.class) {
				if (serverInstance == null) {
					try {
						serverInstance = createBougusServerSslContext();
					} catch (Exception ioe) {
						throw new GeneralSecurityException("Can't create Server SSLContext:" + ioe);
					}
				}
			}
			retInstance = serverInstance;
		} else {
			synchronized (BogusSslContextFactory.class) {
				if (clientInstance == null) {
					clientInstance = createBougusClientSslContext();
				}
			}
			retInstance = clientInstance;
		}
		return retInstance;
	}

	private static SSLContext createBougusServerSslContext() throws GeneralSecurityException, IOException {

		// Initialize the SSLContext to work with our key managers.
		SSLContext sslContext = SSLContext.getInstance(PROTOCOL);
		sslContext.init(getKeyManagers(serverKeys, serverKeysPassword), getTrustManagers(serverTrust,serverTrustPassword), null);

		return sslContext;
	}

	private static SSLContext createBougusClientSslContext() throws GeneralSecurityException, IOException {
		SSLContext context = SSLContext.getInstance(PROTOCOL);
		context.init(getKeyManagers(clientKeys, clientKeysPassword), getTrustManagers(clientTrust,clientTrustPassword), null);

		return context;
	}

	private static KeyManager[] getKeyManagers(String keysfile, String password) throws GeneralSecurityException,
			IOException {
		// First, get the default KeyManagerFactory.
		KeyManagerFactory kmf = KeyManagerFactory.getInstance(KEY_MANAGER_FACTORY_ALGORITHM);
		
		// Next, set up the TrustStore to use. We need to load the file into
		// a KeyStore instance.	
		KeyStore ks = KeyStore.getInstance("JKS");
		InputStream in = BogusSslContextFactory.class.getResourceAsStream(keysfile);
		ks.load(in, password.toCharArray());
		in.close();

		// Now we initialise the KeyManagerFactory with this KeyStore	
		kmf.init(ks, password.toCharArray());
		KeyManager[] dd = kmf.getKeyManagers();
		// And now get the TrustManagers
		return dd;
	}
	
	protected static TrustManager[] getTrustManagers(String trustfile,String pasword) throws IOException, GeneralSecurityException {
		// First, get the default TrustManagerFactory.
		TrustManagerFactory tmFact = TrustManagerFactory.getInstance(TRUST_MANAGER_FACTORY_ALGORITHM);

		// Next, set up the TrustStore to use. We need to load the file into
		// a KeyStore instance.		
		InputStream in = BogusSslContextFactory.class.getResourceAsStream(trustfile);
		KeyStore ks = KeyStore.getInstance("jks");
		ks.load(in, pasword.toCharArray());
		in.close();

		// Now we initialise the TrustManagerFactory with this KeyStore
		tmFact.init(ks);

		// And now get the TrustManagers
		TrustManager[] tms = tmFact.getTrustManagers();
		return tms;
	}

}
