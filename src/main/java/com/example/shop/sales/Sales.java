package com.example.shop.sales;

import com.example.shop.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@ Getter
@Setter
@ToString
public class Sales {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String itemName;
    Integer price;
    Integer count;

    // N(Order) : 1(Member)
    // 한 명의 회원이 여러 개의 주문.
    @ManyToOne
    @JoinColumn(
            name = "member_id",
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    private Member member;

    @CreationTimestamp
    LocalDateTime created;

}
