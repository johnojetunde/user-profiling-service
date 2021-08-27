package com.iddera.userprofile.api.persistence.medicals.service;

import com.iddera.userprofile.api.domain.exception.UserProfilingExceptionService;
import com.iddera.userprofile.api.domain.medicalinfo.model.AnswerModel;
import com.iddera.userprofile.api.persistence.EntityToDomainMapper;
import com.iddera.userprofile.api.persistence.medicals.entity.Answer;
import com.iddera.userprofile.api.persistence.medicals.repository.AnswerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.iddera.commons.utils.FunctionUtil.emptyIfNullStream;
import static java.util.stream.Collectors.toList;

@Service
public class AnswerRepositoryService extends DefaultCrudRepositoryService<AnswerModel, Answer> {

    private final UserProfilingExceptionService exceptionService;
    private final AnswerRepository repository;

    public AnswerRepositoryService(EntityToDomainMapper<AnswerModel, Answer> mapper,
                                   AnswerRepository repository,
                                   UserProfilingExceptionService exceptionService) {
        super(mapper, repository);
        this.exceptionService = exceptionService;
        this.repository = repository;
    }

    @Override
    public Optional<Answer> getByUsername(String username) {
        throw exceptionService.handleCreateNotImplementedException("Get By Username not implemented for Answer entity");
    }

    public Optional<AnswerModel> getByQuestionAndUsername(Long questionId, String username) {
        return repository.findByUserNameAndQuestion(questionId, username)
                .map(mapper::toModel);
    }

    public List<AnswerModel> getByQuestionsAndUsername(Set<Long> questionIds, String username) {
        return emptyIfNullStream(repository.findAllByUserNameAndQuestions(questionIds, username))
                .map(mapper::toModel)
                .collect(toList());
    }

    public List<AnswerModel> getByQuestions(Set<Long> questionIds) {
        return emptyIfNullStream(repository.findAllByQuestions(questionIds))
                .map(mapper::toModel)
                .collect(toList());
    }

    @Override
    public List<Answer> getAllByUsername(String username) {
        return repository.findAllByUsernameIgnoreCase(username);
    }
}
