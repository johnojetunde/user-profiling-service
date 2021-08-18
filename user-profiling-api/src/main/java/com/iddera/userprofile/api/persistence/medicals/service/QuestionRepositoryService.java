package com.iddera.userprofile.api.persistence.medicals.service;

import com.iddera.userprofile.api.domain.exception.UserProfilingExceptionService;
import com.iddera.userprofile.api.domain.phonotype.model.QuestionModel;
import com.iddera.userprofile.api.persistence.EntityToDomainMapper;
import com.iddera.userprofile.api.persistence.medicals.entity.Question;
import com.iddera.userprofile.api.persistence.medicals.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionRepositoryService extends DefaultCrudRepositoryService<QuestionModel, Question> {
    private final UserProfilingExceptionService exceptionService;
    private final QuestionRepository repository;

    public QuestionRepositoryService(EntityToDomainMapper<QuestionModel, Question> mapper,
                                     QuestionRepository repository,
                                     UserProfilingExceptionService exceptionService) {
        super(mapper, repository);
        this.exceptionService = exceptionService;
        this.repository = repository;
    }

    @Override
    public Optional<Question> getByUsername(String username) {
        throw exceptionService.handleCreateNotImplementedException("Get By Username not implemented for Question entity");
    }

    public Optional<QuestionModel> findByQuestion(String question) {
        return repository.findByQuestionIgnoreCase(question)
                .map(mapper::toModel);
    }

    @Override
    public List<Question> getAllByUsername(String username) {
        return repository.findAll();
    }
}
