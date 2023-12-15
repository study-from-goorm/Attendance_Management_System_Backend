package goorm.attendancemanagement.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationRequestDto {

    private LocalDate applicationTargetDate;
    private String applicationType;
    private String applicationReason;
    private List<String> sessionName;
    private MultipartFile file;
}
