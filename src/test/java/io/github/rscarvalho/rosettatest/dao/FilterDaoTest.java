package io.github.rscarvalho.rosettatest.dao;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

import io.github.rscarvalho.rosettatest.data.Filter;
import io.github.rscarvalho.rosettatest.data.FilterValue;
import io.github.rscarvalho.rosettatest.data.NumberValue;
import io.github.rscarvalho.rosettatest.data.StringValue;

public class FilterDaoTest {
  private static DBI dbi;

  private Handle transactionHandle;
  private FilterDao filterDao;

  @BeforeClass
  public static void setupClass() throws Exception {
    Class.forName("org.h2.Driver");
    dbi = new DBI("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");

    Handle createHandle = dbi.open();
    createHandle.execute("CREATE TABLE filters(" +
        "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
        "name VARCHAR(255), " +
        "filterValue VARCHAR(1024)" +
        ")");

    createHandle.close();
  }

  @AfterClass
  public static void tearDownClass() throws Exception {
    Handle dropHandle = dbi.open();
    dropHandle.execute("DROP TABLE IF EXISTS filters");
    dropHandle.close();
  }

  @Before
  public void setUp() {
    transactionHandle = dbi.open();
    filterDao = transactionHandle.attach(FilterDao.class);

    transactionHandle.begin();
  }

  @After
  public void tearDown() {
    transactionHandle.rollback();
    transactionHandle.close();
  }

  @Test
  public void itInsertsNumberFilter() {
    FilterValue<?> value = new StringValue("my value");
    Filter filter = new Filter(0L, "filter1", value);
    long id = filterDao.insert(filter);

    assertThat(id).isGreaterThan(0);
  }

  @Test
  public void itGetsFilterById() {
    FilterValue<?> value = new StringValue("my value");
    Filter filter = new Filter(0L, "filter1", value);
    long id = filterDao.insert(filter);

    assertThat(id).isGreaterThan(0);

    Filter actualFilter = filterDao.getFilters().get(0);
    assertThat(actualFilter).isNotNull();
    assertThat(actualFilter.getFilterValue()).isInstanceOf(NumberValue.class);
  }
}
