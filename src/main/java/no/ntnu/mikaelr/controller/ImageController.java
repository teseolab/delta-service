package no.ntnu.mikaelr.controller;

import no.ntnu.mikaelr.service.dao.ImageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/images")
public class ImageController {

    @Autowired
    ImageDao imageDao;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseEntity<String> uploadImage(@RequestBody byte[] byteArray) {
        String imageUri = imageDao.uploadImage(byteArray);
        return new ResponseEntity<String>(imageUri, HttpStatus.OK);
    }

    @RequestMapping(value = "/{imageName}", method = RequestMethod.GET)
    public ResponseEntity getImage(@PathVariable String imageName) {
        Path imagePath = Paths.get("images/" + imageName + ".jpg");
        try {
            byte[] imageBytes = Files.readAllBytes(imagePath);
            return new ResponseEntity<byte[]>(imageBytes, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

}
