package matcha.tag.service;

import matcha.tag.manipulation.TagManipulator;
import matcha.tag.model.Tag;

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

    public void saveTags(String login, String tags) {
        System.err.println("LOGIN: " + login);
        System.err.println("TAGS: " + tags);
        tagManipulator.clearUserTags(login);
        if (tags == null || tags.isEmpty())
            return;
        String[] split = tags.split(",");
        for (String tag : split) {
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

    public Object getUsersWithCommonTags(List<Integer> tags) {
        return tagManipulator.getUsersWithCommonTags(tags);
    }
}
