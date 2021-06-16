package com.iddera.userprofile.api.domain.consultation.zoom.service;

import com.iddera.userprofile.api.domain.consultation.zoom.model.ZoomMeeting;
import com.iddera.userprofile.api.domain.consultation.zoom.model.ZoomMeetingRegistrant;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.concurrent.CompletableFuture;

public interface ZoomClient {
    @POST("users/me/meetings")
    CompletableFuture<ZoomMeeting> createMeeting(@Body ZoomMeeting zoomMeeting,
                                                 @Header("Authorization") String bearerToken);

    @POST(" meetings/{meetingId}/registrants")
    CompletableFuture<ZoomMeetingRegistrant> addMeetingRegistrant(@Body ZoomMeetingRegistrant registrant,
                                                                  @Path("meetingId") String meetingId,
                                                                  @Header("Authorization") String bearerToken);

}
