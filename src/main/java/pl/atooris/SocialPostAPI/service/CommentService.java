package pl.atooris.SocialPostAPI.service;

import pl.atooris.SocialPostAPI.entity.Comment;

import java.util.List;

public interface CommentService{
    Comment getComment(Long id);
    Comment saveComment(Comment comment, Long postId, Long authorId);
    void deleteComment(Long id);
    Comment updateComment(String content, Long id);
    List<Comment> getPostComments(Long postId);
    List<Comment> getUserComments(Long userId);
}
