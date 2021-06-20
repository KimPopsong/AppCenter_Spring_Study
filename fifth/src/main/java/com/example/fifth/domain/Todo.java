package com.example.fifth.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Todo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todo_id")
    private Long id;

    private String content;

    @Enumerated(EnumType.STRING)
    private TodoStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public static Todo createTodo(String content, Member member) {
        Todo todo = new Todo();

        todo.content = content;
        todo.status = TodoStatus.NOTFINISH;
        todo.member = member;

        return todo;
    }

    public void changeStatus(){
        this.status = TodoStatus.FINISH;
    }
}
