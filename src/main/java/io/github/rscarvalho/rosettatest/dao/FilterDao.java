package io.github.rscarvalho.rosettatest.dao;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapperFactory;

import com.hubspot.rosetta.jdbi.BindWithRosetta;
import com.hubspot.rosetta.jdbi.RosettaMapperFactory;

import io.github.rscarvalho.rosettatest.data.Filter;

@RegisterMapperFactory(RosettaMapperFactory.class)
interface FilterDao {
  @GetGeneratedKeys
  @SqlUpdate("INSERT INTO filters (name, filterValue) VALUES (:name, :filterValue)")
  long insert(@BindWithRosetta Filter filter);

  @SqlQuery("SELECT * FROM filters WHERE id = :id")
  Filter getById(@Bind("id") long id);

  @SqlQuery("SELECT * FROM filters ORDER BY id")
  List<Filter> getFilters();
}
