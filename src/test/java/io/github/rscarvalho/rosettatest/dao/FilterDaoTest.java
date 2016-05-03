package io.github.rscarvalho.rosettatest.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

import com.hubspot.rosetta.Rosetta;
import com.hubspot.rosetta.jdbi.RosettaObjectMapperOverride;

import io.github.rscarvalho.rosettatest.JsonUtils;
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

    dbi = new DBI("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=FALSE");

    Handle createHandle = dbi.open();
    createHandle.execute("CREATE TABLE IF NOT EXISTS filters(" +
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
  public void itDoesSerializationProperly() throws IOException {
    FilterValue<?> value = new StringValue("my value");
    Filter filter = new Filter(0L, "filter1", value);

    String filterString = Rosetta.getMapper().writeValueAsString(filter);
    assertThat(filterString).isEqualTo("{\"id\":0,\"name\":\"filter1\",\"filterValue\":\"{\\\"@type\\\":\\\"StringValue\\\",\\\"value\\\":\\\"my value\\\"}\"}");

    Filter filter1 = Rosetta.getMapper().readValue(filterString, Filter.class);
    assertThat(filter1.getName()).isEqualTo(filter.getName());
    assertThat(filter1.getId()).isEqualTo(filter.getId());
    assertThat(filter1.getFilterValue()).isInstanceOf(StringValue.class);
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

    Filter actualFilter = filterDao.getById(id);
    assertThat(actualFilter).isNotNull();
    assertThat(actualFilter.getId()).isEqualTo(id);
    assertThat(actualFilter.getName()).isEqualTo("filter1");
    assertThat(actualFilter.getFilterValue()).isInstanceOf(NumberValue.class);
  }

  @Test
  public void itGetsFilterByIdWithOverriddenJsonMapperOnHandle() {
    Handle newHandle = dbi.open();

    new RosettaObjectMapperOverride(JsonUtils.MAPPER).override(newHandle);
    FilterDao filterDao = newHandle.attach(FilterDao.class);

    FilterValue<?> value = new StringValue("my value");
    Filter filter = new Filter(0L, "filter1", value);
    long id = filterDao.insert(filter);

    assertThat(id).isGreaterThan(0);

    Filter actualFilter = filterDao.getById(id);
    assertThat(actualFilter).isNotNull();
    assertThat(actualFilter.getId()).isEqualTo(id);
    assertThat(actualFilter.getName()).isEqualTo("filter1");
    assertThat(actualFilter.getFilterValue()).isInstanceOf(NumberValue.class);
  }

  @Test
  public void itGetsFilterByIdWithOverriddenJsonMapperOnDBI() {
    DBI localDbi = new DBI("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=FALSE");
    new RosettaObjectMapperOverride(JsonUtils.MAPPER).override(localDbi);
    Handle localHandle = localDbi.open();

    FilterDao filterDao = localHandle.attach(FilterDao.class);

    FilterValue<?> value = new StringValue("my value");
    Filter filter = new Filter(0L, "filter1", value);
    long id = filterDao.insert(filter);

    assertThat(id).isGreaterThan(0);

    Filter actualFilter = filterDao.getById(id);
    assertThat(actualFilter).isNotNull();
    assertThat(actualFilter.getId()).isEqualTo(id);
    assertThat(actualFilter.getName()).isEqualTo("filter1");
    assertThat(actualFilter.getFilterValue()).isInstanceOf(NumberValue.class);
  }
}
