package matcha.location.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import matcha.location.manipulation.LocationManipulator;
import matcha.location.model.Location;
import matcha.user.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class LocationService {

    private static LocationService locationService;

    public static LocationService getInstance() {
        if (locationService == null) {
            locationService = new LocationService();
        }
        return locationService;
    }

    public void init() {
//        registration();
    }

    private LocationManipulator locationManipulator = new LocationManipulator();

    public Location getLocationByUserId(int userId) {
        return locationManipulator.getLocationByUserIdAndActive(userId);
    }

    public List<Location> getAllLocations() {
        return locationManipulator.getAllLocations();
    }

    public void deactivationLocationByLogin(int profileId) {
        locationManipulator.deactivationLocationByLogin(profileId);
    }

    public void saveLocation(Location location) {
        locationManipulator.insertLocation(location);
    }
}
