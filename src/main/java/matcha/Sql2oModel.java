package matcha;

import org.sql2o.Sql2o;
import org.sql2o.quirks.NoQuirks;

public class Sql2oModel {

    private static Sql2o sql2o = null;

    public static Sql2o getSql2o() {
        if (sql2o == null) {
            sql2o = init();
        }
        return sql2o;
    }

    private Sql2oModel() {
    }

    private static Sql2o init() {
        String connectionString = "jdbc:h2:mem:matcha;DB_CLOSE_DELAY=-1;INIT=RUNSCRIPT from 'classpath:sql/tables/all.sql'";
        Sql2o mySql2o = new Sql2o(connectionString, "root", "root", new NoQuirks() {
        });
        return mySql2o;
    }
}
