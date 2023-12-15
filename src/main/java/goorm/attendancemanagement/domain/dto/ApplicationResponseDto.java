package goorm.attendancemanagement.domain.dto;

import goorm.attendancemanagement.domain.dao.Session;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationResponseDto {

    private int applicationId;
    private LocalDate applicationTargetDate;
    private String applicationType;
    private String applicationStatus;
    private String applicationReason;
    private List<String> sessionName;
    private String fileName;
}