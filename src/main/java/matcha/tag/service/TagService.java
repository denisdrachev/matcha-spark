package matcha.tag.service;

import matcha.tag.manipulation.TagManipulator;
import matcha.tag.model.Tag;

import java.util.ArrayList;
import java.util.List;

public class TagService {

    private static TagService tagService;
    private TagManipulator tagManipulator = new TagManipulator();

    public static TagService getInstance() {
        if (tagService == null) {
            tagService = new TagService();
        }
        return tagService;
    }

    public void saveTags(String login, List<String> tags) {
        tagManipulator.clearUserTags(login);
        for (String tag : tags) {
            int tagId = tagManipulator.saveTag(tag);
            tagManipulator.saveTagForUser(login, tagId);
        }
    }

    public List<String> getUserTags(String login) {
        return tagManipulator.getUserTagsAsList(login);
    }

    public List<Tag> getAllTags() {
        return tagManipulator.getAllTags();
    }

    public List<Integer> getTagsIds(String tags) {
        List<Integer> ids = new ArrayList<>();
        if (tags == null || tags.isEmpty())
            return ids;
        String[] split = tags.split(",");
        for (String tag : split) {
            int tagId = tagManipulator.getTagId(tag);
            if (tagId != -1)
                ids.add(tagId);
        }
        return ids;
    }

    public Object getUsersWithCommonTags(List<Integer> tags) {
        return tagManipulator.getUsersWithCommonTags(tags);
    }

    public List<Tag> getPopularTags(int limit) {
        return tagManipulator.getPopularTags(limit);
    }
}
