package at.wrk.fmd.geobroker.contract;

import at.wrk.fmd.geobroker.data.ToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class Response implements Serializable {
    private static final long serialVersionUID = 1L;

    private StatusCode statusCode;
    private String statusDescription;
    private Serializable payload;

    public StatusCode getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(final StatusCode statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(final String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public Serializable getPayload() {
        return payload;
    }

    public void setPayload(final Serializable payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.STYLE)
                .append("statusCode", statusCode)
                .append("statusDescription", statusDescription)
                .append("payload", payload)
                .toString();
    }

    public static class Builder {
        private final Response response;

        private Builder() {
            response = new Response();
        }

        public static Builder createSuccessful() {
            Builder builder = new Builder();
            builder.response.setStatusCode(StatusCode.OK);
            return builder;
        }

        public static Builder createError() {
            Builder builder = new Builder();
            builder.response.setStatusCode(StatusCode.ERROR);
            return builder;
        }

        public Builder withDescription(final String description) {
            response.setStatusDescription(description);
            return this;
        }

        public Builder withPayload(final Serializable payload) {
            response.setPayload(payload);
            return this;
        }

        public Response build() {
            return response;
        }
    }
}

