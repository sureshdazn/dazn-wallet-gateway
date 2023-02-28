package com.dazn.httpclient.config;


import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;

import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
 
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContexts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class HttpClientConfig {
 
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientConfig.class);
 
	@Value("${connection.time.out}")
    private int CONNECT_TIMEOUT;
    
	@Value("${request.time.out}")
	private int REQUEST_TIMEOUT;
    
	@Value("${socket.time.out}")
	private int SOCKET_TIMEOUT;
 
	@Value("${max.total.connections}")
    private int MAX_TOTAL_CONNECTIONS;
	
	@Value("${keep.alive.time}")
    private int DEFAULT_KEEP_ALIVE_TIME_MILLIS;
   
	@Value("${idle.connection.wait.time}")
	private int CLOSE_IDLE_CONNECTION_WAIT_TIME_SECS;
    
	@Value("${enable.twoway.ssl}")
    private boolean ENABLE_TWO_WAY_SSL;
    
	SSLContext sslcontext = null;
    SSLConnectionSocketFactory sslsf = null;

    @Bean
    PoolingHttpClientConnectionManager poolingConnectionManager() {

        if (ENABLE_TWO_WAY_SSL) {

            try {

                sslcontext = SSLContexts.custom().loadTrustMaterial(new File("my.keystore"), "nopassword".toCharArray(),
                        new TrustSelfSignedStrategy()).build();

                sslsf = new SSLConnectionSocketFactory(sslcontext, new String[]{"TLSv1.2", "TLSv1.3"},
                        null, SSLConnectionSocketFactory.getDefaultHostnameVerifier());
            } catch (Exception e) {
                LOGGER.error("Pooling Connection Manager Initialisation failure because of " + e.getMessage(), e);
            }

        }

        PoolingHttpClientConnectionManager poolingConnectionManager = new PoolingHttpClientConnectionManager();
        poolingConnectionManager.setMaxTotal(MAX_TOTAL_CONNECTIONS);

        return poolingConnectionManager;

    }

    @Bean
    ConnectionKeepAliveStrategy connectionKeepAliveStrategy() {

        return new ConnectionKeepAliveStrategy() {

            @Override
            public long getKeepAliveDuration(HttpResponse response, HttpContext context) {

                HeaderElementIterator it = new BasicHeaderElementIterator(response.headerIterator(HTTP.CONN_KEEP_ALIVE));

                while (it.hasNext()) {
                    HeaderElement he = it.nextElement();
                    String param = he.getName();
                    String value = he.getValue();

                    if (value != null && param.equalsIgnoreCase("timeout")) {
                        return Long.parseLong(value) * 1000;
                    }
                }

                return DEFAULT_KEEP_ALIVE_TIME_MILLIS;

            }
        };
    }

    @Bean
    CloseableHttpClient httpClient() {

        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(REQUEST_TIMEOUT)
                .setConnectTimeout(CONNECT_TIMEOUT)
                .setSocketTimeout(SOCKET_TIMEOUT).build();

        CloseableHttpClient httpClient = null;

        if ((sslcontext != null) && (sslsf != null)) {

            httpClient = HttpClients.custom().setSSLSocketFactory(sslsf)
                    .setDefaultRequestConfig(requestConfig)
                    .setConnectionManager(poolingConnectionManager())
                    .setKeepAliveStrategy(connectionKeepAliveStrategy())
                    .build();

        } else {

            httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig)
                    .setConnectionManager(poolingConnectionManager())
                    .setKeepAliveStrategy(connectionKeepAliveStrategy())
                    .build();

        }

        return httpClient;
    }

    @Bean
    Runnable idleConnectionMonitor(final PoolingHttpClientConnectionManager connectionManager) {
        return new Runnable() {

            @Override
            @Scheduled(fixedDelay = 10000)
            public void run() {

                try {

                    if (connectionManager != null) {

                        LOGGER.trace("run IdleConnectionMonitor - Closing expired and idle connections...");
                        connectionManager.closeExpiredConnections();
                        connectionManager.closeIdleConnections(CLOSE_IDLE_CONNECTION_WAIT_TIME_SECS, TimeUnit.SECONDS);

                    } else {
                        LOGGER.trace("run IdleConnectionMonitor - Http Client Connection manager is not initialised");
                    }

                } catch (Exception e) {
                    LOGGER.error("run IdleConnectionMonitor - Exception occurred. msg={}, e={}", e.getMessage(), e);
                }

            }
        };
    }
}