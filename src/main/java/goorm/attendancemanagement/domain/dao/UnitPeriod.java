package goorm.attendancemanagement.domain.dao;

import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.time.LocalDate;

@Embeddable
@Getter
public class UnitPeriod {

    private LocalDate firstUnitPeriod;
    private LocalDate secondUnitPeriod;
    private LocalDate thirdUnitPeriod;
    private LocalDate fourthUnitPeriod;
    private LocalDate fifthUnitPeriod;
    private LocalDate sixthUnitPeriod;
    private LocalDate seventhUnitPeriod;

    public UnitPeriod() {
    }

    public UnitPeriod(LocalDate firstUnitPeriod, LocalDate secondUnitPeriod, LocalDate thirdUnitPeriod, LocalDate fourthUnitPeriod, LocalDate fifthUnitPeriod, LocalDate sixthUnitPeriod, LocalDate seventhUnitPeriod) {
        this.firstUnitPeriod = firstUnitPeriod;
        this.secondUnitPeriod = secondUnitPeriod;
        this.thirdUnitPeriod = thirdUnitPeriod;
        this.fourthUnitPeriod = fourthUnitPeriod;
        this.fifthUnitPeriod = fifthUnitPeriod;
        this.sixthUnitPeriod = sixthUnitPeriod;
        this.seventhUnitPeriod = seventhUnitPeriod;
    }
}