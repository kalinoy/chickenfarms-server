package com.chickenfarms.chickenfarms.repository;

import com.chickenfarms.chickenfarms.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    
    @Query(value = "SELECT * FROM chickenFarm_db.comment_table WHERE ticket_id=?1 ORDER BY created_date DESC limit ?2,?3",nativeQuery = true)
    List<Comment> getSortedCommentsByPage(long ticketId,int startIndex,int endIndex);
}
