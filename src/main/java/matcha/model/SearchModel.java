package matcha.model;

import lombok.Data;
import matcha.location.model.Location;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Data
public class SearchModel implements MyObject {

    private double oneKmConst = 0.016608;

    @Max(value = 100, message = "Максимальное значение параметра minAge равно 100")
    @Min(value = 0, message = "Минимальное значение параметра minAge равно 0")
    private int minAge;

    @Max(value = 100, message = "Максимальное значение параметра maxAge равно 100")
    @Min(value = 0, message = "Минимальное значение параметра maxAge равно 0")
    private int maxAge;

    @Min(value = 0, message = "Минимальное значение параметра minRatingAge равно 0")
    private int minRatingAge;

    @Min(value = 0, message = "Минимальное значение параметра maxRatingAge равно 0")
    private int maxRatingAge;

    @Min(value = 0, message = "Минимальное значение параметра deltaRadius равно 0")
    private double deltaRadius;

    @NotNull(message = "tags не может быть NULL")
    private List<Integer> tags;

    @Max(value = 100, message = "Максимальное значение параметра limit равно 100")
    @Min(value = 0, message = "Минимальное значение параметра limit равно 0")
    private int limit;

    @Min(value = 0, message = "Минимальное значение параметра offset равно 0")
    private int offset;
    private Location userLocation;

    @Length(min = 1, max = 50, message = "Допустимая длина логина от 1 до 50 символов")
    @NotBlank(message = "Поле 'login' не может быть пустым")
    private String login;

    @NotNull(message = "preference не может быть NULL")
    @Size(min = 1, message = "Минимальное количество элементов в preference равно 1")
    private List<Integer> preference;

    @Max(value = 1, message = "Максимальное значение параметра sortAge равно 1")
    @Min(value = -1, message = "Минимальное значение параметра sortAge равно -1")
    private int sortAge;

    @Max(value = 1, message = "Максимальное значение параметра sortLocation равно 1")
    @Min(value = -1, message = "Минимальное значение параметра sortLocation равно -1")
    private int sortLocation;

    @Max(value = 1, message = "Максимальное значение параметра sortRating равно 1")
    @Min(value = -1, message = "Минимальное значение параметра sortRating равно -1")
    private int sortRating;

    @Max(value = 1, message = "Максимальное значение параметра sortTags равно 1")
    @Min(value = -1, message = "Минимальное значение параметра sortTags равно -1")
    private int sortTags;

    @Size(max = 4, message = "Указаны лишние параметры сортировки")
    private LinkedList<String> sortOrderList;

    public SearchModel(Location location, List<Integer> tags, String login, Integer preference,
                       Map<String, String> params, LinkedList<String> sortOrderList) {

        String minAge = params.get("ageMin");
        String maxAge = params.get("ageMax");
        String minRatingAge = params.get("minRating");
        String maxRatingAge = params.get("maxRating");
        String deltaRadius = params.get("deltaRadius");
        String limit = params.get("limit");
        String offset = params.get("offset");
        String sortAge = params.get("sortAge");
        String sortLocation = params.get("sortLocation");
        String sortRating = params.get("sortRating");
        String sortTags = params.get("sortTags");
        this.sortOrderList = sortOrderList;


        setUserLocation(location);
        setMinAge(minAge);
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
            this.maxAge = 100;
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
        this.deltaRadius = distance;
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
