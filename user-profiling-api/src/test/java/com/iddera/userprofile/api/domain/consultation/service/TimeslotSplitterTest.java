package com.iddera.userprofile.api.domain.consultation.service;

import com.iddera.userprofile.api.domain.consultation.model.DateRange;
import com.iddera.userprofile.api.domain.consultation.model.Timeslot;
import com.iddera.userprofile.api.persistence.consultation.entity.DoctorTimeslot;
import com.iddera.userprofile.api.persistence.doctorprofile.entity.DoctorProfile;
import org.junit.jupiter.api.Test;

import java.time.*;

import static org.assertj.core.api.Assertions.assertThat;

class TimeslotSplitterTest {
    private final Clock clock = Clock.fixed(Instant.parse("2021-05-25T10:15:30.00Z"), ZoneId.systemDefault());

    @Test
    void splitSingleDates() {
        var timeslot = new Timeslot();
        timeslot.setDate(LocalDate.now(clock));
        timeslot.setStartTime(LocalTime.parse("10:30"));
        timeslot.setEndTime(LocalTime.parse("12:00"));

        var doctorTimeSlot = TimeslotSplitter.splitToRange(timeslot, new DoctorProfile());

        var expectedDate = LocalDate.parse("2021-05-25");
        assertThat(doctorTimeSlot)
                .extracting(DoctorTimeslot::getDate)
                .containsExactlyInAnyOrder(expectedDate, expectedDate, expectedDate);

        assertThat(doctorTimeSlot)
                .extracting(DoctorTimeslot::getStartTime)
                .containsExactlyInAnyOrder(
                        LocalTime.parse("10:30"),
                        LocalTime.parse("11:00"),
                        LocalTime.parse("11:30"));

        assertThat(doctorTimeSlot)
                .extracting(DoctorTimeslot::getEndTime)
                .containsExactlyInAnyOrder(
                        LocalTime.parse("11:00"),
                        LocalTime.parse("11:30"),
                        LocalTime.parse("12:00"));
    }

    @Test
    void splitMultipleDates() {
        var timeslot = new Timeslot();
        var startDate = LocalDate.now(clock);
        var endDate = startDate.plusDays(2);

        timeslot.setRange(new DateRange(startDate, endDate));
        timeslot.setStartTime(LocalTime.parse("10:30"));
        timeslot.setEndTime(LocalTime.parse("11:00"));

        var doctorTimeSlot = TimeslotSplitter.splitToRange(timeslot, new DoctorProfile());

        var expectedDate = LocalDate.parse("2021-05-25");
        assertThat(doctorTimeSlot)
                .extracting(DoctorTimeslot::getDate)
                .containsExactlyInAnyOrder(expectedDate, expectedDate.plusDays(1), expectedDate.plusDays(2));

        assertThat(doctorTimeSlot)
                .extracting(DoctorTimeslot::getStartTime)
                .containsExactlyInAnyOrder(
                        LocalTime.parse("10:30"),
                        LocalTime.parse("10:30"),
                        LocalTime.parse("10:30"));

        assertThat(doctorTimeSlot)
                .extracting(DoctorTimeslot::getEndTime)
                .containsExactlyInAnyOrder(LocalTime.parse("11:00"),
                        LocalTime.parse("11:00"),
                        LocalTime.parse("11:00"));
    }
}