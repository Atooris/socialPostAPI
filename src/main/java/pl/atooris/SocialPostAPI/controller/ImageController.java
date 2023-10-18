package pl.atooris.SocialPostAPI.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.atooris.SocialPostAPI.entity.Image;
import pl.atooris.SocialPostAPI.service.ImageService;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
public class ImageController {

    ImageService imageService;

    @PostMapping("/post/{postId}/upload-image")
    public ResponseEntity<Image> uploadImage(@RequestParam("image") MultipartFile file, @PathVariable Long postId){
        try{
            return new ResponseEntity<>(imageService.saveImage(file, postId), HttpStatus.OK);
        } catch (IOException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/image/{id}")
    public ResponseEntity<HttpStatus> deleteImage(@PathVariable Long id){
        imageService.deleteImage(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/user/{userId}/images")
    public ResponseEntity<List<Image>> getUserImages(@PathVariable Long userId){
        return new ResponseEntity<>(imageService.getUserImages(userId), HttpStatus.OK);
    }

    @GetMapping("/post/{postId}/images")
    public ResponseEntity<List<Image>> getPostImages(@PathVariable Long postId){
        return new ResponseEntity<>(imageService.getPostImages(postId), HttpStatus.OK);
    }

}
