package ch.heigvd.amt.gamificator.api.badge;

import ch.heigvd.amt.gamificator.api.model.Badge;
import ch.heigvd.amt.gamificator.repositories.BadgeRepository;
import lombok.AllArgsConstructor;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

@Service
@AllArgsConstructor
public class BadgeService {

    private final BadgeRepository badgeRepository;

    public ch.heigvd.amt.gamificator.entities.Badge registerNewBadge(String name, Integer applicationId, MultipartFile image) {
        ch.heigvd.amt.gamificator.entities.Badge newBadge = toEntity(name, applicationId, image);

        newBadge = badgeRepository.save(newBadge);

        return newBadge;
    }

    public ch.heigvd.amt.gamificator.entities.Badge registerNewBadge(Badge badge) {
        ch.heigvd.amt.gamificator.entities.Badge newBadge = null;
        try {
            newBadge = registerNewBadge(badge.getName(), badge.getApplicationId(), new MockMultipartFile(Objects.requireNonNull(badge.getImage().getFilename()), badge.getImage().getInputStream().readAllBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newBadge;
    }

    public ch.heigvd.amt.gamificator.entities.Badge toEntity(Badge badge) {
        ch.heigvd.amt.gamificator.entities.Badge newBadge = null;
        try {
            newBadge = toEntity(badge.getName(), badge.getApplicationId(), new MockMultipartFile(Objects.requireNonNull(badge.getImage().getFilename()), badge.getImage().getInputStream().readAllBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newBadge;
    }

    public ch.heigvd.amt.gamificator.entities.Badge toEntity(String name, Integer applicationId, MultipartFile image) {
        ch.heigvd.amt.gamificator.entities.Badge badge = new ch.heigvd.amt.gamificator.entities.Badge();

        badge.setName(name);
        badge.setApplicationId(applicationId);
        badge.setUrl(image.getName());

        try {
            if (!image.isEmpty()) {
                ByteArrayInputStream bis = new ByteArrayInputStream(image.getBytes());
                BufferedImage bImage = ImageIO.read(bis);
                ImageIO.write(bImage, "png", new File("src/main/resources/badges/" + image.getName()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return badge;
    }
}
