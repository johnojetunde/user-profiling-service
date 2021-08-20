package com.iddera.userprofile.api.persistence.medicals.mapper;

import com.iddera.userprofile.api.domain.medicalinfo.model.QuestionModel;
import com.iddera.userprofile.api.persistence.EntityToDomainMapper;
import com.iddera.userprofile.api.persistence.medicals.entity.Question;
import org.springframework.stereotype.Component;

@Component
public class QuestionMapper implements EntityToDomainMapper<QuestionModel, Question> {

    @Override
    public Question toEntity(QuestionModel model) {
        return toEntity(model, model.getId());
    }

    @Override
    public Question toEntity(QuestionModel model, Long id) {
        return Question.builder()
                .description(model.getDescription())
                .maxOption(model.getMaxOptions())
                .minOption(model.getMinOptions())
                .options(model.getOptions())
                .id(model.getId())
                .build();
    }

    @Override
    public QuestionModel toModel(Question entity) {
        return QuestionModel.builder()
                .description(entity.getDescription())
                .options(entity.getOptions())
                .maxOptions(entity.getMaxOption())
                .minOptions(entity.getMinOption())
                .id(entity.getId())
                .build();
    }
}
