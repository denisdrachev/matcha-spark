package matcha.profile.service;

import lombok.extern.slf4j.Slf4j;
import matcha.image.model.Image;
import matcha.image.service.ImageService;
import matcha.profile.manipulation.ProfileManipulator;
import matcha.profile.model.ProfileEntity;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
//@Service
//@AllArgsConstructor
//@NoArgsConstructor
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
        List<Image> imagesByProfileId = imageService.getImagesByProfileId(id);
        profileById.setImages(imagesByProfileId);
        return profileById;
    }

    public ProfileEntity getProfileByIdWithImagesNotEmpty(int id) {
        ProfileEntity profileById = profileManipulator.getProfileById(id);
        List<Image> imagesByProfileId = imageService.getImagesByProfileId(id).
                stream()
                .filter(image -> !image.getSrc().isEmpty())
                .collect(Collectors.toList());
        profileById.setImages(imagesByProfileId);
        return profileById;
    }

    public void updateProfile(int profileId, ProfileEntity newProfile) {
        ProfileEntity currentProfile = getProfileById(newProfile.getId());
        System.err.println("profileId: " + profileId);
        System.err.println("newProfile.getId(): " + newProfile.getId());
        List<Image> images = imageService.getImagesByProfileId(profileId);
        System.err.println("images.size: " + images.size());
        //мб получуть только профиль id?
        newProfile.setId(currentProfile.getId());


        newProfile.getImages().forEach(image -> {
            System.err.println("IN CYCLE");
            for (Image img : images) {
                System.err.println("image.getIndex() == img.getIndex(): " + (image.getIndex() == img.getIndex()));
                if (image.getIndex() == img.getIndex()) {
                    System.err.println("INNER! ");
                    System.err.println(image);
                    System.err.println(img);
                    image.setId(img.getId());
                }
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
