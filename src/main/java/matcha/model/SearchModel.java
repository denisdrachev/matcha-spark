package matcha.model;

import lombok.Data;
import matcha.location.model.Location;

import java.util.Arrays;
import java.util.List;

@Data
public class SearchModel {

    private Double oneKmConst = 0.016608;
    private Integer minAge;
    private Integer maxAge;
    private Integer minRatingAge;
    private Integer maxRatingAge;
    private Double deltaRadius;
    private List<String> tags;
    private Integer limit;
    private Integer offset;
    private Location userLocation;

    public SearchModel(Location location, String minAge, String maxAge, String minRatingAge, String maxRatingAge,
                       String deltaRadius, String tags, String limit, String offset) {
        setUserLocation(location);
        setMinAge(minAge);
        setMaxAge(maxAge);
        setMinRatingAge(minRatingAge);
        setMaxRatingAge(maxRatingAge);
        setDeltaRadius(deltaRadius);
        setTags(tags);
        setLimit(limit);
        setOffset(offset);
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

    public void setTags(String tags) {
        if (tags != null) {
            String[] split = tags.split(",");
            this.tags = Arrays.asList(split);
        } else {
            this.tags = List.of();
        }
    }

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
}
