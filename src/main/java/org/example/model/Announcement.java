package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "announcements")
@Slf4j
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title")
    @NotNull
    @NotBlank
    @Size(max = 255)
    private String title;

    @Column(name = "description")
    @NotNull
    @NotBlank
    @Size(max = 3000)
    private String description;

    @Column(name = "_when")
    @PastOrPresent
    private LocalDate date;

    @Column(name = "award")
    private Double award;

    @Column(name = "an_type")
    @Enumerated(value = EnumType.STRING)
    @NotNull
    private Type type;

    @Column(name = "user_id")
    @Positive
    private int authorId;

    public boolean announcementValid(boolean isCreate) {
        if (isCreate) {
            if (id != null) {
                log.error("Объявление невалидное");
                return false;
            }
        }
        if (date.isBefore( LocalDate.of(2022, 9, 1))) {
            log.error("Объявление невалидное");
            return false;
        }
        if (type.equals(Type.LOST)) {
            if (award < 1.0) {
                log.error("Объявление невалидное");
                return false;
            }
        }
        if (type.equals(Type.HAS_FOUND)) {
            if (award != null) {
                log.error("Объявление невалидное");
                return false;
            }
        }
        return true;
    }
}
