package com.villo.alkemychallenge.integrations.decoders;

import com.villo.alkemychallenge.utils.errors.IntegrationException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorBaseFormatDecoder implements ErrorDecoder {
    @Override
    public Exception decode(final String methodKey, final Response response) {
        return new IntegrationException(response.reason(), response.status());
    }
}
