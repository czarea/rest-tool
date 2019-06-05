package com.czarea.rest.config;

import java.util.LinkedHashSet;
import java.util.Set;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zhouzx
 */
@ConfigurationProperties(prefix = "httpclient")
public class HttpClientProperties {

    /**
     * 正常可接受的状态
     */
    private Set<Integer> acceptableStatus = new LinkedHashSet<>();

    /**
     * 最大连接数
     */
    private int maxTotalConnections;
    private int defaultMaxPerRoute;
    private long defaultKeepAliveTimeMillis;
    private int requestTimeout;
    private int connectTimeout;
    private int socketTimeout;
    private long closeIdleConnectionWaitTimeSecs;

    public int getMaxTotalConnections() {
        return maxTotalConnections;
    }

    public void setMaxTotalConnections(int maxTotalConnections) {
        this.maxTotalConnections = maxTotalConnections;
    }

    public int getDefaultMaxPerRoute() {
        return defaultMaxPerRoute;
    }

    public void setDefaultMaxPerRoute(int defaultMaxPerRoute) {
        this.defaultMaxPerRoute = defaultMaxPerRoute;
    }

    public long getDefaultKeepAliveTimeMillis() {
        return defaultKeepAliveTimeMillis;
    }

    public void setDefaultKeepAliveTimeMillis(long defaultKeepAliveTimeMillis) {
        this.defaultKeepAliveTimeMillis = defaultKeepAliveTimeMillis;
    }

    public int getRequestTimeout() {
        return requestTimeout;
    }

    public void setRequestTimeout(int requestTimeout) {
        this.requestTimeout = requestTimeout;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public long getCloseIdleConnectionWaitTimeSecs() {
        return closeIdleConnectionWaitTimeSecs;
    }

    public void setCloseIdleConnectionWaitTimeSecs(long closeIdleConnectionWaitTimeSecs) {
        this.closeIdleConnectionWaitTimeSecs = closeIdleConnectionWaitTimeSecs;
    }

    public Set<Integer> getAcceptableStatus() {
        return acceptableStatus;
    }

    public void setAcceptableStatus(Set<Integer> acceptableStatus) {
        this.acceptableStatus = acceptableStatus;
    }
}
