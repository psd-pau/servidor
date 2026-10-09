package cat.paucasesnoves.unitat2.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.util.List;

/**
 * Ampliació de publicació: les respostes tenen una forma finita.
 * Les peticions de cursos referencien professors i estudiants existents per ID.
 */
public final class UniversityDto {
    private UniversityDto() {}

    public record TeacherInput(@NotBlank @Size(max = 255) String fullName,
                               @Size(max = 255) String department) {}

    public record StudentInput(@NotBlank @Size(max = 255) String fullName,
                               @Email @Size(max = 255) String email) {}

    public record CourseInput(@NotBlank @Size(max = 255) String name,
                              @PositiveOrZero int credits,
                              @Positive Long teacherId,
                              List<@Positive Long> studentIds) {}

    public record TeacherView(Long id, String fullName, String department) {}
    public record StudentView(Long id, String fullName, String email) {}
    public record CourseView(Long id, String name, int credits,
                             TeacherView teacher, List<StudentView> students) {}
}
