package com.mm.apps.airdb.sql;


public interface IQuery {
	QueryType getType();
	String getId();
	String getSql();
	ISqlParameters getParameters();
}
