/*package org.dbs.mydoc.data.format;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import in.transerv.commons.utils.IOUtils;

public class RequestHelper {

  private static final Logger LOGGER = LoggerFactory.getLogger(RequestHelper.class);
  public static int TIMEOUT = 45000;// Default is 45 seconds

  private DefaultRedirectStrategy getRedirection() {
    DefaultRedirectStrategy redirectStrategy = new DefaultRedirectStrategy() {
      @Override
      public boolean isRedirected(HttpRequest request, HttpResponse response, HttpContext context) {
        boolean isRedirect = false;
        try {
          isRedirect = super.isRedirected(request, response, context);
        } catch (ProtocolException e) {
          LOGGER.error(e.getMessage(), e);
        }
        if (!isRedirect) {
          int responseCode = response.getStatusLine().getStatusCode();
          if (responseCode == 301 || responseCode == 302) {
            return true;
          }
        }
        return false;
      }
    };
    return redirectStrategy;
  }

  private CloseableHttpClient getHttpClient() {
    // TODO use proper host verifier
    return HttpClients.custom().setSSLHostnameVerifier(new NoopHostnameVerifier())
        .setRedirectStrategy(getRedirection()).build();
  }

  public String connectAndPost(String url, Map<String, String> dataMap, Map<String, String> headers)
      throws IOException {
    CloseableHttpClient httpclient = getHttpClient();

    HttpPost httpPost = new HttpPost(url);
    for (String key : headers.keySet()) {
      httpPost.addHeader(key, headers.get(key));
    }
    List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();

    for (String key : dataMap.keySet()) {
      urlParameters.add(new BasicNameValuePair(key, dataMap.get(key)));
    }
    String content = null;
    CloseableHttpResponse response = null;
    try {
      httpPost.setEntity(new UrlEncodedFormEntity(urlParameters));
      response = httpclient.execute(httpPost);
      HttpEntity entity = response.getEntity();
      content = IOUtils.toString(entity.getContent());
      entity.getContent().close();
    } finally {
      try {
        if (response != null) {
          response.close();
        }
        if (httpclient != null) {
          httpclient.close();
        }
      } catch (IOException e) {
        LOGGER.error(e.getMessage(), e);
      }
    }
    return content;
  }

  public Map<String, String> getCommonHeaders() {
    Map<String, String> map = new HashMap<>();
    map.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*;q=0.8");
    map.put("Accept-Language", "en-US,en;q=0.5");
    map.put("User-Agent", " Mozilla/5.0 (Windows NT 6.3; WOW64; rv:46.0) Gecko/20100101 Firefox/46.0");
    return map;
  }

  public String connectAndPost(String url, Map<String, String> data) throws IOException {
    return connectAndPost(url, data, getCommonHeaders());
  }

  public String connectAndPost(String url, String data, Map<String, String> headers)
      throws IOException {
    CloseableHttpClient clientFirstRequest = null;
    HttpPost post = null;
    CloseableHttpResponse httpResponse = null;

    clientFirstRequest = HttpClients.createDefault();
    post = new HttpPost(url);
    post.setEntity(new StringEntity(data, "UTF-8"));

    for (String key : headers.keySet()) {
      post.addHeader(key, headers.get(key));
    }
    String content = null;
    try {
      httpResponse = clientFirstRequest.execute(post);
      content = IOUtils.toString(httpResponse.getEntity().getContent());
      httpResponse.getEntity().getContent().close();
    } finally {
      try {
        if (httpResponse != null) {
          httpResponse.close();
        }
        if (clientFirstRequest != null) {
          clientFirstRequest.close();
        }
      } catch (IOException e) {
        LOGGER.error(e.getMessage(), e);
      }
    }
    return content;
  }

  public String connectAndGet(String url, Map<String, String> data) {
    CloseableHttpClient httpclient = getHttpClient();
    List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();

    for (String key : data.keySet()) {
      urlParameters.add(new BasicNameValuePair(key, data.get(key)));
    }

    CloseableHttpResponse httpResponse = null;
    String response = null;

    try {
      URIBuilder builder = new URIBuilder(url).setParameters(urlParameters);
      HttpGet httpGet = new HttpGet(builder.build());

      httpResponse = httpclient.execute(httpGet);
      response = IOUtils.toString(httpResponse.getEntity().getContent());
    } catch (Exception e) {
      LOGGER.error("Error making a get call to {}", url, e);
    } finally {
      try {
        if (httpResponse != null) {
          httpResponse.close();
        }
        if (httpclient != null) {
          httpclient.close();
        }
      } catch (IOException ie) {
        LOGGER.error("Error clearing up connections", ie);
      }
    }
    return response;
  }
}
*/