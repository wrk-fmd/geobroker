package at.wrk.fmd.geobroker.data;

public final class ToStringStyle extends org.apache.commons.lang3.builder.ToStringStyle {
    public static ToStringStyle STYLE = new ToStringStyle();

    private ToStringStyle() {
        setUseShortClassName(true);
    }
}
