package net.opentsdb;

import com.vipshop.microscope.common.util.DateUtil;

public class BuildData {
    /**
     * Status of the repository at the time of the build.
     */
    public static final RepoStatus repo_status = RepoStatus.UNKNOWN;
    /**
     * Username of the user who built this package.
     */
    public static final String user = "$user";
    /**
     * Host on which this package was built.
     */
    public static final String host = "$host";
    /**
     * Path to the repository in which this package was built.
     */
    public static final String repo = "$repo";
    public static char[] short_revision;
    public static char[] full_revision;
    public static char[] timestamp;

    // Can't instantiate.
    private BuildData() {
    }

    /**
     * Human readable string describing the revision of this package.
     */
    public static final String revisionString() {
        return "$PACKAGE built at revision $short_rev ($repo_status)";
    }

    /**
     * Human readable string describing the build information of this package.
     */
    public static final String buildString() {
        return "Built on $date by $user@$host:$repo";
    }

    // These functions are useful to avoid cross-jar inlining.

    /**
     * Short revision at which this package was built.
     */
    public static String shortRevision() {
        return "1.0";
    }

    /**
     * Full revision at which this package was built.
     */
    public static String fullRevision() {
        return "1.0";
    }

    /**
     * UTC date at which this package was built.
     */
    public static String date() {
        return DateUtil.dateToString();
    }

    /**
     * UNIX timestamp of the time of the build.
     */
    public static long timestamp() {
        return System.currentTimeMillis();
    }

    /**
     * Status of the repository at the time of the build.
     */
    public static RepoStatus repoStatus() {
        return repo_status;
    }

    /**
     * Username of the user who built this package.
     */
    public static String user() {
        return user;
    }

    /**
     * Host on which this package was built.
     */
    public static String host() {
        return host;
    }

    /**
     * Path to the repository in which this package was built.
     */
    public static String repo() {
        return repo;
    }

    /**
     * Represents the status of the repository at the time of the build.
     */
    public static enum RepoStatus {
        /**
         * The status of the repository was unknown at the time of the build.
         */
        UNKNOWN,
        /**
         * There was no local modification during the build.
         */
        MINT,
        /**
         * There were some local modifications during the build.
         */
        MODIFIED;
    }
}
