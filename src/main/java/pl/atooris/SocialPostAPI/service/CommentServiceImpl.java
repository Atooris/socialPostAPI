package pl.atooris.SocialPostAPI.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.atooris.SocialPostAPI.entity.Comment;
import pl.atooris.SocialPostAPI.entity.Post;
import pl.atooris.SocialPostAPI.entity.User;
import pl.atooris.SocialPostAPI.exception.EntityNotFoundException;
import pl.atooris.SocialPostAPI.repository.CommentRepository;
import pl.atooris.SocialPostAPI.repository.PostRepository;
import pl.atooris.SocialPostAPI.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CommentServiceImpl implements CommentService{

    CommentRepository commentRepository;
    PostService postService;
    UserService userService;
    NotificationService notificationService;

    @Override
    public Comment getComment(Long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        return unwrapComment(comment, id);
    }

    @Override
    public Comment saveComment(Comment comment, Long postId, Long authorId) {
        Post post = postService.getPost(postId);
        User author = userService.getUser(authorId);
        comment.setPost(post);
        comment.setAuthor(author);
        notificationService.createCommentNotification(comment);
        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public Comment updateComment(String content, Long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        Comment unwrappedComment = unwrapComment(comment, id);
        unwrappedComment.setContent(content);
        return commentRepository.save(unwrappedComment);
    }

    @Override
    public List<Comment> getPostComments(Long postId) {
        return commentRepository.findByPostId(postId);
    }

    @Override
    public List<Comment> getUserComments(Long userId) {
        return commentRepository.findByAuthorId(userId);
    }

    static Comment unwrapComment(Optional<Comment> entity, Long id){
        if(entity.isPresent()) return entity.get();
        else throw new EntityNotFoundException(id, Comment.class);
    }
}
