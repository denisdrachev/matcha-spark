package matcha.location.service;

import matcha.location.manipulation.LocationManipulator;
import matcha.location.model.Location;

import java.util.List;

//import org.springframework.stereotype.Service;

//@Service
//@AllArgsConstructor
//@NoArgsConstructor
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

    public Location getLocationIfActiveByProfileId(int profileId) {
        Location location = locationManipulator.getLocationByProfileId(profileId);
//        if (!location.isActive())
//            return null;
        return location;
    }

    public Location getLocationByProfileId(int profileId) {
        return locationManipulator.getLocationByProfileId(profileId);
    }

    public List<Location> getAllLocations() {
        return locationManipulator.getAllLocations();
    }

    public void deactivationLocationByLogin(int profileId) {
        locationManipulator.deactivationLocationByLogin(profileId);
    }

    public void updateUserLocation(Location location) {
        locationManipulator.updateLocation(location);
    }

    public void initLocationAndUpdate(Location location, Integer profileId, boolean isActive, boolean userSet) {
        if (location != null) {
            location.setProfileId(profileId);
            location.setActive(isActive);
            location.setUserSet(userSet);
            updateUserLocation(location);
        }
    }

    public void saveLocation(Location location) {
        locationManipulator.insertLocation(location);
    }

    public Location getLastLocationByProfileId(Integer profileId) {
        return locationManipulator.getLastLocationByProfileId(profileId);
    }
}
