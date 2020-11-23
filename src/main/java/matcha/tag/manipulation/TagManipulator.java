package matcha.tag.manipulation;

import lombok.extern.slf4j.Slf4j;
import matcha.tag.db.TagDB;
import matcha.tag.model.Tag;

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
    public List<Tag> getAllTags() {
        return tagDB.getAllTags();
    }

    public void clearUserTags(String login) {
        List<Integer> tagsIds = tagDB.getUserTagsOnlyIds(login);
        tagsIds.forEach(tagDB::decTagCount);
        tagDB.clearUserTagRelations(login);
    }

    public String getUserTagsAsString(String login) {
        List<String> userTagsOnlyName = tagDB.getUserTagsOnlyName(login);
        return userTagsOnlyName.stream().collect(Collectors.joining(","));
    }

    public List<String> getUserTagsAsList(String login) {
        return tagDB.getUserTagsOnlyName(login);
    }

    public Object getUsersWithCommonTags(List<Integer> tags) {
//        List<Integer> tagsId = new ArrayList<>();
//        tags.forEach(tag -> {
//            int tagId = tagDB.getTagId(tag);
//            if (tagId != -1)
//                tagsId.add(tagId);
//        });
        return tagDB.getUsersWithCommonTags(tags);
    }
}
