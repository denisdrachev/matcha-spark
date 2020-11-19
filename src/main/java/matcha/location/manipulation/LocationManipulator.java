package matcha.location.manipulation;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import matcha.exception.db.location.GetActiveLocationByLoginException;
import matcha.location.db.LocationDB;
import matcha.location.model.Location;
import matcha.mail.MailService;
import matcha.profile.db.ProfileDB;
import matcha.user.db.UserDB;
import matcha.validator.ValidationMessageService;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
//@RequiredArgsConstructor
@NoArgsConstructor
public class LocationManipulator {

    private final LocationDB locationDB = new LocationDB();

    public Location getLocationByUserIdAndActive(Integer userId) {

        Location locationByUserIdAndActive = locationDB.getLocationByUserIdAndActive(userId);
        if (locationByUserIdAndActive == null) {
            return new Location();
        } else {
            return locationByUserIdAndActive;
        }


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

    public void deactivationLocationByLogin(String login) {
        locationDB.updateActiveLocationByLogin(false, login);
    }

    public List<Location> getAllLocations() {
        return locationDB.getLocations();
    }
}
