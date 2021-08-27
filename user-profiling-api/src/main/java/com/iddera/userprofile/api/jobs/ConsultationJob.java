package com.iddera.userprofile.api.jobs;

import com.iddera.userprofile.api.domain.consultation.service.DoctorTimeslotService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ConsultationJob {

    private final DoctorTimeslotService timeslotService;

    @Scheduled(cron = "${cron.consultation.delete-expired-slots:0 0 0 * * ?}")
    public void deleteExpiredTimeslots() {
        timeslotService.deleteExpiredAndUnusedSlots().join();
    }
}
