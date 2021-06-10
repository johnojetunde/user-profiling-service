package com.iddera.userprofile.api.domain.consultation.zoom.service;

import com.iddera.usermanagement.lib.domain.model.UserType;
import com.iddera.userprofile.api.app.config.MeetingConfig;
import com.iddera.userprofile.api.app.config.ZoomConfig;
import com.iddera.userprofile.api.domain.consultation.model.MeetingParticipant;
import com.iddera.userprofile.api.domain.consultation.service.MeetingDetails;
import com.iddera.userprofile.api.domain.consultation.zoom.model.ZoomMeeting;
import com.iddera.userprofile.api.domain.consultation.zoom.model.ZoomMeetingRegistrant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.*;
import java.util.List;

import static java.util.concurrent.CompletableFuture.completedFuture;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ZoomMeetingProviderTest {

    private final Clock clock = Clock.fixed(Instant.parse("2021-05-25T10:15:30.00Z"), ZoneId.systemDefault());
    @Mock
    private ZoomClient zoomClient;
    @Mock
    private ZoomConfig zoomConfig;
    @Mock
    private MeetingConfig meetingConfig;
    private ZoomMeetingProvider provider;

    @BeforeEach
    void setUp() {
        provider = new ZoomMeetingProvider(zoomConfig, meetingConfig, zoomClient, clock);
    }

    @Test
    void scheduleMeeting() {
        var participant = buildMeetingParticipant();
        var meetingDetails = buildMeetingDetails(participant);

        when(zoomConfig.getApiKey()).thenReturn("APIKEYAlMO2peDME0q44uOwsjaL6N3");
        when(zoomConfig.getApiSecret()).thenReturn("AQJHWQj8e3s2RPYlMO2peDME0qaL6N312345");
        when(zoomConfig.getTokenExpiryDuration()).thenReturn(20L);
        when(zoomClient.createMeeting(isA(ZoomMeeting.class), anyString()))
                .then(i -> {
                    var meet = i.getArgument(0, ZoomMeeting.class);
                    meet.setId("meetingId");
                    return completedFuture(meet);
                });

        when(zoomClient.addMeetingRegistrant(isA(ZoomMeetingRegistrant.class), eq("meetingId"), anyString()))
                .then((i -> {
                    var reg = i.getArgument(0, ZoomMeetingRegistrant.class);
                    reg.setJoinUrl("joinUrl");
                    reg.setId("regId");
                    reg.setRegistrantId("registrantId");
                    return completedFuture(reg);
                }));

        var result = provider.scheduleMeet(meetingDetails).join();

        assertThat(result.get(0).getMeetingId()).isEqualTo("meetingId");
        assertThat(result.get(0).getRegistrantId()).isEqualTo("registrantId");
        assertThat(result.get(0).getMeetingPasscode()).isNotBlank();
        assertThat(result.get(0).getMeetingUrl()).isEqualTo("joinUrl");

        verify(zoomConfig, times(2)).getApiKey();
        verify(zoomConfig, times(2)).getApiSecret();
        verify(zoomConfig, times(2)).getTokenExpiryDuration();
        verify(zoomClient, times(1)).createMeeting(isA(ZoomMeeting.class), anyString());
    }

    private MeetingParticipant buildMeetingParticipant() {
        return MeetingParticipant.builder()
                .userId(1)
                .email("john.ojetunde@telesoftas.com")
                .firstname("Oluwayemisi")
                .lastname("Ooreofeoluwa")
                .userType(UserType.CLIENT)
                .build();
    }

    private MeetingDetails buildMeetingDetails(MeetingParticipant participant) {
        return MeetingDetails.builder()
                .participants(List.of(participant))
                .date(LocalDate.now().plusDays(1))
                .time(LocalTime.of(13, 30))
                .agenda("Consultation /Health Check")
                .title("Consultation /Health Check")
                .duration(30)
                .build();
    }
}
