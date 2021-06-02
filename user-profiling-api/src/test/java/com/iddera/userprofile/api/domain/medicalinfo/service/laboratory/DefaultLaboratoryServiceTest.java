package com.iddera.userprofile.api.domain.medicalinfo.service.laboratory;

import com.iddera.userprofile.api.domain.exception.UserProfilingException;
import com.iddera.userprofile.api.domain.exception.UserProfilingExceptionService;
import com.iddera.userprofile.api.domain.lab.model.ContactInfo;
import com.iddera.userprofile.api.domain.lab.model.LaboratoryModel;
import com.iddera.userprofile.api.domain.lab.model.WorkingHour;
import com.iddera.userprofile.api.domain.medicalinfo.service.abstracts.LaboratoryService;
import com.iddera.userprofile.api.domain.medicalinfo.service.concretes.DefaultLaboratoryService;
import com.iddera.userprofile.api.persistence.medicals.entity.Laboratory;
import com.iddera.userprofile.api.persistence.medicals.repository.LaboratoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class DefaultLaboratoryServiceTest {
    @Mock
    private LaboratoryRepository repository;

    private LaboratoryService laboratoryService;

    @BeforeEach
    void setUp() {
        openMocks(this);
        var exception = new UserProfilingExceptionService();
        laboratoryService = new DefaultLaboratoryService(repository,exception);
    }

    @Test
    void findById() {
        when(repository.findById(1L))
                .thenReturn(Optional.of(buildUpdateLaboratory()));

        var result = laboratoryService.findById(1L).join();

        assertLaboratoryValues(result);
        verify(repository).findById(1L);
    }

    @Test
    void findAll() {
        when(repository.findAll())
                .thenReturn(List.of(buildLaboratory()));

        var result = laboratoryService.findAll().join();

        assertThat(result.isEmpty()).isFalse();
        verify(repository).findAll();
    }

    @Test
    void createFails_WhenEmailExists() {
        when(repository.existsByContactInfo_Email(eq("sanmi@iddera.com")))
                .thenReturn(true);
        when(repository.save(any(Laboratory.class)))
                .thenReturn(buildLaboratory());

        var result = laboratoryService.update(toModel(buildLaboratory()));

        assertThatThrownBy(result::join)
                .isInstanceOf(CompletionException.class)
                .hasCause(new UserProfilingException("Email already exists for another laboratory."))
                .extracting(Throwable::getCause)
                .hasFieldOrPropertyWithValue("code", BAD_REQUEST.value());
    }

    @Test
    void create_Successfully() {
        Laboratory laboratory = buildLaboratory();
        laboratory.setId(1L);
        when(repository.existsByContactInfo_Email(eq("sanmi@iddera.com")))
                .thenReturn(false);
        when(repository.save(any(Laboratory.class)))
                .thenReturn(laboratory);

        var result = laboratoryService.update(toModel(buildLaboratory())).join();
        assertLaboratoryValues(result);
    }

    @Test
    void update_Successfully() {
        when(repository.findById(any(Long.class)))
                .thenReturn(Optional.of(buildUpdateLaboratory()));
        when(repository.save(any(Laboratory.class)))
                .thenReturn(buildUpdateLaboratory());

        var result = laboratoryService.update(toModel(buildUpdateLaboratory())).join();
        assertLaboratoryValues(result);
    }

    private Laboratory buildLaboratory(){
        Laboratory laboratory = new Laboratory();
        laboratory.setSpecialisation("Medical center");
        laboratory.setWorkingHour(workingHour());
        laboratory.setAdditionalInfo("This is a special medical centre");
        laboratory.setName("Lexx medical centre");
        laboratory.setAddress("90, seattle street, USA");
        laboratory.setContactInfo(contactInfo());

        return laboratory;
    }

    private Laboratory buildUpdateLaboratory(){
        Laboratory laboratory = new Laboratory();
        laboratory.setId(1L);
        laboratory.setSpecialisation("Medical center");
        laboratory.setWorkingHour(workingHour());
        laboratory.setAdditionalInfo("This is a special medical centre");
        laboratory.setName("Lexx medical centre");
        laboratory.setAddress("90, seattle street, USA");
        laboratory.setContactInfo(contactInfo());

        return laboratory;
    }

    private ContactInfo contactInfo(){
        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setEmail("sanmi@iddera.com");
        contactInfo.setLineOne("09084845988");
        contactInfo.setLineTwo("09084845988");

        return contactInfo;
    }

    private WorkingHour workingHour(){
        WorkingHour workingHour = new WorkingHour();
        workingHour.setStartAt(LocalTime.of(12,0));
        workingHour.setEndAt(LocalTime.of(15,30));

        return workingHour;
    }

    private LaboratoryModel toModel(Laboratory laboratory){
        return laboratory.toModel();
    }

    private void assertLaboratoryValues(LaboratoryModel laboratoryModel){
        assertThat(laboratoryModel.getId()).isEqualTo(1L);
        assertThat(laboratoryModel.getName()).isEqualToIgnoringCase("Lexx medical centre");
        assertThat(laboratoryModel.getSpecialisation()).isEqualToIgnoringCase("Medical center");
        assertThat(laboratoryModel.getAdditionalInfo()).isEqualToIgnoringCase("This is a special medical centre");
        assertThat(laboratoryModel.getAddress()).isEqualToIgnoringCase("90, seattle street, USA");
        assertThat(laboratoryModel.getWorkingHour().getStartAt()).isEqualTo(LocalTime.of(12,0));
        assertThat(laboratoryModel.getWorkingHour().getEndAt()).isEqualTo(LocalTime.of(15,30));
        assertThat(laboratoryModel.getContactInfo().getEmail()).isEqualToIgnoringCase("sanmi@iddera.com");
        assertThat(laboratoryModel.getContactInfo().getLineOne()).isEqualToIgnoringCase("09084845988");
        assertThat(laboratoryModel.getContactInfo().getLineTwo()).isEqualToIgnoringCase("09084845988");
    }
}
