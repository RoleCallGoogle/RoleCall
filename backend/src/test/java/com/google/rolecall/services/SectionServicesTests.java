package com.google.rolecall.services;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.google.rolecall.models.Position;
import com.google.rolecall.models.Section;
import com.google.rolecall.repos.SectionRepository;
import com.google.rolecall.restcontrollers.exceptionhandling.RequestExceptions.EntityNotFoundException;
import com.google.rolecall.restcontrollers.exceptionhandling.RequestExceptions.InvalidParameterException;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class SectionServicesTests {

  private SectionServices sectionService;
  private SectionRepository sectionRepo;
  private Section existingSection;
  private int invalidId = 10;
  private int id = 1;
  private String name = "Name";
  private String notes = "Notes";
  private int length = 20;

  @BeforeEach
  public void init() throws InvalidParameterException, EntityNotFoundException {
    sectionRepo = mock(SectionRepository.class);
    sectionService = new SectionServices(sectionRepo);
    Section section = Section.newBuilder()
        .setName(name)
        .setNotes(notes)
        .setLength(length)
        .build();
    existingSection = spy(section);

    for(int i = 1; i < 4; i++) {
      Position position = Position.newBuilder()
          .setName(Integer.toString(i))
          .setNotes(Integer.toString(i*2))
          .setOrder(i)
          .setSize(i*2)
          .build();

      existingSection.addPosition(position);
      lenient().doReturn(position).when(existingSection).getPositionById(i);
    }

    lenient().doReturn(Optional.of(existingSection)).when(sectionRepo).findById(id);
    lenient().doReturn(Collections.singletonList(existingSection)).when(sectionRepo).findAll();
    lenient().doReturn(Optional.empty()).when(sectionRepo).findById(invalidId);
    lenient().when(sectionRepo.save(any(Section.class))).thenAnswer(new Answer<Section>() {
        @Override
        public Section answer(InvocationOnMock invocation) throws Throwable {
          Object[] args = invocation.getArguments();
          return (Section) args[0];
        }
    });
  }

  @Test
  public void getAllSections_success() throws Exception {
    // Excecute
    List<Section> sections = sectionService.getAllSections();

    // Assert
    assertThat(sections).containsExactly(existingSection);
  }

  @Test
  public void getSectionById_success() throws Exception {
    // Execute
    Section output = sectionService.getSection(id);

    // Assert
    assertThat(output).isEqualTo(existingSection);
  }

  @Test
  public void getSectionByInvalidId_failure() throws Exception {
    // Execute
    EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
        () -> { sectionService.getSection(invalidId); });

    // Assert
    assertThat(exception).hasMessageThat().contains(Integer.toString(invalidId));
  }

}