package com.iddera.userprofile.api.persistence.consultation.persistence;

import com.iddera.userprofile.api.persistence.consultation.entity.Consultation;
import com.iddera.userprofile.api.persistence.consultation.entity.Consultation_;
import com.iddera.userprofile.api.persistence.consultation.entity.DoctorTimeslot_;
import com.iddera.userprofile.api.persistence.consultation.specs.SearchCriteria;
import com.iddera.userprofile.api.persistence.consultation.specs.SearchOperation;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.iddera.userprofile.api.persistence.consultation.specs.SearchOperation.*;

public class ConsultationSpecification implements Specification<Consultation> {

    private final List<SearchCriteria> list;

    public ConsultationSpecification() {
        this.list = new ArrayList<>();
    }

    public void add(SearchCriteria criteria) {
        list.add(criteria);
    }

    @Override
    public Predicate toPredicate(Root<Consultation> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

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
            } else if (criteria.getOperation().equals(TIMESLOT)) {
                predicates.add(
                        builder.equal(
                                root.get(Consultation_.timeslot).get(DoctorTimeslot_.id), criteria.getValue()));

            }
        }

        return builder.and(predicates.toArray(new Predicate[predicates.size()]));
    }
}