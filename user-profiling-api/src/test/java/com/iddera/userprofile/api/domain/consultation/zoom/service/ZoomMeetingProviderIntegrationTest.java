package com.iddera.userprofile.api.domain.consultation.zoom.service;

import com.iddera.usermanagement.lib.domain.model.UserType;
import com.iddera.userprofile.api.domain.consultation.model.MeetingParticipant;
import com.iddera.userprofile.api.domain.consultation.service.MeetingDetails;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Disabled("Only run to test ZOOM API")
@SpringBootTest
class ZoomMeetingProviderIntegrationTest {

    @Autowired
    private ZoomMeetingProvider provider;

    @Test
    void testZoomProvider() {
        var participant = MeetingParticipant.builder()
                .userId(1L)
                .email("john.ojetunde@telesoftas.com")
                .firstname("Oluwayemisi")
                .lastname("Ooreofeoluwa")
                .userType(UserType.CLIENT)
                .build();

        MeetingDetails meetingDetails = MeetingDetails.builder()
                .participants(List.of(participant))
                .date(LocalDate.now().plusDays(1))
                .time(LocalTime.of(13, 30))
                .agenda("Consultation /Health Check")
                .title("Consultation /Health Check")
                .duration(30)
                .build();

        var result = provider.scheduleMeet(meetingDetails).join();
        System.out.println(result);
    }
}