package com.iddera.userprofile.api.domain.consultation.service;

import com.iddera.userprofile.api.domain.consultation.model.Timeslot;
import com.iddera.userprofile.api.domain.consultation.model.TimeslotRange;
import com.iddera.userprofile.api.persistence.consultation.entity.DoctorTimeslot;
import com.iddera.userprofile.api.persistence.doctorprofile.entity.DoctorProfile;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import static com.iddera.commons.utils.FunctionUtil.emptyIfNullStream;
import static com.iddera.userprofile.api.domain.consultation.model.TimeslotStatus.FREE;
import static com.iddera.userprofile.api.domain.utils.Constants.CONSULTATION_PERIOD;
import static java.util.Objects.nonNull;


public class TimeslotSplitter {

    private TimeslotSplitter() {
    }

    public static Set<DoctorTimeslot> splitToRange(Timeslot slot, DoctorProfile doctor) {
        return splitRange(doctor, slot);
    }

    private static Set<DoctorTimeslot> splitRange(DoctorProfile doctor, Timeslot slot) {
        if (nonNull(slot.getDate()))
            return splitTimeslots(doctor, slot.getDate(), slot.getStartTime(), slot.getEndTime());

        return splitTimeslots(doctor, slot);
    }

    private static Set<DoctorTimeslot> splitTimeslots(DoctorProfile doctor, Timeslot timeslot) {
        LocalDate startDate = timeslot.getRange().getFrom();
        LocalDate endDate = timeslot.getRange().getTo();
        LocalDate currentDate = startDate;

        var slots = splitTimeslots(timeslot.getStartTime(), timeslot.getEndTime());
        var doctorTimeSlots = new HashSet<DoctorTimeslot>();

        while (!currentDate.isAfter(endDate)) {
            addToDoctorTimeSlot(doctor, currentDate, slots, doctorTimeSlots);
            currentDate = currentDate.plusDays(1);
        }

        return doctorTimeSlots;
    }

    private static Set<TimeslotRange> splitTimeslots(LocalTime from, LocalTime to) {
        Set<TimeslotRange> range = new HashSet<>();
        LocalTime currentTime = from;

        while (currentTime.isBefore(to)) {
            var prev = currentTime;
            currentTime = currentTime.plusMinutes(CONSULTATION_PERIOD);
            range.add(new TimeslotRange(prev, currentTime));
        }

        return range;
    }

    private static Set<DoctorTimeslot> splitTimeslots(DoctorProfile doctorId,
                                                      LocalDate date,
                                                      LocalTime from,
                                                      LocalTime to) {
        Set<DoctorTimeslot> slots = new HashSet<>();
        LocalTime currentTime = from;

        while (currentTime.isBefore(to)) {
            var prev = currentTime;
            currentTime = currentTime.plusMinutes(CONSULTATION_PERIOD);
            slots.add(toSlot(doctorId, date, prev, currentTime));
        }

        return slots;
    }

    private static void addToDoctorTimeSlot(DoctorProfile doctor,
                                            LocalDate date,
                                            Set<TimeslotRange> ranges,
                                            Set<DoctorTimeslot> doctorTimeslot) {
        emptyIfNullStream(ranges)
                .map(r -> toSlot(doctor, date, r.getStartTime(), r.getEndTime()))
                .forEach(doctorTimeslot::add);
    }

    private static DoctorTimeslot toSlot(DoctorProfile doctor,
                                         LocalDate date,
                                         LocalTime startTime,
                                         LocalTime endTime) {
        return new DoctorTimeslot()
                .setDoctor(doctor)
                .setDate(date)
                .setStartTime(startTime)
                .setEndTime(endTime)
                .setStatus(FREE);
    }
}
