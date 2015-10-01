package ekamp.currently.utils;

/**
 * General formatting utility class.
 *
 * @author Erik Kamp
 * @since 9/29/15.
 */
public class FormatUtils {

    /**
     * Properly formats the address for viewable purposes, we simply extract the town from our
     * address {@link String}.
     *
     * @param currentAddress the entire address received from the {@link ekamp.currently.services.LocationToAddressService}
     * @return properly formatted current town for display, if formatting error occurs input is returned
     */
    public static String extractTownFromAddress(String currentAddress) {
        int townStartLocation = currentAddress.indexOf('\n'),
                townEndLocation = currentAddress.indexOf(',');

        if (townStartLocation != -1 && townEndLocation != -1)
            return currentAddress.substring(townStartLocation, townEndLocation);
        return currentAddress;
    }
}
