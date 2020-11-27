package matcha.location.manipulation;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import matcha.location.db.LocationDB;
import matcha.location.model.Location;

import java.util.List;

@Slf4j
public class LocationManipulator {

    private final LocationDB locationDB = new LocationDB();

    public Location getLocationByProfileId(Integer profileId) {

        return locationDB.getLocationByProfileId(profileId);

//        Location locationByUserIdAndActive = locationDB.getLocationByUserIdAndActive(userId);
//        if (locationByUserIdAndActive == null) {
//            return new Location();
//        } else {
//            return locationByUserIdAndActive;
//        }


//        try {
//            return locationDB.getLocationByUserIdAndActive(userId);
//        } catch (GetActiveLocationByLoginException e) {
//            return new Location();
//        }
    }

    public void insertLocation(Location location) {
        locationDB.insertLocation(location);
    }

    public Integer updateLocation(Location location) {
        return locationDB.updateLocation(location);
    }

    public void deactivationLocationByLogin(int profileId) {
        locationDB.updateActiveLocationByLogin(false, profileId);
    }

    public List<Location> getAllLocations() {
        return locationDB.getLocations();
    }

    public Location getLastLocationByProfileId(Integer profileId) {
        return locationDB.getLastLocationByProfileId(profileId);
    }
}
