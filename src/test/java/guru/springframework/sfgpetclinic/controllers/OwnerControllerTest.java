package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.fauxspring.BindingResult;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    private static final String OWNERS_CREATE_OR_UPDATE_OWNER_FORM = "owners/createOrUpdateOwnerForm";
    private static final String REDIRECT_OWNERS_5 = "redirect:/owners/5";

    @Mock
    OwnerService service;

    @InjectMocks
    OwnerController controller;

    @Mock
    BindingResult bindingResult;

    @Captor
    ArgumentCaptor<String> captor;

    @Test
    void processFindFormWildCardString(){
        //given
        Owner owner = new Owner(1L,"Joe","Buck");
        List<Owner> owners = new ArrayList<>();
        given(service.findAllByLastNameLike(captor.capture())).willReturn(owners);

        //when
        String viewName = controller.processFindForm(owner,bindingResult,null);

        //then
        assertThat("%Buck%").isEqualToIgnoringCase(captor.getValue());
    }


    @Test
    void processCreationFormHasErrors() {
        //given
        Owner owner = new Owner(1L,"Gent","Kastrati");
        given(bindingResult.hasErrors()).willReturn(true);

        //when
        String viewName = controller.processCreationForm(owner,bindingResult);

        //then
        assertThat(viewName).isEqualToIgnoringCase(OWNERS_CREATE_OR_UPDATE_OWNER_FORM);

    }

    @Test
    void processCreationFormNoErrors(){
        //given
        Owner owner = new Owner(5L,"Gent","Kastrati");
        given(bindingResult.hasErrors()).willReturn(false);
        given(service.save(any())).willReturn(owner);

        //when
        String viewName = controller.processCreationForm(owner,bindingResult);

        //then
       assertThat(viewName).isEqualToIgnoringCase(REDIRECT_OWNERS_5);

    }
}