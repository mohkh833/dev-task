package com.project.movies.movie;

import com.project.movies.common.BaseEntity;
import com.project.movies.feedback.Feedback;
import com.project.movies.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Movie extends BaseEntity {

    private String title;

    private String poster;

    private String Released;

    @OneToMany(mappedBy = "movie")
    private List<Feedback> feedbacks;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;


    @Transient
    public double getRate() {
        if(feedbacks == null || feedbacks.isEmpty()) return 0.0;
        var rate = this.feedbacks.stream()
                .mapToDouble(Feedback::getNote)
                .average()
                .orElse(0.0);
        double roundedRate = Math.round(rate * 10.0) / 10.0;
        return roundedRate;
    }
}
