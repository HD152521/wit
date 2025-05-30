package com.arom.with_travel.domain.likes;

import com.arom.with_travel.domain.accompanies.model.Accompany;
import com.arom.with_travel.domain.member.Member;
import com.arom.with_travel.domain.shorts.Shorts;
import com.arom.with_travel.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE likes SET is_deleted = true, deleted_at = now() where id = ?")
@SQLRestriction("is_deleted is FALSE")
public class Likes extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shorts_id")
    private Shorts shorts;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accompanies_id")
    private Accompany accompanies;

    @Builder
    public Likes(Member member, Accompany accompanies) {
        this.member = member;
        this.accompanies = accompanies;
    }

    @Builder
    public Likes(Member member, Shorts shorts) {
        this.member = member;
        this.shorts = shorts;
    }

    public void update(Member member, Accompany accompanies){
        this.member = member;
        this.accompanies = accompanies;
        accompanies.getLikes().add(this);
        member.getLikes().add(this);
    }

    public static Likes init(){
        return Likes.builder()
                .build();
    }
}
