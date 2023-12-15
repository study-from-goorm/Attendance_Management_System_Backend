package goorm.attendancemanagement.upload;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
public class UploadFile {

    private String uploadFileName;
    private String storeFileName;
    private String fileFormat;

    public UploadFile(String uploadFileName, String storeFileName, String fileFormat) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
        this.fileFormat = fileFormat;
    }
}
