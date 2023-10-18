package pl.atooris.SocialPostAPI.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.atooris.SocialPostAPI.entity.Image;
import pl.atooris.SocialPostAPI.entity.Post;
import pl.atooris.SocialPostAPI.repository.ImageRepository;
import pl.atooris.SocialPostAPI.repository.PostRepository;

import java.io.IOException;
import java.util.List;

@AllArgsConstructor
@Service
public class ImageServiceImpl implements ImageService{

    ImageRepository imageRepository;
    PostService postService;

    @Override
    public Image saveImage(MultipartFile file, Long postId) throws IOException {
        Image image = new Image();
        Post post = postService.getPost(postId);
        image.setImageData(file.getBytes());
        image.setImageName(file.getOriginalFilename());
        image.setAuthor(post.getAuthor());
        image.setPost(post);

        return imageRepository.save(image);
    }

    @Override
    public void deleteImage(Long id) {
        imageRepository.deleteById(id);
    }

    @Override
    public List<Image> getUserImages(Long userId) {
        return imageRepository.findByAuthorId(userId);
    }

    @Override
    public List<Image> getPostImages(Long postId) {
        return imageRepository.findByPostId(postId);
    }
}
