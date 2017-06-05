package com.bmc.truesight.meter.plugin.remedy.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bmc.thirdparty.org.apache.commons.codec.binary.Base64;


/*
 * @author Santosh Patil
 * @Date 25-05-2017
 *
 */
public class HttpClient {

    private static final Logger LOG = LoggerFactory
            .getLogger(HttpClient.class);

    public static void pushBulkEvents(final String bulkEvents, final String apiToken, final String url) {
        Retry retry = new Retry();
        org.apache.http.client.HttpClient httpClient = null;
        while (retry.shouldRetry()) {
            try {
                httpClient = HttpClientBuilder.create().build();
                //new DefaultHttpClient();
                //HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), 10000);
                HttpPost httpPost = new HttpPost(url);
                httpPost.addHeader("Authorization", "Basic " + encodeBase64("" + ":" + apiToken));
                httpPost.addHeader("Content-Type", "application/json");
                httpPost.addHeader("accept", "application/json");
                StringEntity postingString = new StringEntity(bulkEvents);
                httpPost.setEntity(postingString);
                HttpResponse response = httpClient.execute(httpPost);
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode != Constants.TSI_STATUS_CODE) {
                    LOG.error("Enable to send events {}", response.getStatusLine());
                    break;
                } else {
                    break;
                }

            } catch (Exception e) {
                try {
                    retry.errorOccured();
                } catch (RuntimeException e1) {

                    LOG.error("Exception occured while posting bulk events {}"
                            + url, e.getMessage());
                } catch (Exception e1) {
                    LOG.error("Exception occured while posting bulk events {}",
                            e1.getMessage());
                }
            } finally {
                httpClient = null;
            }
        }
    }

    static class Retry {

        private int numberOfRetries;
        private int numberOfTriesLeft;
        private long timeToWait;

        public Retry() {
            this(Constants.TSI_DEFAULT_RETRIES, Constants.TSI_DEFAULT_WAIT_TIME_IN_MILLI);
        }

        public Retry(int numberOfRetries,
                long timeToWait) {
            this.numberOfRetries = numberOfRetries;
            numberOfTriesLeft = numberOfRetries;
            this.timeToWait = timeToWait;
        }

        /**
         * @return true if there are tries left
         */
        public boolean shouldRetry() {
            return numberOfTriesLeft > 0;
        }

        public void errorOccured() throws Exception {
            numberOfTriesLeft--;
            if (!shouldRetry()) {
                LOG.error("Retry Failed: Total " + numberOfRetries
                        + " attempts made at interval " + getTimeToWait()
                        + "ms");
            }
            waitUntilNextTry();
        }

        public long getTimeToWait() {
            return timeToWait;
        }

        private void waitUntilNextTry() {
            try {
                Thread.sleep(getTimeToWait());
            } catch (InterruptedException ignored) {
            }
        }
    }

    public static String encodeBase64(final String encodeToken) {
        byte[] encoded = Base64.encodeBase64(encodeToken.getBytes());
        return new String(encoded);
    }
}
