package matcha.profile.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import matcha.event.service.EventService;
import matcha.image.model.Image;
import matcha.image.service.ImageService;
import matcha.profile.manipulation.ProfileManipulator;
import matcha.profile.model.ProfileEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor
public class ProfileService {

    private ImageService imageService = ImageService.getInstance();
    private ProfileManipulator profileManipulator = new ProfileManipulator();

    private static ProfileService profileService;

    public static ProfileService getInstance() {
        if (profileService == null) {
            profileService = new ProfileService();
        }
        return profileService;
    }

    public void init() {
//        registration();
    }

    public void saveProfile(ProfileEntity profile) {
        profileManipulator.updateProfile(profile);
        imageService.saveImages(profile.getImages());
    }

    public ProfileEntity getProfileById(int id) {
        return profileManipulator.getProfileById(id);
    }

    public ProfileEntity getProfileByIdWithImages(int id) {
        ProfileEntity profileById = profileManipulator.getProfileById(id);
        List<Image> imagesByProfileId = imageService.getImagesByProfileId(profileById.getId());
        profileById.setImages(imagesByProfileId);
        return profileById;
    }

    public void updateProfile(int profileId, ProfileEntity newProfile) {
        ProfileEntity currentProfile = getProfileById(newProfile.getId());
        List<Image> images = imageService.getImagesByProfileId(profileId);
        //мб получуть только профиль id?
        newProfile.setId(currentProfile.getId());


        newProfile.getImages().forEach(image -> {
            for (Image img : images) {
                if (image.getIndex() == img.getIndex())
                    image.setId(img.getId());
            }
        });
        System.err.println(newProfile.getImages());
        saveProfile(newProfile);
    }

    public Integer createNewProfile() {
        Integer newProfileId = profileManipulator.createNewProfile();
        imageService.createNewImages(newProfileId);
        return newProfileId;
    }
}
