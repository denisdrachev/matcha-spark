package matcha.tag.manipulation;

import lombok.extern.slf4j.Slf4j;
import matcha.tag.db.TagDB;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class TagManipulator {

    private final TagDB tagDB = new TagDB();

    public int saveTag(String tag) {
        int tagId = tagDB.getTagId(tag);
        if (tagId == -1) {
            tagId = tagDB.createNewTag(tag);
        } else {
            tagDB.incTagCount(tagId);
        }
        return tagId;
    }

    public void saveTagForUser(String login, int tagId) {
        tagDB.addTagRelation(login, tagId);
    }

    public void clearUserTags(String login) {
        tagDB.clearUserTagRelations(login);
    }

    public String getUserTags(String login) {
        List<String> userTagsOnlyName = tagDB.getUserTagsOnlyName(login);
        return userTagsOnlyName.stream().collect(Collectors.joining(","));
    }
}
