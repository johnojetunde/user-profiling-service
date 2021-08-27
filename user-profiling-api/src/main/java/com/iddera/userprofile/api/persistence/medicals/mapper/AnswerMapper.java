package com.iddera.userprofile.api.persistence.medicals.mapper;

import com.iddera.userprofile.api.domain.medicalinfo.model.AnswerModel;
import com.iddera.userprofile.api.persistence.EntityToDomainMapper;
import com.iddera.userprofile.api.persistence.medicals.entity.Answer;
import com.iddera.userprofile.api.persistence.userprofile.entity.UserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static java.util.Optional.ofNullable;

@RequiredArgsConstructor
@Component
public class AnswerMapper implements EntityToDomainMapper<AnswerModel, Answer> {

    private final QuestionMapper questionMapper;

    @Override
    public Answer toEntity(AnswerModel model) {
        return toEntity(model, model.getId());
    }

    @Override
    public Answer toEntity(AnswerModel model, Long id) {
        return Answer.builder()
                .id(id)
                .options(model.getAnswers())
                .question(questionMapper.toEntity(model.getQuestion()))
                .username(model.getUsername())
                .user(ofNullable(model.getUser())
                        .map(UserProfile::fromModel)
                        .orElse(null))
                .build();
    }

    @Override
    public AnswerModel toModel(Answer entity) {
        return AnswerModel.builder()
                .id(entity.getId())
                .answers(entity.getOptions())
                .question(questionMapper.toModel(entity.getQuestion()))
                .username(entity.getUsername())
                .user(ofNullable(entity.getUser())
                        .map(UserProfile::toModel)
                        .orElse(null))
                .build();
    }
}