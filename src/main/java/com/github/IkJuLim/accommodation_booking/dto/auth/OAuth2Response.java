package com.github.IkJuLim.accommodation_booking.dto.auth;

public interface OAuth2Response {

    String getProvider();

    String getProviderId();

    String getEmail();

    String getName();
}