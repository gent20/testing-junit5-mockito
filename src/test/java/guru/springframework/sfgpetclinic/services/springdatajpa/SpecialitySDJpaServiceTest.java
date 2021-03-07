package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.repositories.SpecialtyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.BDDMockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpecialitySDJpaServiceTest {

    @Mock
    SpecialtyRepository specialtyRepository;

    @InjectMocks
    SpecialitySDJpaService service;


    @Test
    void testDeleteByObject(){
        //given
        Speciality speciality = new Speciality();

        //when
        service.delete(speciality);

        //then
        BDDMockito.then(specialtyRepository).should().delete(any(Speciality.class));
    }

    @Test
    void deleteById() {
        //given - none

        //when
        service.deleteById(1L);
        service.deleteById(1L);

        //then
        BDDMockito.then(specialtyRepository).should(times(2)).deleteById(1L);
    }

    @Test
    void testDelete() {
        //when
        service.delete(new Speciality());

        //then
        BDDMockito.then(specialtyRepository).should().delete(any());
    }

    @DisplayName("Find a specialty by ID in BDD approach")
    @Test
    void findByIdBddTest(){
        //given
        Speciality speciality = new Speciality();
        given(specialtyRepository.findById(1L)).willReturn(Optional.of(speciality));

        //when
        Speciality savedSpecialty = service.findById(1L);

        //then
        assertThat(savedSpecialty).isNotNull();
        BDDMockito.then(specialtyRepository).should().findById(anyLong());
    }

}