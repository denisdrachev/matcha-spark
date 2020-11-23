package matcha.tag.db;

import lombok.extern.slf4j.Slf4j;
import matcha.Sql2oModel;
import matcha.db.crud.Delete;
import matcha.db.crud.Insert;
import matcha.db.crud.Select;
import matcha.db.crud.Update;
import matcha.exception.db.DeleteDBException;
import matcha.exception.db.InsertDBException;
import matcha.exception.db.SelectDBException;
import matcha.exception.db.UpdateDBException;
import matcha.tag.model.Tag;
import org.sql2o.Sql2o;

import java.util.List;

@Slf4j
public class TagDB {

    private final Sql2o sql2o = Sql2oModel.getSql2o();


    public int getTagId(String tag) {
        log.info("Get tag ID: {}", tag);
        try (org.sql2o.Connection conn = sql2o.open()) {

            List<Integer> count = conn.createQuery(Select.selectTagIdByName)
                    .addParameter("name", tag)
                    .executeAndFetch(Integer.class);
            conn.commit();
            log.info("Get tag ID result: {} ", count);
            return count.size() == 0 ? -1 : count.get(0);
        } catch (Exception e) {
            log.warn("Exception. getTagId: {}", e.getMessage());
            throw new SelectDBException();
        }
    }

    public List<String> getUserTagsOnlyName(String login) {
        log.info("Get user tags only name. Login: {}", login);
        try (org.sql2o.Connection conn = sql2o.open()) {

            List<String> tags = conn.createQuery(Select.selectUserTags)
                    .addParameter("login", login)
                    .executeAndFetch(String.class);
            conn.commit();
            log.info("Get user tags only name. Result: {} ", tags.size());
            return tags;
        } catch (Exception e) {
            log.warn("Exception. getUserTagsOnlyName: {}", e.getMessage());
            throw new SelectDBException();
        }
    }

    public List<Integer> getUserTagsOnlyIds(String login) {
        log.info("Get user tags only Ids. Login: {}", login);
        try (org.sql2o.Connection conn = sql2o.open()) {

            List<Integer> tagsIds = conn.createQuery(Select.selectUserTagsOnluIds)
                    .addParameter("login", login)
                    .executeAndFetch(Integer.class);
            conn.commit();
            log.info("Get user tags only Ids. Result size: {} ", tagsIds.size());
            return tagsIds;
        } catch (Exception e) {
            log.warn("Exception. getUserTagsOnlyIds: {}", e.getMessage());
            throw new SelectDBException();
        }
    }

    public List<Tag> getAllTags() {
        log.info("Get all tags");
        try (org.sql2o.Connection conn = sql2o.open()) {

            List<Tag> tags = conn.createQuery(Select.selectAllTags)
                    .executeAndFetch(Tag.class);
            conn.commit();
            log.info("Get all tags. Size: {} ", tags.size());
            return tags;
        } catch (Exception e) {
            log.warn("Exception. getUserTagsOnlyName: {}", e.getMessage());
            throw new SelectDBException();
        }
    }

    public List<Tag> getAllTagRelations() {
        log.info("Get all tag relations");
        try (org.sql2o.Connection conn = sql2o.open()) {

            List<Tag> tags = conn.createQuery(Select.selectAllTagRelations)
                    .executeAndFetch(Tag.class);
            conn.commit();
            log.info("Get all tags. Size: {} ", tags.size());
            return tags;
        } catch (Exception e) {
            log.warn("Exception. getUserTagsOnlyName: {}", e.getMessage());
            throw new SelectDBException();
        }
    }

    public void incTagCount(int tagId) {
        log.info("Inc tag count by tag ID: {}", tagId);
        try (org.sql2o.Connection conn = sql2o.open()) {

            int result = conn.createQuery(Update.updateIncTagCountById)
                    .addParameter("id", tagId)
                    .executeUpdate().getResult();
            conn.commit();
            log.info("Inc tag count by tag ID. result: {}", result);
        } catch (Exception e) {
            log.warn("Exception. incTagCount: {}", e.getMessage());
            e.printStackTrace();
            throw new UpdateDBException();
        }
    }

    public void decTagCount(int tagId) {
        log.info("Dec tag count by tag ID: {}", tagId);
        try (org.sql2o.Connection conn = sql2o.open()) {

            int result = conn.createQuery(Update.updateDecTagCountById)
                    .addParameter("id", tagId)
                    .executeUpdate().getResult();
            conn.commit();
            log.info("Dec tag count by tag ID. result: {}", result);
        } catch (Exception e) {
            log.warn("Exception. decTagCount: {}", e.getMessage());
            e.printStackTrace();
            throw new UpdateDBException();
        }
    }

    public int createNewTag(String tag) {
        log.info("Create new tag: {}", tag);
        try (org.sql2o.Connection conn = sql2o.open()) {

            Integer tagId = conn.createQuery(Insert.insertTag)
                    .addParameter("name", tag)
                    .executeUpdate().getKey(Integer.class);
            conn.commit();

            log.info("Create new tag. Tag id: {}", tagId);
            return tagId;
        } catch (Exception e) {
            log.warn("Exception. createNewTag: {}", e.getMessage());
            e.printStackTrace();
            throw new InsertDBException();
        }
    }

    public void clearUserTagRelations(String login) {
        log.info("Delete user tag relations. Login:{}", login);
        try (org.sql2o.Connection conn = sql2o.open()) {

            Integer result = conn.createQuery(Delete.deleteTagsRelationByLogin)
                    .addParameter("login", login)
                    .executeUpdate().getResult();
            conn.commit();

            log.info("Delete user tag relations. Result: {}", result);
        } catch (Exception e) {
            log.warn("Exception. clearUserTagRelations: {}", e.getMessage());
            e.printStackTrace();
            throw new DeleteDBException();
        }
    }

    public void addTagRelation(String login, int tagId) {
        log.info("Create new tag relation. login:{} tagId:{}", login, tagId);
        try (org.sql2o.Connection conn = sql2o.open()) {

            Integer result = conn.createQuery(Insert.insertTagRelation)
                    .addParameter("login", login)
                    .addParameter("tagId", tagId)
                    .executeUpdate().getResult();
            conn.commit();

            log.info("Create new tag relation. Result: {}", result);
        } catch (Exception e) {
            log.warn("Exception. addTagRelation: {}", e.getMessage());
            e.printStackTrace();
            throw new InsertDBException();
        }
    }

    public Object getUsersWithCommonTags(List<Integer> tagsIds) {
        log.info("Get users with common tags: {}", tagsIds);
        try (org.sql2o.Connection conn = sql2o.open()) {

            //TODO мб тут сортировку по убываниюдобавить и брать только логины, без количества
            List<Tag> tagsResult = conn.createQuery(Select.selectUsersWithCommonTags)
                    .addParameter("tagIds", tagsIds)
                    .executeAndFetch(Tag.class);
            conn.commit();
            tagsResult.forEach(System.out::println);
            log.info("Get users with common tags. Result count: {} ", tagsResult.size());
            return tagsResult;
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("Exception. getUsersWithCommonTags: {}", e.getMessage());
            throw new SelectDBException();
        }
    }
}
