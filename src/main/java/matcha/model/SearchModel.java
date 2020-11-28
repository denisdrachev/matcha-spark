package matcha.model;

import lombok.Data;
import matcha.location.model.Location;

import java.util.List;

@Data
public class SearchModel {

    private Double oneKmConst = 0.016608;
    private Integer minAge;
    private Integer maxAge;
    private Integer minRatingAge;
    private Integer maxRatingAge;
    private Double deltaRadius;
    private List<Integer> tags;
    private Integer limit;
    private Integer offset;
    private Location userLocation;
    private String login;
    private List<Integer> preference;
    private int sortAge;
    private int sortLocation;
    private int sortRating;
    private int sortTags;

    public SearchModel(Location location, String minAge, String maxAge, String minRatingAge, String maxRatingAge,
                       String deltaRadius, List<Integer> tags, String limit, String offset, String login,
                       Integer preference, String sortAge, String sortLocation, String sortRating, String sortTags) throws Exception {
        setUserLocation(location);
        setMinAge(minAge);
        //tags=24,sefad&sortAge=1&sortLocation=1&sortRating=1&sortTags=1&ageMin=18&ageMax=27&minRating=0&maxRating=500&deltaRadius=500&limit=3&offset=0
        //get-users?ageMin=0&ageMax=100&minRating=0&maxRating=999&deltaRadius=1000&limit=100&offset=0&sortAge=1&sortLocation=1&sortRating-1&sortTags=1&needPreference=1
        setMaxAge(maxAge);
        setMinRatingAge(minRatingAge);
        setMaxRatingAge(maxRatingAge);
        setDeltaRadius(deltaRadius);
        this.tags = tags;
        setLimit(limit);
        setOffset(offset);
        this.login = login;
        setPreference(preference);
        setSortAge(sortAge);
        setSortLocation(sortLocation);
        setSortRating(sortRating);
        setSortTags(sortTags);
        validate();
    }

    private void validate() throws Exception {
        if (userLocation == null || minAge < 0 || minAge > 150 || maxAge < 0 || maxAge > 150 || minRatingAge < 0
                || maxRatingAge < 0 || deltaRadius < 0 || limit <= 0 || limit > 100 || offset < 0 || login == null
                || login.isEmpty() || sortAge < -1 || sortAge > 1 || sortLocation < -1 || sortLocation > 1
                || sortRating < -1 || sortRating > 1 || sortTags < -1 || sortTags > 1)
            throw new Exception();
    }

    public void setMinAge(String minAge) {
        if (minAge != null)
            this.minAge = Integer.parseInt(minAge);
        else
            this.minAge = 0;
    }

    public void setMaxAge(String maxAge) {
        if (maxAge != null)
            this.maxAge = Integer.parseInt(maxAge);
        else
            this.maxAge = 150;
    }

    public void setMinRatingAge(String minRatingAge) {
        if (minRatingAge != null)
            this.minRatingAge = Integer.parseInt(minRatingAge);
        else
            this.minRatingAge = 0;
    }

    public void setMaxRatingAge(String maxRatingAge) {
        if (maxRatingAge != null)
            this.maxRatingAge = Integer.parseInt(maxRatingAge);
        else
            this.maxRatingAge = Integer.MAX_VALUE;
    }

    public void setDeltaRadius(String deltaRadius) {
        Integer distance;
        if (deltaRadius != null)
            distance = Integer.parseInt(deltaRadius);
        else
            distance = 100;
        this.deltaRadius = distance * oneKmConst;
    }

//    public void setTags(String tags) {
//        if (tags != null) {
//            String[] split = tags.split(",");
//            this.tags = Arrays.asList(split);
//        } else {
//            this.tags = List.of();
//        }
//    }

    public void setLimit(String limit) {
        if (limit != null)
            this.limit = Integer.parseInt(limit);
        else
            this.limit = 20;
    }

    public void setOffset(String offset) {
        if (offset != null)
            this.offset = Integer.parseInt(offset);
        else
            this.offset = 0;
    }

    public Double getMaxX() {
        return userLocation.getX() + this.deltaRadius;
    }

    public Double getMaxY() {
        return userLocation.getY() + this.deltaRadius;
    }

    public Double getMinX() {
        return userLocation.getX() - this.deltaRadius;
    }

    public Double getMinY() {
        return userLocation.getY() - this.deltaRadius;
    }

    public void setPreference(Integer preference) {
        if (preference == null) {
            this.preference = List.of(1, 2);
        } else {
            if (preference == 3) {
                this.preference = List.of(1, 2);
            } else if (preference == 2) {
                this.preference = List.of(2);
            } else {
                this.preference = List.of(1);
            }
        }
        System.err.println("this.preference: " + this.preference);
    }

    public void setSortAge(String sortAge) {
        if (sortAge == null || sortAge.isEmpty())
            this.sortAge = 0;
        else {
            this.sortAge = Integer.parseInt(sortAge);
        }
    }

    public void setSortLocation(String sortLocation) {
        if (sortLocation == null || sortLocation.isEmpty())
            this.sortLocation = 0;
        else
            this.sortLocation = Integer.parseInt(sortLocation);
    }

    public void setSortRating(String sortRating) {
        if (sortRating == null || sortRating.isEmpty())
            this.sortRating = 0;
        else
            this.sortRating = Integer.parseInt(sortRating);
    }

    public void setSortTags(String sortTags) {
        if (sortTags == null || sortTags.isEmpty())
            this.sortTags = 0;
        else
            this.sortTags = Integer.parseInt(sortTags);
    }

    public boolean isSorting() {
        return !(sortAge == 0 && sortLocation == 0 && sortRating == 0 && sortTags == 0);
    }
}
