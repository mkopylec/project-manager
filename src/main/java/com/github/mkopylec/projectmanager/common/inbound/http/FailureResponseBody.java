package com.github.mkopylec.projectmanager.common.inbound.http;

import java.util.Map;
import java.util.StringJoiner;

import static com.github.mkopylec.projectmanager.common.inbound.http.FailureResponseBody.FailureCodeResponseBody;
import static com.github.mkopylec.projectmanager.common.support.MapUtils.toUnmodifiable;

public abstract class FailureResponseBody<C extends Enum<C> & FailureCodeResponseBody> {

    private final C code;
    private final Map<String, Object> properties;

    protected FailureResponseBody(C code) {
        this(code, null);
    }

    protected FailureResponseBody(C code, Map<String, Object> properties) {
        this.code = code;
        this.properties = toUnmodifiable(properties);
    }

    public C getCode() {
        return code;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", this.getClass().getSimpleName() + "[", "]")
                .add("code=" + code)
                .add("properties='" + properties + "'")
                .toString();
    }

    public interface FailureCodeResponseBody {

    }
}
