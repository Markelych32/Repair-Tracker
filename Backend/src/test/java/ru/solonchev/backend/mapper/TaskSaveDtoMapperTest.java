package ru.solonchev.backend.mapper;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@Tag("Unit Test")
@ExtendWith(MockitoExtension.class)
public class TaskSaveDtoMapperTest {
    @InjectMocks
    private TaskSaveDtoMapperImpl mapper;

//    @Test
//    @DisplayName("Mapping test [TaskSaveDto -> Task]")
//    void dtoMapToEntity() {
//        TaskSaveDto dto = new TaskSaveDto(1, "title", "payload");
//        Task task = mapper.map(dto);
//        Assertions.assertAll(() -> {
//            Assertions.assertEquals("title", task.getTitle());
//            Assertions.assertEquals("payload", task.getPayload());
//        });
//    }
}
