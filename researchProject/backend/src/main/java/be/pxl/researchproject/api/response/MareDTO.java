package be.pxl.researchproject.api.response;
import be.pxl.researchproject.domain.DiaryEntry;
import be.pxl.researchproject.domain.Roles;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MareDTO extends HorseDTO {

    private Long id;
    private boolean isPregnant;
    private Integer daysPregnant;
    private String dueDate;
    private Integer daysUntilDueDate;
    private List<DiaryEntry> diary;
    private List<LocalDate> coverings;
    public MareDTO(Long id, String name, double height, LocalDate dateOfBirth, String gender, Integer daysPregnant, LocalDate dueDate,  Integer daysUntilDueDate,  List<DiaryEntry> diary, Boolean isPregnant) {
        super(name, dateOfBirth, height, gender);
        this.id = id;
        this.isPregnant = isPregnant;
        this.daysPregnant = daysPregnant;
        this.dueDate = dueDate != null ? dueDate.toString() : "/";
        this.daysUntilDueDate = daysUntilDueDate;
        this.diary = diary;
        this.coverings = new ArrayList<>();
    }

    public MareDTO() {

    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public List<LocalDate> getCoverings() {
        return coverings;
    }

    public void setCoverings(List<LocalDate> coverings) {
        this.coverings = coverings;
    }

    public List<DiaryEntry> getDiary() {
        return diary;
    }

    public void setDiary(List<DiaryEntry> diary) {
        this.diary = diary;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate.toString();
    }

    public Boolean getPregnant() {
        return isPregnant;
    }

    public String getDaysUntilDueDate() {
        return daysUntilDueDate != null ? daysUntilDueDate.toString() : "/";
    }

    public void setDaysUntilDueDate(int daysUntilDueDate) {
        this.daysUntilDueDate = daysUntilDueDate;
    }

    public boolean isPregnant() {
        return isPregnant;
    }

    public void setPregnant(boolean pregnant) {
        isPregnant = pregnant;
    }

    public Integer getDaysPregnant() {
        return daysPregnant;
    }

    public void setDaysPregnant(Integer daysPregnant) {
        this.daysPregnant = daysPregnant;
    }
}