/*
 * Copyright (c) 2018 Red Cross Vienna and contributors. All rights reserved.
 *
 * This software may be modified and distributed under the terms of the MIT license. See the LICENSE file for details.
 */

package at.wrk.fmd.geobroker.integration;

final class UrlHelper {
    private UrlHelper() {
    }

    static String privateApiUrl(final String suffix) {
        return "/api/v1/private" + suffix;
    }

    static String publicApiUrl(final String suffix) {
        return "/api/v1/public" + suffix;
    }
}
