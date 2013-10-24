package com.vipshop.microscope.trace.http;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.RequestAddCookies;
import org.apache.http.client.protocol.RequestAuthCache;
import org.apache.http.client.protocol.RequestClientConnControl;
import org.apache.http.client.protocol.RequestDefaultHeaders;
import org.apache.http.client.protocol.RequestProxyAuthentication;
import org.apache.http.client.protocol.RequestTargetAuthentication;
import org.apache.http.client.protocol.ResponseAuthCache;
import org.apache.http.client.protocol.ResponseProcessCookies;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.params.SyncBasicHttpParams;
import org.apache.http.protocol.BasicHttpProcessor;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.RequestContent;
import org.apache.http.protocol.RequestExpectContinue;
import org.apache.http.protocol.RequestTargetHost;
import org.apache.http.protocol.RequestUserAgent;
import org.apache.http.util.VersionInfo;

import com.vipshop.microscope.trace.Tracer;
import com.vipshop.microscope.trace.span.Category;

public class TraceHttpClient extends AbstractHttpClient {

	/**
	 * Creates a new HTTP client from parameters and a connection manager.
	 * 
	 * @param params
	 *            the parameters
	 * @param conman
	 *            the connection manager
	 */
	public TraceHttpClient(ClientConnectionManager conman, HttpParams params) {
		super(conman, params);
	}

	/**
	 * @since 4.1
	 */
	public TraceHttpClient(final ClientConnectionManager conman) {
		super(conman, null);
	}

	public TraceHttpClient(final HttpParams params) {
		super(null, params);
	}

	public TraceHttpClient() {
		super(null, null);
	}
	
	public final HttpResponse executeInTrace(HttpUriRequest request) throws IOException, ClientProtocolException {
		Tracer.clientSend(request, Category.HTTP_CALL);
		HttpResponse response = super.execute(request);
		Tracer.clientReceive();
		return response;
	} 

	/**
	 * Creates the default set of HttpParams by invoking
	 * {@link DefaultHttpClient#setDefaultHttpParams(HttpParams)}
	 * 
	 * @return a new instance of {@link SyncBasicHttpParams} with the defaults
	 *         applied to it.
	 */
	@Override
	protected HttpParams createHttpParams() {
		HttpParams params = new SyncBasicHttpParams();
		setDefaultHttpParams(params);
		return params;
	}

	/**
	 * Saves the default set of HttpParams in the provided parameter. These are:
	 * <ul>
	 * <li>{@link CoreProtocolPNames#PROTOCOL_VERSION}: 1.1</li>
	 * <li>{@link CoreProtocolPNames#HTTP_CONTENT_CHARSET}: ISO-8859-1</li>
	 * <li>{@link CoreConnectionPNames#TCP_NODELAY}: true</li>
	 * <li>{@link CoreConnectionPNames#SOCKET_BUFFER_SIZE}: 8192</li>
	 * <li>{@link CoreProtocolPNames#USER_AGENT}: Apache-HttpClient/<release>
	 * (java 1.5)</li>
	 * </ul>
	 */
	public static void setDefaultHttpParams(HttpParams params) {
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(params, HTTP.DEFAULT_CONTENT_CHARSET);
		HttpConnectionParams.setTcpNoDelay(params, true);
		HttpConnectionParams.setSocketBufferSize(params, 8192);

		// determine the release version from packaged version info
		final VersionInfo vi = VersionInfo.loadVersionInfo("org.apache.http.client", DefaultHttpClient.class.getClassLoader());
		final String release = (vi != null) ? vi.getRelease() : VersionInfo.UNAVAILABLE;
		HttpProtocolParams.setUserAgent(params, "Apache-HttpClient/" + release + " (java 1.5)");
	}

	@Override
	protected BasicHttpProcessor createHttpProcessor() {
		BasicHttpProcessor httpproc = new BasicHttpProcessor();
		httpproc.addInterceptor(new RequestDefaultHeaders());
		// Required protocol interceptors
		httpproc.addInterceptor(new RequestContent());
		httpproc.addInterceptor(new RequestTargetHost());
		// Recommended protocol interceptors
		httpproc.addInterceptor(new RequestClientConnControl());
		httpproc.addInterceptor(new RequestUserAgent());
		httpproc.addInterceptor(new RequestExpectContinue());
		// HTTP state management interceptors
		httpproc.addInterceptor(new RequestAddCookies());
		httpproc.addInterceptor(new ResponseProcessCookies());
		// HTTP authentication interceptors
		httpproc.addInterceptor(new RequestAuthCache());
		httpproc.addInterceptor(new ResponseAuthCache());
		httpproc.addInterceptor(new RequestTargetAuthentication());
		httpproc.addInterceptor(new RequestProxyAuthentication());
		return httpproc;
	}

}
