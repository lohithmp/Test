   try {
                var sslContext = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();
                var httpClient = HttpClient.create().secure(sslContextSpec -> sslContextSpec.sslContext(sslContext));
                client = WebClient.builder().clientConnector(new ReactorClientHttpConnector(httpClient)).baseUrl(baseUrl).build();
            } catch (SSLException sslException) {
                logger.error("SSLException while creating ApiClient for: {}, error msg: {}", baseUrl, sslException.getMessage());
                client = WebClient.builder().baseUrl(baseUrl).build();
            }


An Error Occurred While Fetching Data:

javax.net.ssl.SSLHandshakeException: PKIX path building failed: sun.security.provider.certpath.SunCertPathBuilderException: unable to find valid certification path to requested target
	at java.base/sun.security.ssl.Alert.createSSLException(Alert.java:130)
	at java.base/sun.security.ssl.TransportContext.fatal(TransportContext.java:378)
	at 
