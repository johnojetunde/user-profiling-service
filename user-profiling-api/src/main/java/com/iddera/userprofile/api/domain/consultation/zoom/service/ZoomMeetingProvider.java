package com.iddera.userprofile.api.domain.consultation.zoom.service;

import com.iddera.userprofile.api.app.config.MeetingConfig;
import com.iddera.userprofile.api.app.config.ZoomConfig;
import com.iddera.userprofile.api.domain.consultation.model.MeetingParticipant;
import com.iddera.userprofile.api.domain.consultation.model.MeetingProviderKey;
import com.iddera.userprofile.api.domain.consultation.model.RegisteredParticipant;
import com.iddera.userprofile.api.domain.consultation.service.MeetingDetails;
import com.iddera.userprofile.api.domain.consultation.service.MeetingProvider;
import com.iddera.userprofile.api.domain.consultation.zoom.model.ZoomMeeting;
import com.iddera.userprofile.api.domain.consultation.zoom.model.ZoomMeetingRegistrant;
import com.iddera.userprofile.api.domain.consultation.zoom.model.ZoomSettings;
import com.iddera.userprofile.api.domain.utils.StringUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static com.iddera.usermanagement.lib.domain.utils.FunctionUtil.emptyIfNullStream;
import static java.util.stream.Collectors.toList;

@Primary
@RequiredArgsConstructor
@Component
public class ZoomMeetingProvider implements MeetingProvider {

    private static final int SCHEDULE_MEETING_TYPE = 2;
    private static final int NEEDS_TO_ALWAYS_REGISTER = 2;
    private static final int AUTO_APPROVAL = 0;
    private static final String WEST_AFRICA_TIMEZONE = "Africa/Bangui";

    private final ZoomConfig config;
    private final MeetingConfig meetingConfig;
    private final ZoomClient zoomClient;
    private final Clock clock;

    @Override
    public MeetingProviderKey key() {
        return MeetingProviderKey.ZOOM;
    }

    @Override
    public CompletableFuture<List<RegisteredParticipant>> scheduleMeet(MeetingDetails meetingDetails) {
        var zoomSettings = buildSettings();
        var passcode = StringUtil.generatePasscode();
        var meeting = ZoomMeeting.builder()
                .agenda(meetingDetails.getAgenda())
                .duration(meetingDetails.getDuration())
                .startTime(LocalDateTime.of(meetingDetails.getDate(), meetingDetails.getTime()))
                .type(SCHEDULE_MEETING_TYPE)
                .password(passcode)
                .timezone(WEST_AFRICA_TIMEZONE)
                .settings(zoomSettings)
                .build();

        var bearerToken = generateBearerToken();

        return zoomClient.createMeeting(meeting, bearerToken)
                .thenApply(response -> emptyIfNullStream(meetingDetails.getParticipants())
                        .map(part -> scheduleMeet(part, response, passcode))
                        .map(CompletableFuture::join)
                        .collect(toList()));
    }

    private CompletableFuture<RegisteredParticipant> scheduleMeet(MeetingParticipant participant,
                                                                  ZoomMeeting meeting,
                                                                  String passcode) {
        var registrant = toRegistrant(participant, meeting.getTopic(), meeting.getStartTime());
        var bearerToken = generateBearerToken();

        return zoomClient.addMeetingRegistrant(registrant, meeting.getId(), bearerToken)
                .thenApply(reg -> RegisteredParticipant.builder()
                        .meetingId(meeting.getId())
                        .registrantId(reg.getRegistrantId())
                        .meetingUrl(reg.getJoinUrl())
                        .meetingPasscode(passcode)
                        .participantDetails(participant)
                        .build());
    }

    private String generateBearerToken() {
        String id = UUID.randomUUID().toString().replace("-", "");
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        Date creation = new Date(clock.millis());
        Date tokenExpiry = new Date(clock.millis() + config.getTokenExpiryDuration() * 1000);

        Key key = Keys
                .hmacShaKeyFor(config.getApiSecret().getBytes());
        var token = Jwts.builder()
                .setId(id)
                .setIssuer(config.getApiKey())
                .setIssuedAt(creation)
                .setSubject("")
                .setExpiration(tokenExpiry)
                .signWith(key, signatureAlgorithm)
                .compact();
        return "Bearer " + token;
    }

    private ZoomSettings buildSettings() {
        return ZoomSettings.builder()
                .contactName(meetingConfig.getContactName())
                .allowMultipleDevices(true)
                .joinBeforeHost(true)
                .participantVideo(true)
                .registrantsEmailNotification(true)
                .contactEmail(meetingConfig.getContactEmail())
                .hostVideo(true)
                .approvalType(AUTO_APPROVAL)
                .registrationType(NEEDS_TO_ALWAYS_REGISTER)
                .build();
    }

    private ZoomMeetingRegistrant toRegistrant(MeetingParticipant participant,
                                               String title,
                                               LocalDateTime startTime) {
        return ZoomMeetingRegistrant.builder()
                .email(participant.getEmail())
                .firstname(participant.getFirstname())
                .lastName(participant.getLastname())
                .autoApprove(true)
                .topic(title)
                .startTime(startTime)
                .build();
    }
}
