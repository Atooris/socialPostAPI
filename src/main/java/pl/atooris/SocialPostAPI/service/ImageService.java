package pl.atooris.SocialPostAPI.service;

import jakarta.transaction.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.atooris.SocialPostAPI.entity.Image;

import java.io.IOException;
import java.util.List;

public interface ImageService {
    Image saveImage(MultipartFile file, Long postId) throws IOException;
    void deleteImage(Long id);
    List<Image> getUserImages(Long userId);
    List<Image> getPostImages(Long postId);
}
