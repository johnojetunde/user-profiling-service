package com.iddera.userprofile.api.persistence.consultation.persistence;

import com.iddera.userprofile.api.persistence.consultation.entity.DoctorTimeslot;
import com.iddera.userprofile.api.persistence.consultation.entity.DoctorTimeslot_;
import com.iddera.userprofile.api.persistence.consultation.specs.SearchCriteria;
import com.iddera.userprofile.api.persistence.consultation.specs.SearchOperation;
import com.iddera.userprofile.api.persistence.doctorprofile.entity.DoctorProfile_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.iddera.userprofile.api.persistence.consultation.specs.SearchOperation.*;


public class TimeslotSpecification implements Specification<DoctorTimeslot> {

    private final List<SearchCriteria> list;

    public TimeslotSpecification() {
        this.list = new ArrayList<>();
    }

    public void add(SearchCriteria criteria) {
        list.add(criteria);
    }

    @Override
    public Predicate toPredicate(Root<DoctorTimeslot> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        //create a new predicate list
        List<Predicate> predicates = new ArrayList<>();

        //add add criteria to predicates
        for (SearchCriteria criteria : list) {
            if (criteria.getOperation().equals(SearchOperation.GREATER_THAN)) {
                predicates.add(builder.greaterThan(
                        root.get(criteria.getKey()), criteria.getValue().toString()));
            } else if (criteria.getOperation().equals(SearchOperation.LESS_THAN)) {
                predicates.add(builder.lessThan(
                        root.get(criteria.getKey()), criteria.getValue().toString()));
            } else if (criteria.getOperation().equals(GREATER_THAN_EQUAL) && criteria.getValue() instanceof LocalDateTime) {
                predicates.add(builder.greaterThanOrEqualTo(
                        root.get(criteria.getKey()),
                        LocalDateTime.parse(criteria.getValue().toString())));
            } else if (criteria.getOperation().equals(LESS_THAN_EQUAL) && criteria.getValue() instanceof LocalDateTime) {
                predicates.add(builder.lessThanOrEqualTo(
                        root.get(criteria.getKey()), LocalDateTime.parse(criteria.getValue().toString())));
            } else if (criteria.getOperation().equals(NOT_EQUAL)) {
                predicates.add(builder.notEqual(
                        root.get(criteria.getKey()), criteria.getValue()));
            } else if (criteria.getOperation().equals(EQUAL)) {
                predicates.add(builder.equal(
                        root.get(criteria.getKey()), criteria.getValue()));
            } else if (criteria.getOperation().equals(MATCH)) {
                predicates.add(builder.like(
                        builder.lower(root.get(criteria.getKey())),
                        "%" + criteria.getValue().toString().toLowerCase() + "%"));
            } else if (criteria.getOperation().equals(MATCH_END)) {
                predicates.add(builder.like(
                        builder.lower(root.get(criteria.getKey())),
                        criteria.getValue().toString().toLowerCase() + "%"));
            } else if (criteria.getOperation().equals(MATCH_START)) {
                predicates.add(builder.like(
                        builder.lower(root.get(criteria.getKey())),
                        "%" + criteria.getValue().toString().toLowerCase()));
            } else if (criteria.getOperation().equals(SearchOperation.IN)) {
                predicates.add(builder.in(
                        root.get(criteria.getKey()))
                        .value(criteria.getValue()));
            } else if (criteria.getOperation().equals(NOT_IN)) {
                predicates.add(builder.not(root.get(criteria.getKey())).in(criteria.getValue()));
            } else if (criteria.getOperation().equals(DOCTOR_ID)) {
                predicates.add(
                        builder.equal(
                                root.get(DoctorTimeslot_.doctor).get(DoctorProfile_.id), criteria.getValue()));
            } else if (criteria.getOperation().equals(DOCTOR_USER_ID)) {
                predicates.add(
                        builder.equal(
                                root.get(DoctorTimeslot_.doctor).get(DoctorProfile_.userId), criteria.getValue()));
            }
        }

        return builder.and(predicates.toArray(new Predicate[predicates.size()]));
    }
}