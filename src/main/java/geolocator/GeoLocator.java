package geolocator;

import java.net.URL;

import java.io.IOException;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.google.common.net.UrlEscapers;

import org.apache.commons.io.IOUtils;

/**
 * class for obtaining geolocation information of an ip address or host name.
 * geolocation information is obtained from <a href="https://ip-api.com/">IP-API.com</a> service
 */

public class GeoLocator {

    /**
     * URI for geolocation service.
     */

    public static final String GEOLOCATOR_SERVICE_URI = "http://ip-api.com/json/";

    private static ObjectMapper OBJECT_MAPPER = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    /**
     * Create a {@code GeoLocator} object.
     */

    public GeoLocator() {}

    /**
     * Returns Geolocation information about the JVM running the application.
     *
     * @return an object wrapping the geolocation information returned
     * @throws IOException if any I/O error occurs
     */

    public GeoLocation getGeoLocation() throws IOException {
        return getGeoLocation(null);
    }

    /**
     * Returns Geolocation information about the IP address or host name specified
     *if the argument is {@code null}, the method returns geolocation information
     * about the JVM running the application.
     *
     * @param ipAddrOrHost the IP address or host name, maybe {@code null}
     * @return an object wrapping the geolocation information returned
     * @throws IOException if any I/O error occurs
     */

    public GeoLocation getGeoLocation(String ipAddrOrHost) throws IOException {
        URL url;
        if (ipAddrOrHost != null) {
            ipAddrOrHost = UrlEscapers.urlPathSegmentEscaper().escape(ipAddrOrHost);
            url = new URL(GEOLOCATOR_SERVICE_URI + ipAddrOrHost);
        } else {
            url = new URL(GEOLOCATOR_SERVICE_URI);
        }
        String s = IOUtils.toString(url, "UTF-8");
        return OBJECT_MAPPER.readValue(s, GeoLocation.class);
    }

    // CHECKSTYLE:OFF
    public static void main(String[] args) throws IOException {
        try {
            String arg = args.length > 0 ? args[0] : null;
            System.out.println(new GeoLocator().getGeoLocation(arg));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    // CHECKSTYLE:ON
}
