package pl.atooris.SocialPostAPI.service;

import pl.atooris.SocialPostAPI.entity.Like;

import java.util.List;

public interface LikeService {
    void addOrDeleteLikeFromPost(Like like, Long postId, Long userId);
    void deleteLikeFromPost(Long postId, Long userId);
    List<Like> getPostLikes(Long postId);
    List<Like> getUserLikes(Long userId);
    Integer getPostLikesCount(Long postId);
}
