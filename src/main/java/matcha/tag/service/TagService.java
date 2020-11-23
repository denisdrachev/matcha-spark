package matcha.tag.service;

import matcha.tag.manipulation.TagManipulator;

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
        tagManipulator.clearUserTags(login);
        if (tags == null || tags.isEmpty())
            return;
        String[] split = tags.split(",");
        for (String tag : split) {
            int tagId = tagManipulator.saveTag(tag);
            tagManipulator.saveTagForUser(login, tagId);
        }
    }

    public String getUserTags(String login) {
        return tagManipulator.getUserTags(login);
    }
}
